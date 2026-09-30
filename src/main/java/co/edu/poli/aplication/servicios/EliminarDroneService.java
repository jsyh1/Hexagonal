package co.edu.poli.aplication.servicios;

import co.edu.poli.aplication.port.in.EliminarDroneUseCase;
import co.edu.poli.aplication.port.out.DroneRepository;

public class EliminarDroneService implements EliminarDroneUseCase {

    private final DroneRepository droneRepository;

    public EliminarDroneService(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Override
    public void eliminarDrone(int id) {
        droneRepository.eliminarPorId(id);
    }
}
