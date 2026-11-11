package co.edu.poli.infrastructure.weather;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpHeaders;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.poli.aplication.domain.model.weather.Weather;
import co.edu.poli.aplication.port.out.GeocodingPort.Coordenadas;

class OpenMeteoWeatherAdapterTest {

    private final OpenMeteoWeatherAdapter adapter = new OpenMeteoWeatherAdapter();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void consultaElClimaYObtieneLaVisibilidadDeLaRespuesta() throws Exception {
        HttpClient httpClient = new StubHttpClient(200, """
                {
                  "timezone": "America/Bogota",
                  "current": {
                    "time": "2026-11-11T12:00",
                    "temperature_2m": 22.5,
                    "relative_humidity_2m": 60,
                    "wind_speed_10m": 12,
                    "surface_pressure": 1010
                  },
                  "hourly": {
                    "time": ["2026-11-11T12:00"],
                    "visibility": [7500]
                  }
                }
                """);
        OpenMeteoWeatherAdapter adapter = new OpenMeteoWeatherAdapter(httpClient);

        Weather weather = adapter.consultarClima(new Coordenadas(4.711, -74.0721));

        assertEquals(22.5, weather.getTemperatura());
        assertEquals(7500, weather.getVisibilidad());
    }

    @Test
    void rechazaUnaRespuestaHttpNoExitosa() {
        OpenMeteoWeatherAdapter adapter = new OpenMeteoWeatherAdapter(
                new StubHttpClient(503, ""));

        assertThrows(RuntimeException.class, () -> adapter.consultarClima(
                new Coordenadas(4.711, -74.0721)));
    }

    @Test
    void rechazaUnaRespuestaSinDatosActuales() {
        OpenMeteoWeatherAdapter adapter = new OpenMeteoWeatherAdapter(
                new StubHttpClient(200, """
                        {
                          "current": {},
                          "hourly": {
                            "time": ["2026-11-11T12:00"],
                            "visibility": [7500]
                          }
                        }
                        """));

        assertThrows(RuntimeException.class, () -> adapter.consultarClima(
                new Coordenadas(4.711, -74.0721)));
    }

    @Test
    void rechazaUnaRespuestaSinSeccionDeDatosActuales() {
        OpenMeteoWeatherAdapter adapter = new OpenMeteoWeatherAdapter(
                new StubHttpClient(200, """
                        {
                          "hourly": {
                            "time": ["2026-11-11T12:00"],
                            "visibility": [7500]
                          }
                        }
                        """));

        assertThrows(RuntimeException.class, () -> adapter.consultarClima(
                new Coordenadas(4.711, -74.0721)));
    }

    @Test
    void rechazaLaRespuestaSinDatosDeVisibilidad() throws Exception {
        JsonNode horas = objectMapper.readTree("[]");
        JsonNode visibilidades = objectMapper.readTree("[]");

        assertThrows(RuntimeException.class, () -> adapter.buscarVisibilidad(
                horas, visibilidades, "2026-11-11T12:00", "America/Bogota"));
    }

    @Test
    void obtieneVisibilidadParaLaHoraExactaSinNecesitarZonaHoraria() throws Exception {
        JsonNode horas = objectMapper.readTree("[\"2026-11-11T12:00\"]");
        JsonNode visibilidades = objectMapper.readTree("[8500]");

        assertEquals(8500, adapter.buscarVisibilidad(horas, visibilidades, "2026-11-11T12:00", null));
    }

    @Test
    void obtieneLaVisibilidadDeLaHoraMasCercanaEnLaZonaIndicada() throws Exception {
        JsonNode horas = objectMapper.readTree("[\"2026-11-11T11:30\", \"2026-11-11T13:00\"]");
        JsonNode visibilidades = objectMapper.readTree("[6000, 9000]");

        assertEquals(6000, adapter.buscarVisibilidad(
                horas, visibilidades, "2026-11-11T12:00", "America/Bogota"));
    }

    @Test
    void ignoraEntradasSinVisibilidadNumericaAlBuscarLaHoraCercana() throws Exception {
        JsonNode horas = objectMapper.readTree("[\"2026-11-11T11:50\", \"2026-11-11T12:10\"]");
        JsonNode visibilidades = objectMapper.readTree("[null, 7000]");

        assertEquals(7000, adapter.buscarVisibilidad(
                horas, visibilidades, "2026-11-11T12:00", "America/Bogota"));
    }

    @Test
    void rechazaHorasSinZonaHorariaEnLaBusquedaAproximada() throws Exception {
        JsonNode horas = objectMapper.readTree("[\"2026-11-11T11:30\"]");
        JsonNode visibilidades = objectMapper.readTree("[6000]");

        assertThrows(RuntimeException.class, () -> adapter.buscarVisibilidad(
                horas, visibilidades, "2026-11-11T12:00", null));
    }

