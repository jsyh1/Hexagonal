
package co.edu.poli.infrastructure.weather;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.poli.aplication.port.out.GeocodingPort;

public class OpenMeteoGeocodingAdapter implements GeocodingPort {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Coordenadas buscarCiudad(String ciudad) throws Exception {

        String ciudadCodificada = URLEncoder.encode(
                ciudad, StandardCharsets.UTF_8
        );

        String url = "https://geocoding-api.open-meteo.com/v1/search"
                + "?name=" + ciudadCodificada
                + "&count=1&language=es&format=json";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "Error al buscar la ciudad. Código HTTP: "
                            + response.statusCode()
            );
        }

        JsonNode json = objectMapper.readTree(response.body());
        JsonNode resultados = json.path("results");

        if (!resultados.isArray() || resultados.isEmpty()) {
            throw new IllegalArgumentException(
                    "No se encontró la ciudad: " + ciudad
            );
        }

        JsonNode resultado = resultados.get(0);

        double latitud = resultado.path("latitude").asDouble();
        double longitud = resultado.path("longitude").asDouble();

        return new Coordenadas(latitud, longitud);
    }
}
