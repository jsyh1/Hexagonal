
package co.edu.poli.aplication.weather;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.domain.model.weather.Weather;
import co.edu.poli.aplication.port.in.ConsultarClimaUseCase;
import javafx.scene.control.Alert;

public class WeatherNotificationService {

    private final Set<Drone> drones = new HashSet<>();
    private final ConsultarClimaUseCase consultarClima;
    private final BiConsumer<Drone, Weather> notificador;

    public WeatherNotificationService(
            ConsultarClimaUseCase consultarClima) {
        this(consultarClima, WeatherNotificationService::mostrarNotificacionClima);
    }

    public WeatherNotificationService(
            ConsultarClimaUseCase consultarClima,
            BiConsumer<Drone, Weather> notificador) {
        this.consultarClima = consultarClima;
        this.notificador = notificador;
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

        Weather clima = consultarClima.consultarClima(ciudad);

        notificar(clima);
    }

    private void notificar(Weather clima) {
        for (Drone drone : drones) {
            notificador.accept(drone, clima);
        }
    }

    private static void mostrarNotificacionClima(
            Drone drone, Weather clima) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle("Notificación meteorológica");
        alerta.setHeaderText(
                "Clima para el drone: " + drone.getSerial());

        alerta.setContentText(
                "Temperatura: " + clima.getTemperatura() + " °C\n"
                + "Humedad: " + clima.getHumedad() + " %\n"
                + "Velocidad del viento: " + clima.getVelocidadViento() + " km/h\n"
                + "Presión atmosférica: " + clima.getPresionAtmosferica() + " hPa\n"
                + "Visibilidad: " + clima.getVisibilidad() + " m"
        );

        alerta.showAndWait();
    }
}
