package co.edu.poli.aplication.port.out;

import co.edu.poli.aplication.domain.model.Drone;
import java.util.List;

/**
 * Puerto de salida único para la persistencia y gestión externa de los Drones.
 * Remueve el uso de Optional para facilitar el flujo directo de datos en JavaFX.
 */
public interface DroneRepository {

    Drone guardar(Drone drone);

    Drone buscarPorId(int id);

    List<Drone> buscarTodos();

    Drone modificar(int id, Drone drone);

    void eliminarPorId(int id);
}
