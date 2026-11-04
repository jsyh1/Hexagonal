package co.edu.poli.aplication.mediator.components;

import co.edu.poli.aplication.mediator.DroneMediator;

/**
 * Sistema de comunicación: establece el enlace con el drone.
 */
public class CommunicationSystem {

    private final DroneMediator mediator;

    public CommunicationSystem(DroneMediator mediator) {
        this.mediator = mediator;
    }

    public String establecerEnlace() {
        String resultado = "Comunicación: enlace establecido.";
        mediator.notificar(this, "ENLACE_ESTABLECIDO");
        return resultado;
    }
}