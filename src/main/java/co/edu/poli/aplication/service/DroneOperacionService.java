package co.edu.poli.aplication.service;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import co.edu.poli.aplication.domain.model.Drone;

public class DroneOperacionService {

    private final Drone drone;
    private final Random random = new Random();

    public DroneOperacionService(Drone drone) {
        this.drone = Objects.requireNonNull(drone, "El dron no puede ser nulo.");
    }

    public String tomarFotografia() {
        return etiqueta() + "Fotografía tomada correctamente.";
    }

    public String escanearZona() {
        return etiqueta() + "Zona escaneada: no se detectaron obstáculos.";
    }

    public String medirTemperatura() {
        double temperatura = 10 + random.nextDouble() * 20;   // lectura simulada
        return etiqueta() + String.format(Locale.US, "Temperatura medida: %.1f °C.", temperatura);
    }

    private String etiqueta() {
        return "[Dron " + drone.getSerial() + "] ";
    }
}
