
package co.edu.poli.infrastructure.weather;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.poli.aplication.domain.model.weather.*;
import co.edu.poli.aplication.port.out.GeocodingPort.Coordenadas;
import co.edu.poli.aplication.port.out.WeatherApiPort;

/** Adaptador que obtiene las condiciones meteorológicas desde Open-Meteo. */
public class OpenMeteoWeatherAdapter implements WeatherApiPort {

	private final HttpClient httpClient;
	private final ObjectMapper objectMapper = new ObjectMapper();

    /** Crea un adaptador meteorológico con un cliente HTTP predeterminado. */
    public OpenMeteoWeatherAdapter() {
		this(HttpClient.newHttpClient());
    }

    OpenMeteoWeatherAdapter(HttpClient httpClient) {
		this.httpClient = httpClient;
    }

    /**
     * Consulta el pronóstico actual para las coordenadas indicadas.
     *
     * @param coordenadas ubicación de la consulta
     * @return observación meteorológica actual
     * @throws Exception si falla la petición o la respuesta no contiene datos
     *         meteorológicos válidos
     */
	@Override
	public Weather consultarClima(Coordenadas coordenadas) throws Exception {

		String url = "https://api.open-meteo.com/v1/forecast" + "?latitude=" + coordenadas.getLatitud() + "&longitude="
				+ coordenadas.getLongitud() + "&current=temperature_2m,relative_humidity_2m,"
				+ "wind_speed_10m,surface_pressure" + "&hourly=visibility" + "&timezone=auto";

		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

		HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			throw new RuntimeException("Error al consultar el clima. Código HTTP: " + response.statusCode());
		}

		JsonNode json = objectMapper.readTree(response.body());
		JsonNode actual = json.path("current");

		if (actual.isMissingNode() || actual.path("time").isMissingNode()) {
			throw new RuntimeException("La API no devolvió los datos climáticos actuales.");
		}

		double temperatura = actual.path("temperature_2m").asDouble();

		double humedad = actual.path("relative_humidity_2m").asDouble();

		double velocidadViento = actual.path("wind_speed_10m").asDouble();

		double presionAtmosferica = actual.path("surface_pressure").asDouble();

		String horaActual = actual.path("time").asText();
		String zonaHoraria = json.path("timezone").asText(null);

		JsonNode horas = json.path("hourly").path("time");
		JsonNode visibilidades = json.path("hourly").path("visibility");

		double visibilidad = buscarVisibilidad(horas, visibilidades, horaActual, zonaHoraria);

		return new Weather(null, temperatura, humedad, velocidadViento, presionAtmosferica, visibilidad);
	}

	double buscarVisibilidad(JsonNode horas, JsonNode visibilidades, String horaActual, String zonaHoraria) {

		if (!horas.isArray() || !visibilidades.isArray() || horas.isEmpty() || visibilidades.isEmpty()) {
			throw new RuntimeException("La API no devolvió datos de visibilidad.");
		}

		// Primero intentamos encontrar la coincidencia exacta.
		for (int i = 0; i < horas.size(); i++) {
			if (horaActual.equals(horas.get(i).asText())) {
				if (i < visibilidades.size() && visibilidades.get(i).isNumber()) {
					return visibilidades.get(i).asDouble();
				}
			}
		}

		// Si no coincide, buscamos la hora más cercana.
		try {
			if (zonaHoraria == null || zonaHoraria.isBlank()) {
				throw new DateTimeException("La respuesta no contiene una zona horaria.");
			}
			ZoneId zona = ZoneId.of(zonaHoraria);
			ZonedDateTime actual = LocalDateTime.parse(horaActual).atZone(zona);

			int indiceCercano = -1;
			long diferenciaMinima = Long.MAX_VALUE;

			for (int i = 0; i < horas.size(); i++) {
				if (i >= visibilidades.size() || !visibilidades.get(i).isNumber()) {
					continue;
				}

				ZonedDateTime hora = LocalDateTime.parse(horas.get(i).asText()).atZone(zona);

				long diferencia = Math.abs(Duration.between(actual, hora).toMinutes());

				if (diferencia < diferenciaMinima) {
					diferenciaMinima = diferencia;
					indiceCercano = i;
				}
			}

			// Solo aceptamos datos a un máximo de 60 minutos.
			if (indiceCercano >= 0 && diferenciaMinima <= 60) {
				return visibilidades.get(indiceCercano).asDouble();
			}

		} catch (DateTimeException e) {
			throw new RuntimeException(
					"La API devolvió una zona horaria o un formato de hora inesperado: " + horaActual, e);
		}

		throw new RuntimeException("No se encontró una visibilidad cercana a la hora actual: " + horaActual);
	}
}
