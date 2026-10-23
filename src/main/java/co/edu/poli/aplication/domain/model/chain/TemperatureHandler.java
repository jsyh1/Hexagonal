package co.edu.poli.aplication.domain.model.chain;

import co.edu.poli.aplication.domain.model.weather.Weather;

/** Valida que la temperatura esté entre 0 y 40 °C, inclusive. */
public class TemperatureHandler extends WeatherHandler {

    /** Crea un manejador para validar la temperatura. */
    public TemperatureHandler() {
    }

    /** {@inheritDoc} */
    @Override
	public boolean isValid(Weather w) {
        return w.getTemperatura() >= 0 && w.getTemperatura()<= 40 ;
    }
    /** {@inheritDoc} */
    @Override
	public String getFailureReason(Weather w) {
        return "Temperatura no esta entre  0°C y 40°C (actual: " + w.getTemperatura() + " °C)";
    }
}