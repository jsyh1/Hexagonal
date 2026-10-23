package co.edu.poli.aplication.domain.model;

/**
 * Representa un drone especializado en actividades de vigilancia.
 */
public class Vigilancia extends Drone {

    private boolean deteccionTermica;

    /** Crea un drone de vigilancia sin inicializar sus atributos. */
    public Vigilancia() {
    }

    /**
     * Crea un drone de vigilancia con sus datos y capacidad térmica.
     *
     * @param id identificador del drone
     * @param serial número de serie
     * @param modelo modelo del drone
     * @param peso peso del drone en kilogramos
     * @param deteccionTermica indica si cuenta con detección térmica
     */
    public Vigilancia(int id, String serial, String modelo,
                       double peso,
                      boolean deteccionTermica) {

        super(id, serial, modelo, peso);
        this.deteccionTermica = deteccionTermica;
    }

    /**
     * Indica si el equipo cuenta con detección térmica.
     *
     * @return {@code true} si dispone de esta función
     */
    public boolean isDeteccionTermica() {
        return deteccionTermica;
    }

    /**
     * Configura la disponibilidad de detección térmica.
     *
     * @param deteccionTermica {@code true} si el drone dispone de esta función
     */
    public void setDeteccionTermica(boolean deteccionTermica) {
        this.deteccionTermica = deteccionTermica;
    }
}