package co.edu.poli.aplication.domain.model;

import java.util.Date;

/**
 * Representa una misión realizada por un drone.
 */
public class Mision {

    private int id;
    private String nombre;
    private String descripcion;
    private Date fecha;
    private String ubicacion;

    /** Crea una misión sin inicializar sus atributos. */
    public Mision() {
    }

    /**
     * Crea una misión con sus datos principales.
     *
     * @param id identificador de la misión
     * @param nombre nombre de la misión
     * @param descripcion descripción de las actividades
     * @param fecha fecha de realización
     * @param ubicacion ubicación de la misión
     */
    public Mision(int id, String nombre, String descripcion,
                  Date fecha, String ubicacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
    }

    /**
     * Devuelve el identificador asignado.
     *
     * @return identificador de la misión
     */
    public int getId() {
        return id;
    }

    /**
     * Actualiza el identificador.
     *
     * @param id nuevo identificador
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve el nombre de la misión.
     *
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Actualiza el nombre de la misión.
     *
     * @param nombre nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la descripción de la misión.
     *
     * @return descripción
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Actualiza la descripción de la misión.
     *
     * @param descripcion nueva descripción
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Devuelve la fecha de realización.
     *
     * @return fecha de la misión
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Actualiza la fecha de realización.
     *
     * @param fecha nueva fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Devuelve la ubicación de la misión.
     *
     * @return ubicación
     */
    public String getUbicacion() {
        return ubicacion;
    }

    /**
     * Actualiza la ubicación de la misión.
     *
     * @param ubicacion nueva ubicación
     */
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}