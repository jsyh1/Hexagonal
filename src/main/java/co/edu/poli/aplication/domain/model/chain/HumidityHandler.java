package co.edu.poli.aplication.domain.model.chain;

import co.edu.poli.aplication.domain.model.weather.Weather;

/** Valida que la humedad relativa no supere el 85 %. */
public class HumidityHandler extends WeatherHandler {

    /** Crea un manejador para validar la humedad relativa. */
    public HumidityHandler() {
    }

    /** {@inheritDoc} */
    @Override
	public boolean isValid(Weather w) {
        return w.getHumedad() <= 85;
    }
    /** {@inheritDoc} */
    @Override
	public String getFailureReason(Weather w) {
        return "Humedad mayor al 85% (actual: " + w.getHumedad() + " %)";
    }
}