package co.edu.poli.aplication.service;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.port.in.ObtenerDronePorIdUseCase;
import co.edu.poli.aplication.port.out.DroneRepository;

/** Implementa la consulta de un drone por identificador. */
public class ObtenerDronePorIdService implements ObtenerDronePorIdUseCase {

    private final DroneRepository droneRepository;

    /**
     * Crea el servicio con el repositorio que realizará la búsqueda.
     *
     * @param droneRepository repositorio de drones
     */
    public ObtenerDronePorIdService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    /** {@inheritDoc} */
    @Override
    public Drone obtenerDronePorId(int id) {
        return droneRepository.buscarPorId(id);
    }
}
