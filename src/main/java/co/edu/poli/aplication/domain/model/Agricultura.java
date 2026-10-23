package co.edu.poli.aplication.domain.model;

/**
 * Representa un drone especializado en actividades de agricultura.
 */
public class Agricultura extends Drone {

    private double capacidadTanque;

    /** Crea un drone agrícola sin inicializar sus atributos. */
    public Agricultura() {
    }

    /**
     * Crea un drone agrícola con sus datos y capacidad del tanque.
     *
     * @param id identificador del drone
     * @param serial número de serie
     * @param modelo modelo del drone
     * @param peso peso del drone en kilogramos
     * @param capacidadTanque capacidad del tanque de carga
     */
    public Agricultura(int id, String serial, String modelo,
                        double peso,
                       double capacidadTanque) {

        super(id, serial, modelo,  peso);
        this.capacidadTanque = capacidadTanque;
    }

    /**
     * Devuelve la capacidad del tanque de carga.
     *
     * @return capacidad del tanque
     */
    public double getCapacidadTanque() {
        return capacidadTanque;
    }

    /**
     * Actualiza la capacidad del tanque.
     *
     * @param capacidadTanque nueva capacidad
     */
    public void setCapacidadTanque(double capacidadTanque) {
        this.capacidadTanque = capacidadTanque;
    }
}