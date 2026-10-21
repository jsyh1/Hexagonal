
package co.edu.poli.aplication.weather;

import java.util.HashSet;
import java.util.Set;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.domain.model.weather.Weather;
import co.edu.poli.aplication.port.in.ConsultarClimaUseCase;

public class WeatherNotificationService {

    private final Set<Drone> drones = new HashSet<>();

    private final ConsultarClimaUseCase consultarClima;

    public WeatherNotificationService(
            ConsultarClimaUseCase consultarClima) {
        this.consultarClima = consultarClima;
    }

    public void suscribir(Drone dron) {
        if (dron != null) {
            drones.add(dron);
        }
    }

    public void darDeBaja(Drone dron) {
        drones.remove(dron);
    }

    public void actualizarDesdeCiudad(String ciudad)
            throws Exception {

        Weather weather = consultarClima.consultarClima(ciudad);

        notificar(weather);
    }

    public void notificar(Weather weather) {
        if (weather == null) {
            return;
        }

        for (Drone dron : new HashSet<>(drones)) {
            System.out.println(
                    "Notificación meteorológica para el drone: "
                    + dron.getSerial());

            System.out.println("Temperatura: "
                    + weather.getTemperatura() + " °C");

            System.out.println("Humedad: "
                    + weather.getHumedad() + " %");

            System.out.println("Velocidad del viento: "
                    + weather.getVelocidadViento() + " km/h");

            System.out.println("Presión atmosférica: "
                    + weather.getPresionAtmosferica() + " hPa");

            System.out.println("Visibilidad: "
                    + weather.getVisibilidad() + " m");
        }
    }
}
