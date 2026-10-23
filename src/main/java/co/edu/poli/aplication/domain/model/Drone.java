package co.edu.poli.aplication.domain.model;

/**
 * Representa un drone dentro del sistema.
 */
public class Drone {

    private int id;
    private String serial;
    private String modelo;
    private double peso;

    /** Crea un drone sin inicializar sus atributos. */
    public Drone() {
    }

    /**
     * Crea un drone con sus datos principales.
     *
     * @param id identificador del drone
     * @param serial número de serie
     * @param modelo modelo del drone
     * @param peso peso del drone en kilogramos
     */
    public Drone(int id, String serial, String modelo, double peso) {
        this.id = id;
        this.serial = serial;
        this.modelo = modelo;
        this.peso = peso;
    }

    /**
     * Devuelve el identificador asignado al drone.
     *
     * @return identificador del drone
     */
    public int getId() {
        return id;
    }

    /**
     * Actualiza el identificador del drone.
     *
     * @param id nuevo identificador
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el número de serie del drone.
     *
     * @return número de serie
     */
    public String getSerial() {
        return serial;
    }

    /**
     * Actualiza el número de serie.
     *
     * @param serial nuevo número de serie
     */
    public void setSerial(String serial) {
        this.serial = serial;
    }

    /**
     * Devuelve el modelo del drone.
     *
     * @return modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Actualiza el modelo del drone.
     *
     * @param modelo nuevo modelo
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * Devuelve el peso del drone.
     *
     * @return peso en kilogramos
     */
    public double getPeso() {
        return peso;
    }

    /**
     * Actualiza el peso del drone.
     *
     * @param peso nuevo peso en kilogramos
     */
    public void setPeso(double peso) {
        this.peso = peso;
    }
}