package co.edu.poli.aplication.domain.model.chain;

import co.edu.poli.aplication.domain.model.weather.Weather;

/** Valida que la velocidad del viento no supere los 40 km/h. */
public class WindHandler extends WeatherHandler {

    /** Crea un manejador para validar la velocidad del viento. */
    public WindHandler() {
    }

    /** {@inheritDoc} */
    @Override
	public boolean isValid(Weather w) {
        return w.getVelocidadViento() <= 40;
    }
    /** {@inheritDoc} */
    @Override
	public String getFailureReason(Weather w) {
        return "Velocidad del viento superior a 40 km/h (actual: " + w.getVelocidadViento() + " km/h)";
    }
}