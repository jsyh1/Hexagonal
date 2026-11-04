package co.edu.poli.aplication.service;

import java.util.Objects;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.mediator.DroneMediator;
import co.edu.poli.aplication.port.in.ExecuteMissionUseCase;

/**
 * Servicio de aplicación: delega la coordinación en el mediador.
 */
public class MissionService implements ExecuteMissionUseCase {

    private final DroneMediator mediator;

    public MissionService(DroneMediator mediator) {
        this.mediator = Objects.requireNonNull(mediator, "El mediador no puede ser nulo.");
    }

    @Override
    public String ejecutarMision(Drone drone) {
        return mediator.iniciarOperacion(drone);
    }
}