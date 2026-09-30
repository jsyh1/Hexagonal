package co.edu.poli.aplication.servicios;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.port.in.CrearDroneUseCase;
import co.edu.poli.aplication.port.out.DroneRepository;

public class CrearDroneService implements CrearDroneUseCase {

    private final DroneRepository droneRepository;

    public CrearDroneService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Override
    public Drone crearDrone(Drone drone) {
        return droneRepository.guardar(drone);
    }
}
