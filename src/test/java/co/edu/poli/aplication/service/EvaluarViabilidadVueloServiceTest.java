package co.edu.poli.aplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import co.edu.poli.aplication.domain.model.chain.WeatherHandler;
import co.edu.poli.aplication.domain.model.weather.Weather;

class EvaluarViabilidadVueloServiceTest {

    private final EvaluarViabilidadVueloService service =
            new EvaluarViabilidadVueloService();

    @Test
    void debeEvaluarElClimaRecibidoSinConsultarOtraFuente() {
        Weather clima = new Weather(1L, 23.5, 50.0, 18.0, 1012.0, 8.0);

        assertEquals(
                WeatherHandler.APTO,
                service.evaluarViabilidad("Bogotá", clima));
    }

    @Test
    void debeRechazarElClimaQueIncumpleLaPrimeraReglaDeLaCadena() {
        Weather clima = new Weather(1L, 41.0, 50.0, 18.0, 1012.0, 8.0);

        String resultado = service.evaluarViabilidad("Bogotá", clima);

        assertTrue(resultado.startsWith(WeatherHandler.NO_APTO));
        assertTrue(resultado.contains("41.0"));
    }
}
