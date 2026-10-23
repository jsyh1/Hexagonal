package co.edu.poli.aplication.domain.model.chain;
import co.edu.poli.aplication.domain.model.weather.Weather;

/** Valida que la visibilidad reportada sea de al menos 5 km. */
public class VisibilityHandler extends WeatherHandler {

    /** Crea un manejador para validar la visibilidad. */
    public VisibilityHandler() {
    }

    /** {@inheritDoc} */
    @Override
	public boolean isValid(Weather w) {
        return w.getVisibilidad() >= 5 ;
    }
    /** {@inheritDoc} */
    @Override
	public String getFailureReason(Weather w) {
        return "La visibilidad no es mayor a 5km (actual: " + w.getVisibilidad() + " km)";
    }
}