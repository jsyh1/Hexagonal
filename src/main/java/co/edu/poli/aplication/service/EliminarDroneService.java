package co.edu.poli.aplication.service;

import co.edu.poli.aplication.port.in.EliminarDroneUseCase;
import co.edu.poli.aplication.port.out.DroneRepository;

/** Implementa el caso de uso de eliminación de drones. */
public class EliminarDroneService implements EliminarDroneUseCase {

    private final DroneRepository droneRepository;

    /**
     * Crea el servicio con el repositorio de drones.
     *
     * @param droneRepository repositorio de drones
     */
    public EliminarDroneService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    /** {@inheritDoc} */
    @Override
    public void eliminarDrone(int id) {
        droneRepository.eliminarPorId(id);
    }
}
