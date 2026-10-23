package co.edu.poli.aplication.port.in;

/**
 * Caso de uso para remover un drone del sistema.
 */
public interface EliminarDroneUseCase {

    /**
     * Elimina un drone mediante su identificador.
     *
     * @param id identificador del drone que se eliminará
     */
    void eliminarDrone(int id);
}
