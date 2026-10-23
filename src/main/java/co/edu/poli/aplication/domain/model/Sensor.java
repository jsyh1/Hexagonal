package co.edu.poli.aplication.domain.model;

/**
 * Representa un sensor instalado en un drone.
 */
public class Sensor {

    private int id;
    private String tipo;
    private String fabricante;

    /** Crea un sensor sin inicializar sus atributos. */
    public Sensor() {
    }

    /**
     * Crea un sensor con sus datos principales.
     *
     * @param id identificador del sensor
     * @param tipo tipo de sensor
     * @param fabricante fabricante del sensor
     */
    public Sensor(int id, String tipo, String fabricante) {
        this.id = id;
        this.tipo = tipo;
        this.fabricante = fabricante;
    }

    /**
     * Devuelve el identificador asignado.
     *
     * @return identificador del sensor
     */
    public int getId() {
        return id;
    }

    /**
     * Actualiza el identificador del sensor.
     *
     * @param id nuevo identificador
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el tipo de sensor.
     *
     * @return tipo de sensor
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Actualiza el tipo de sensor.
     *
     * @param tipo nuevo tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Devuelve el fabricante del sensor.
     *
     * @return fabricante
     */
    public String getFabricante() {
        return fabricante;
    }

    /**
     * Actualiza el fabricante del sensor.
     *
     * @param fabricante nuevo fabricante
     */
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }
}