package co.edu.poli.aplication.servicios;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.port.in.ListarDronesUseCase;
import co.edu.poli.aplication.port.out.DroneRepository;
import java.util.List;

public class ListarDronesService implements ListarDronesUseCase {

    private final DroneRepository droneRepository;

    public ListarDronesService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Override
    public List<Drone> listarDrones() {
        return droneRepository.buscarTodos();
    }
}
