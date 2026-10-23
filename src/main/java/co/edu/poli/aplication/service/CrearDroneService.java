package co.edu.poli.aplication.service;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.port.in.CrearDroneUseCase;
import co.edu.poli.aplication.port.out.DroneRepository;

/** Implementa el caso de uso de creación de drones. */
public class CrearDroneService implements CrearDroneUseCase {

    private final DroneRepository droneRepository;

    /**
     * Crea el servicio con el repositorio que persistirá los drones.
     *
     * @param droneRepository repositorio de drones
     */
    public CrearDroneService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    /** {@inheritDoc} */
    @Override
    public Drone crearDrone(Drone drone) {
        return droneRepository.guardar(drone);
    }
}
