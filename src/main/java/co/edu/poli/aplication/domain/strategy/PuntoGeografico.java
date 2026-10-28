package co.edu.poli.aplication.domain.strategy;

/**
 * Representa un punto geográfico mediante latitud y longitud.
 */
public class PuntoGeografico {

    private String nombre;
    private Double latitud;
    private Double longitud;

    /**
     * Constructor vacío.
     */
    public PuntoGeografico() {
    }

    /**
     * Construye un punto geográfico.
     *
     * @param nombre nombre del punto
     * @param latitud latitud geográfica
     * @param longitud longitud geográfica
     */
    public PuntoGeografico(
            String nombre,
            Double latitud,
            Double longitud) {

        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }
}