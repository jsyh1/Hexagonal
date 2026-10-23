package co.edu.poli.aplication.domain.model;

/**
 * Representa un piloto encargado de operar un drone.
 */
public class Piloto {

    private int id;
    private String nombre;
    private String telefono;
    private String licencia;

    /** Crea un piloto sin inicializar sus atributos. */
    public Piloto() {
    }

    /**
     * Crea un piloto con sus datos principales.
     *
     * @param id identificador del piloto
     * @param nombre nombre del piloto
     * @param telefono número de contacto
     * @param licencia identificador de la licencia de operación
     */
    public Piloto(int id, String nombre, String telefono, String licencia) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.licencia = licencia;
    }

    /**
     * Devuelve el identificador asignado.
     *
     * @return identificador del piloto
     */
    public int getId() {
        return id;
    }

    /**
     * Actualiza el identificador del piloto.
     *
     * @param id nuevo identificador
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre del piloto.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Actualiza el nombre del piloto.
     *
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el número de contacto del piloto.
     *
     * @return teléfono de contacto
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Actualiza el número de contacto del piloto.
     *
     * @param telefono nuevo teléfono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve la licencia de operación del piloto.
     *
     * @return licencia del piloto
     */
    public String getLicencia() {
        return licencia;
    }

    /**
     * Actualiza la licencia de operación.
     *
     * @param licencia nueva licencia
     */
    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }
}