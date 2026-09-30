package co.edu.poli.aplication.domain.model;

/**
 * Representa un drone especializado en actividades de vigilancia.
 */
public class Vigilancia extends Drone {

    private boolean deteccionTermica;

    public Vigilancia() {
    }

    public Vigilancia(int id, String serial, String modelo,
                       double peso,
                      boolean deteccionTermica) {

        super(id, serial, modelo, peso);
        this.deteccionTermica = deteccionTermica;
    }

    public boolean isDeteccionTermica() {
        return deteccionTermica;
    }

    public void setDeteccionTermica(boolean deteccionTermica) {
        this.deteccionTermica = deteccionTermica;
    }
}