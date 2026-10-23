package co.edu.poli.aplication.service;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.port.in.ListarDronesUseCase;
import co.edu.poli.aplication.port.out.DroneRepository;
import java.util.List;

/** Implementa el caso de uso de consulta de todos los drones. */
public class ListarDronesService implements ListarDronesUseCase {

    private final DroneRepository droneRepository;

    /**
     * Crea el servicio con el repositorio que proporciona los drones.
     *
     * @param droneRepository repositorio de drones
     */
    public ListarDronesService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    /** {@inheritDoc} */
    @Override
    public List<Drone> listarDrones() {
        return droneRepository.buscarTodos();
    }
}
