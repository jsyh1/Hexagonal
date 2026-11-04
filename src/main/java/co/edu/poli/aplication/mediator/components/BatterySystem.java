package co.edu.poli.aplication.mediator.components;

import co.edu.poli.aplication.mediator.DroneMediator;

/**
 * Sistema de batería: verifica la disponibilidad energética.
 */
public class BatterySystem {

    private static final int NIVEL_MINIMO = 20;

    private final DroneMediator mediator;
    private final int nivelBateria;

    public BatterySystem(DroneMediator mediator) {
        this(mediator, 100);
    }

    public BatterySystem(DroneMediator mediator, int nivelBateria) {
        this.mediator = mediator;
        this.nivelBateria = nivelBateria;
    }

    public String verificarBateria() {
        if (nivelBateria < NIVEL_MINIMO) {
            String resultado = "Batería: nivel insuficiente (" + nivelBateria + "%).";
            mediator.notificar(this, "BATERIA_BAJA");
            return resultado;
        }

        String resultado = "Batería: nivel suficiente (" + nivelBateria + "%).";
        mediator.notificar(this, "BATERIA_OK");
        return resultado;
    }
}