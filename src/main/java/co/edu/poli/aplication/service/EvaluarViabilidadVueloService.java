package co.edu.poli.aplication.service;

import co.edu.poli.aplication.domain.model.chain.HumidityHandler;
import co.edu.poli.aplication.domain.model.chain.PressureHandler;
import co.edu.poli.aplication.domain.model.chain.TemperatureHandler;
import co.edu.poli.aplication.domain.model.chain.VisibilityHandler;
import co.edu.poli.aplication.domain.model.chain.WeatherHandler;
import co.edu.poli.aplication.domain.model.chain.WindHandler;
import co.edu.poli.aplication.domain.model.weather.Weather;
import co.edu.poli.aplication.port.in.EvaluarViabilidadVueloUseCase;

/** Evalúa las condiciones meteorológicas mediante una cadena de reglas. */
public class EvaluarViabilidadVueloService implements EvaluarViabilidadVueloUseCase {

    private final WeatherHandler cadena;

    /** Construye el servicio con la cadena de reglas de vuelo predeterminada. */
    public EvaluarViabilidadVueloService() {
        this.cadena = construirCadena();
    }

    /** {@inheritDoc} */
    @Override
    public String evaluarViabilidad(String ciudad, Weather clima) {
        String resultado = cadena.handle(clima);

        // Registro del motivo del rechazo.
        if (resultado.startsWith(WeatherHandler.NO_APTO)) {
            System.out.println("[REGISTRO] " + ciudad + " -> " + resultado);
        }

        return resultado;
    }

    // Temperature → Humidity → Wind → Pressure → Visibility
    private WeatherHandler construirCadena() {
        WeatherHandler temperature = new TemperatureHandler();

        temperature.setNext(new HumidityHandler())
                   .setNext(new WindHandler())
                   .setNext(new PressureHandler())
                   .setNext(new VisibilityHandler());

        return temperature;
    }
}