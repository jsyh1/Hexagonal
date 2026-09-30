package co.edu.poli.aplication.port.in;

import co.edu.poli.aplication.domain.model.Drone;
import java.util.List;

/**
 * Caso de uso para obtener la lista completa de drones.
 */
public interface ListarDronesUseCase {
    List<Drone> listarDrones();
}
