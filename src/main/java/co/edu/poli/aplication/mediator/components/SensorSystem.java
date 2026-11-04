package co.edu.poli.aplication.mediator.components;

import co.edu.poli.aplication.mediator.DroneMediator;

/**
 * Sistema de sensores: activa los sensores necesarios.
 */
public class SensorSystem {

    private final DroneMediator mediator;

    public SensorSystem(DroneMediator mediator) {
        this.mediator = mediator;
    }

    public String activarSensores() {
        String resultado = "Sensores: sensores activados.";
        mediator.notificar(this, "SENSORES_ACTIVADOS");
        return resultado;
    }
}