    @Test
    void rechazaUnaZonaHorariaVacia() throws Exception {
        JsonNode horas = objectMapper.readTree("[\"2026-11-11T11:30\"]");
        JsonNode visibilidades = objectMapper.readTree("[6000]");

        assertThrows(RuntimeException.class, () -> adapter.buscarVisibilidad(
                horas, visibilidades, "2026-11-11T12:00", "   "));
    }

    @Test
    void rechazaUnaZonaHorariaNoValida() throws Exception {
        JsonNode horas = objectMapper.readTree("[\"2026-11-11T11:30\"]");
        JsonNode visibilidades = objectMapper.readTree("[6000]");

        assertThrows(RuntimeException.class, () -> adapter.buscarVisibilidad(
                horas, visibilidades, "2026-11-11T12:00", "Zona/Invalida"));
    }

    @Test
    void rechazaHorasQueNoSePuedenInterpretar() throws Exception {
        JsonNode horas = objectMapper.readTree("[\"hora-invalida\"]");
        JsonNode visibilidades = objectMapper.readTree("[6000]");

        assertThrows(RuntimeException.class, () -> adapter.buscarVisibilidad(
                horas, visibilidades, "2026-11-11T12:00", "America/Bogota"));
    }

    @Test
    void rechazaUnaVisibilidadAmasDeUnaHoraDeDistancia() throws Exception {
        JsonNode horas = objectMapper.readTree("[\"2026-11-11T10:00\"]");
        JsonNode visibilidades = objectMapper.readTree("[6000]");

        assertThrows(RuntimeException.class, () -> adapter.buscarVisibilidad(
                horas, visibilidades, "2026-11-11T12:00", "America/Bogota"));
    }

    private static final class StubHttpClient extends HttpClient {
        private final int statusCode;
        private final String body;

        private StubHttpClient(int statusCode, String body) {
            this.statusCode = statusCode;
            this.body = body;
        }

        @Override
        public Optional<CookieHandler> cookieHandler() {
            return Optional.empty();
        }

        @Override
        public Optional<Duration> connectTimeout() {
            return Optional.empty();
        }

        @Override
        public Redirect followRedirects() {
            return Redirect.NEVER;
        }

        @Override
        public Optional<ProxySelector> proxy() {
            return Optional.empty();
        }

        @Override
        public SSLContext sslContext() {
            try {
                return SSLContext.getDefault();
            } catch (java.security.NoSuchAlgorithmException exception) {
                throw new IllegalStateException(exception);
            }
        }

        @Override
        public SSLParameters sslParameters() {
            return new SSLParameters();
        }

        @Override
        public Optional<Authenticator> authenticator() {
            return Optional.empty();
        }

        @Override
        public Version version() {
            return Version.HTTP_1_1;
        }

        @Override
        public Optional<Executor> executor() {
            return Optional.empty();
        }

        @Override
        @SuppressWarnings("unchecked")
        public <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> handler)
                throws IOException, InterruptedException {
            HttpResponse<String> response = new StubHttpResponse(request, statusCode, body);
            return (HttpResponse<T>) response;
        }

        @Override
        public <T> CompletableFuture<HttpResponse<T>> sendAsync(
                HttpRequest request, HttpResponse.BodyHandler<T> handler) {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> CompletableFuture<HttpResponse<T>> sendAsync(
                HttpRequest request,
                HttpResponse.BodyHandler<T> handler,
                HttpResponse.PushPromiseHandler<T> pushPromiseHandler) {
            throw new UnsupportedOperationException();
        }
    }

    private static final class StubHttpResponse implements HttpResponse<String> {
        private final HttpRequest request;
        private final int statusCode;
        private final String body;

        private StubHttpResponse(HttpRequest request, int statusCode, String body) {
            this.request = request;
            this.statusCode = statusCode;
            this.body = body;
        }

        @Override
        public int statusCode() {
            return statusCode;
        }

        @Override
        public HttpRequest request() {
            return request;
        }

        @Override
        public Optional<HttpResponse<String>> previousResponse() {
            return Optional.empty();
        }

        @Override
        public HttpHeaders headers() {
            return HttpHeaders.of(Map.of(), (name, value) -> true);
        }

        @Override
        public String body() {
            return body;
        }

        @Override
        public Optional<SSLSession> sslSession() {
            return Optional.empty();
        }

        @Override
        public URI uri() {
            return request.uri();
        }

        @Override
        public HttpClient.Version version() {
            return HttpClient.Version.HTTP_1_1;
        }
    }
}
