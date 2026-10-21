package co.edu.poli.aplication.port.out;

import co.edu.poli.aplication.domain.model.weather.*;
import co.edu.poli.aplication.port.out.GeocodingPort.Coordenadas;

public interface WeatherApiPort {

    Weather consultarClima(Coordenadas coordenadas) throws Exception;
}