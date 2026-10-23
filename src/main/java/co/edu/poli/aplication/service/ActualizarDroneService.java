package co.edu.poli.aplication.service;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.port.in.ActualizarDroneUseCase;
import co.edu.poli.aplication.port.out.DroneRepository;

/** Implementa el caso de uso de actualización de drones. */
public class ActualizarDroneService implements ActualizarDroneUseCase {

    private final DroneRepository droneRepository;

    /**
     * Crea el servicio con el repositorio que actualizará los drones.
     *
     * @param droneRepository repositorio de drones
     */
    public ActualizarDroneService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    /** {@inheritDoc} */
    @Override
    public Drone actualizarDrone(int id, Drone drone) {
        return droneRepository.modificar(id, drone);
    }
}
