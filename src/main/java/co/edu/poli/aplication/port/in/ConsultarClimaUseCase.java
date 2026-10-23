package co.edu.poli.aplication.port.in;

import co.edu.poli.aplication.domain.model.weather.*;

/** Caso de uso para consultar las condiciones meteorológicas de una ciudad. */
public interface ConsultarClimaUseCase {

    /**
     * Obtiene las condiciones meteorológicas actuales para una ciudad.
     *
     * @param ciudad nombre de la ciudad que se consultará
     * @return datos meteorológicos actuales
     * @throws Exception si la geocodificación o la consulta meteorológica falla
     */
    Weather consultarClima(String ciudad) throws Exception;
}