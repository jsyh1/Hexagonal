package co.edu.poli.aplication.domain.model.chain;

import co.edu.poli.aplication.domain.model.weather.Weather;

/** Valida que la presión atmosférica esté entre 950 y 1050 hPa, inclusive. */
public class PressureHandler extends WeatherHandler {

    /** Crea un manejador para validar la presión atmosférica. */
    public PressureHandler() {
    }

    /** {@inheritDoc} */
    @Override
	public boolean isValid(Weather w) {
        return w.getPresionAtmosferica() >= 950 && w.getPresionAtmosferica() <= 1050;
    }
    /** {@inheritDoc} */
    @Override
	public String getFailureReason(Weather w) {
        return "Presion atmosferica no esta entre 950 y 1050 hPa (actual: " + w.getPresionAtmosferica() + " hPa)";
    }
}