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

    public Long getId() {
        return id;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public double getHumedad() {
        return humedad;
    }

    public double getVelocidadViento() {
        return velocidadViento;
    }

    public double getPresionAtmosferica() {
        return presionAtmosferica;
    }

    public double getVisibilidad() {
        return visibilidad;
    }
}