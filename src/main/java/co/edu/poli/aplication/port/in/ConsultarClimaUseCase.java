package co.edu.poli.aplication.port.in;

import co.edu.poli.aplication.domain.model.weather.*;

public interface ConsultarClimaUseCase {

    Weather consultarClima(String ciudad) throws Exception;
}