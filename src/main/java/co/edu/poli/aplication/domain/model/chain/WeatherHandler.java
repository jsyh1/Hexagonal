package co.edu.poli.aplication.domain.model.chain;

import co.edu.poli.aplication.domain.model.weather.Weather;

/**
 * Manejador base de una regla en la cadena de evaluación meteorológica.
 *
 * <p>Cada manejador valida una condición y delega al siguiente si esta se
 * cumple.</p>
 */
public abstract class WeatherHandler {
    /** Resultado de una evaluación que cumple todas las reglas. */
	public static final String APTO = "CLIMA APTO PARA OPERACIÓN DE DRONES";

    /** Prefijo del resultado de una evaluación que incumple una regla. */
    public static final String NO_APTO = "CLIMA NO APTO PARA OPERACIÓN DE DRONES";
    
    private WeatherHandler next;

    /** Crea un manejador que no delega hasta configurar su sucesor. */
    public WeatherHandler() {
    }

    /**
     * Configura el siguiente manejador de la cadena.
     *
     * @param next siguiente manejador
     * @return el manejador recibido, para enlazar varios manejadores
     */
	public WeatherHandler setNext(WeatherHandler next) {
		this.next = next;
		return next;
	}
    
    /**
     * Evalúa el clima con este manejador y continúa por la cadena si es válido.
     *
     * @param w condiciones meteorológicas que se evaluarán
     * @return resultado apto o no apto, con el motivo del primer incumplimiento
     */
	public String handle (Weather w) {
		if(!isValid(w)) {
			return NO_APTO + ": " + getFailureReason(w); 
		}
		if(next != null) {
			return next.handle(w);
		}
		return APTO;
	}
    /**
     * Comprueba la regla meteorológica implementada por el manejador.
     *
     * @param w condiciones meteorológicas que se evaluarán
     * @return {@code true} si el clima cumple la regla
     */
	public abstract boolean isValid(Weather w);

    /**
     * Describe por qué el clima incumple la regla del manejador.
     *
     * @param w condiciones meteorológicas evaluadas
     * @return descripción del incumplimiento
     */
    public abstract String getFailureReason(Weather w);

}
