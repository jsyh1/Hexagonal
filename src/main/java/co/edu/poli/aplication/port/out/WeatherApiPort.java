package co.edu.poli.aplication.port.out;

import co.edu.poli.aplication.domain.model.weather.*;
import co.edu.poli.aplication.port.out.GeocodingPort.Coordenadas;

/** Puerto de salida para consultar datos de una fuente meteorológica. */
public interface WeatherApiPort {

    /**
     * Consulta las condiciones meteorológicas para unas coordenadas.
     *
     * @param coordenadas ubicación de la consulta
     * @return datos meteorológicos obtenidos
     * @throws Exception si la consulta a la fuente meteorológica falla
     */
    Weather consultarClima(Coordenadas coordenadas) throws Exception;
}