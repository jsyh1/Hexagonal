
package co.edu.poli.aplication.weather;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.domain.model.weather.Weather;
import co.edu.poli.aplication.port.in.ConsultarClimaUseCase;
import javafx.scene.control.Alert;

/**
 * Administra las suscripciones meteorológicas y distribuye actualizaciones
 * a los drones suscritos.
 */
public class WeatherNotificationService {

    private final Set<Drone> drones = new HashSet<>();
    private final ConsultarClimaUseCase consultarClima;
    private final WeatherNotifier notificador;

    /**
     * Crea el servicio con el notificador JavaFX predeterminado.
     *
     * @param consultarClima caso de uso para obtener el clima
     */
    public WeatherNotificationService(
            ConsultarClimaUseCase consultarClima) {
        this(consultarClima, WeatherNotificationService::mostrarNotificacionClima);
    }

    /**
     * Crea el servicio con un notificador que recibe solo el drone y el clima.
     *
     * @param consultarClima caso de uso para obtener el clima
     * @param notificador acción que procesa cada notificación
     */
    public WeatherNotificationService(
            ConsultarClimaUseCase consultarClima,
            BiConsumer<Drone, Weather> notificador) {
        this(consultarClima, (drone, clima, viabilidad) ->
                notificador.accept(drone, clima));
    }

    /**
     * Crea el servicio con un notificador que también recibe la evaluación
     * de viabilidad del vuelo.
     *
     * @param consultarClima caso de uso para obtener el clima
     * @param notificador acción que procesa cada notificación y su resultado
     */
    public WeatherNotificationService(
            ConsultarClimaUseCase consultarClima,
            WeatherNotifier notificador) {
        this.consultarClima = consultarClima;
        this.notificador = notificador;
    }

    /**
     * Suscribe un drone para recibir notificaciones meteorológicas.
     *
     * @param dron drone que se suscribirá; se ignora si es {@code null}
     */
    public void suscribir(Drone dron) {
        if (dron != null) {
            drones.add(dron);
        }
    }

    /**
     * Elimina la suscripción de un drone.
     *
     * @param dron drone que dejará de recibir notificaciones
     */
    public void darDeBaja(Drone dron) {
        drones.remove(dron);
    }

    /**
     * Consulta el clima de una ciudad y notifica a los drones suscritos.
     *
     * @param ciudad ciudad cuyo clima se consultará
     * @throws Exception si la consulta meteorológica falla
     */
    public void actualizarDesdeCiudad(String ciudad)
            throws Exception {

        Weather clima = consultarClima.consultarClima(ciudad);

        notificar(clima);
    }

    /**
     * Notifica el clima con un estado de viabilidad no evaluado.
     *
     * @param clima condiciones meteorológicas que se distribuirán
     */
    public void notificar(Weather clima) {
        notificar(clima, "Viabilidad de vuelo no evaluada.");
    }

    /**
     * Notifica a cada drone suscrito con el clima y el resultado de viabilidad.
     *
     * @param clima condiciones meteorológicas que se distribuirán
     * @param viabilidad resultado de la evaluación de vuelo
     */
    public void notificar(Weather clima, String viabilidad) {
        for (Drone drone : drones) {
            notificador.notificar(drone, clima, viabilidad);
        }
    }

    /** Notificador que presenta la información en una alerta JavaFX. */
    private static void mostrarNotificacionClima(
            Drone drone, Weather clima, String viabilidad) {

        Alert alerta = new Alert(Alert.AlertType.INFORMATION);

        alerta.setTitle("Notificación meteorológica");
        alerta.setHeaderText(
                "Evaluación de vuelo para el drone: " + drone.getSerial());

        alerta.setContentText(
                viabilidad + "\n\n"
                + "Temperatura: " + clima.getTemperatura() + " °C\n"
                + "Humedad: " + clima.getHumedad() + " %\n"
                + "Velocidad del viento: " + clima.getVelocidadViento() + " km/h\n"
                + "Presión atmosférica: " + clima.getPresionAtmosferica() + " hPa\n"
                + "Visibilidad: " + clima.getVisibilidad() + " m"
        );

        alerta.showAndWait();
    }

    /** Callback que presenta o procesa una notificación meteorológica. */
    @FunctionalInterface
    public interface WeatherNotifier {
        /**
         * Procesa una notificación para un drone.
         *
         * @param drone drone destinatario
         * @param clima condiciones meteorológicas consultadas
         * @param viabilidad resultado de la evaluación de vuelo
         */
        void notificar(Drone drone, Weather clima, String viabilidad);
    }
}
