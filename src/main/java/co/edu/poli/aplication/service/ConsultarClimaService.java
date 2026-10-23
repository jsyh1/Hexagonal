
package co.edu.poli.aplication.service;

import co.edu.poli.aplication.domain.model.weather.*;
import co.edu.poli.aplication.port.in.ConsultarClimaUseCase;
import co.edu.poli.aplication.port.out.GeocodingPort;
import co.edu.poli.aplication.port.out.GeocodingPort.Coordenadas;
import co.edu.poli.aplication.port.out.WeatherApiPort;

/** Coordina la geocodificación de una ciudad y la consulta del clima. */
public class ConsultarClimaService implements ConsultarClimaUseCase {

    private final GeocodingPort geocodingPort;
    private final WeatherApiPort weatherApiPort;

    /**
     * Crea el servicio con los adaptadores de geocodificación y clima.
     *
     * @param geocodingPort servicio que resuelve ciudades a coordenadas
     * @param weatherApiPort servicio que obtiene datos meteorológicos
     */
    public ConsultarClimaService(
            GeocodingPort geocodingPort,
            WeatherApiPort weatherApiPort) {

        this.geocodingPort = geocodingPort;
        this.weatherApiPort = weatherApiPort;
    }

    /** {@inheritDoc} */
    @Override
    public Weather consultarClima(String ciudad) throws Exception {

        Coordenadas coordenadas =
                geocodingPort.buscarCiudad(ciudad);

        return weatherApiPort.consultarClima(coordenadas);
    }
}
