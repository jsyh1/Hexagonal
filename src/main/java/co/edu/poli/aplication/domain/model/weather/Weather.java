package co.edu.poli.aplication.domain.model.weather;

/**
 * Representa los datos meteorológicos obtenidos de la API.
 */
public class Weather {

    private Long id;
    private double temperatura;
    private double humedad;
    private double velocidadViento;
    private double presionAtmosferica;
    private double visibilidad;

    /**
     * Crea una observación meteorológica.
     *
     * @param id identificador de la observación, si existe
     * @param temperatura temperatura actual en grados Celsius
     * @param humedad humedad relativa en porcentaje
     * @param velocidadViento velocidad del viento en kilómetros por hora
     * @param presionAtmosferica presión atmosférica en hectopascales
     * @param visibilidad visibilidad reportada por la fuente meteorológica
     */
    public Weather(Long id, double temperatura, double humedad,
                   double velocidadViento, double presionAtmosferica,
                   double visibilidad) {
        this.id = id;
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.velocidadViento = velocidadViento;
        this.presionAtmosferica = presionAtmosferica;
        this.visibilidad = visibilidad;
    }

    /**
     * Devuelve el identificador de la observación.
     *
     * @return identificador, o {@code null} si no está asignado
     */
    public Long getId() {
        return id;
    }

    /**
     * Devuelve la temperatura.
     *
     * @return temperatura en grados Celsius
     */
    public double getTemperatura() {
        return temperatura;
    }

    /**
     * Devuelve la humedad relativa.
     *
     * @return humedad en porcentaje
     */
    public double getHumedad() {
        return humedad;
    }

    /**
     * Devuelve la velocidad del viento.
     *
     * @return velocidad en kilómetros por hora
     */
    public double getVelocidadViento() {
        return velocidadViento;
    }

    /**
     * Devuelve la presión atmosférica.
     *
     * @return presión en hectopascales
     */
    public double getPresionAtmosferica() {
        return presionAtmosferica;
    }

    /**
     * Devuelve la visibilidad reportada por la fuente meteorológica.
     *
     * @return visibilidad en la unidad entregada por la fuente
     */
    public double getVisibilidad() {
        return visibilidad;
    }
}