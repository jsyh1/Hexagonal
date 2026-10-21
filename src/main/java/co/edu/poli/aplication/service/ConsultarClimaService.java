
package co.edu.poli.aplication.service;

import co.edu.poli.aplication.domain.model.weather.*;
import co.edu.poli.aplication.port.in.ConsultarClimaUseCase;
import co.edu.poli.aplication.port.out.GeocodingPort;
import co.edu.poli.aplication.port.out.GeocodingPort.Coordenadas;
import co.edu.poli.aplication.port.out.WeatherApiPort;

public class ConsultarClimaService implements ConsultarClimaUseCase {

    private final GeocodingPort geocodingPort;
    private final WeatherApiPort weatherApiPort;

    public ConsultarClimaService(
            GeocodingPort geocodingPort,
            WeatherApiPort weatherApiPort) {

        this.geocodingPort = geocodingPort;
        this.weatherApiPort = weatherApiPort;
    }

    @Override
    public Weather consultarClima(String ciudad) throws Exception {

        Coordenadas coordenadas =
                geocodingPort.buscarCiudad(ciudad);

        return weatherApiPort.consultarClima(coordenadas);
    }
}
