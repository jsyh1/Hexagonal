package co.edu.poli.aplication.port.out;

import co.edu.poli.aplication.domain.model.Drone;
import java.util.List;

/**
 * Puerto de salida único para la persistencia y gestión externa de los Drones.
 * Remueve el uso de Optional para facilitar el flujo directo de datos en JavaFX.
 */
public interface DroneRepository {

    /**
     * Persiste un drone nuevo.
     *
     * @param drone drone que se guardará
     * @return drone guardado, incluido su identificador persistido
     */
    Drone guardar(Drone drone);

    /**
     * Busca un drone por su identificador.
     *
     * @param id identificador que se consultará
     * @return drone encontrado o {@code null} si no existe
     */
    Drone buscarPorId(int id);

    /**
     * Recupera todos los drones persistidos.
     *
     * @return lista de drones; vacía si no hay registros
     */
    List<Drone> buscarTodos();

    /**
     * Actualiza los datos de un drone.
     *
     * @param id identificador del drone que se actualizará
     * @param drone nuevos datos del drone
     * @return drone actualizado o {@code null} si no existe
     */
    Drone modificar(int id, Drone drone);

    /**
     * Elimina un drone por su identificador.
     *
     * @param id identificador del drone que se eliminará
     */
    void eliminarPorId(int id);
}
