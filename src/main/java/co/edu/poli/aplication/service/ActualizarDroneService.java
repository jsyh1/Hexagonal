package co.edu.poli.aplication.service;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.port.in.ActualizarDroneUseCase;
import co.edu.poli.aplication.port.out.DroneRepository;

public class ActualizarDroneService implements ActualizarDroneUseCase {

    private final DroneRepository droneRepository;

    public ActualizarDroneService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Override
    public Drone actualizarDrone(int id, Drone drone) {
        return droneRepository.modificar(id, drone);
    }
}
