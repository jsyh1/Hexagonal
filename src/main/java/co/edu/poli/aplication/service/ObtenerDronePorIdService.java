package co.edu.poli.aplication.service;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.port.in.ObtenerDronePorIdUseCase;
import co.edu.poli.aplication.port.out.DroneRepository;

public class ObtenerDronePorIdService implements ObtenerDronePorIdUseCase {

    private final DroneRepository droneRepository;

    public ObtenerDronePorIdService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Override
    public Drone obtenerDronePorId(int id) {
        return droneRepository.buscarPorId(id);
    }
}
