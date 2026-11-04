package co.edu.poli.aplication.mediator;

import co.edu.poli.aplication.domain.model.Drone;

/**
 * Mediador (patrón GoF Mediator) que centraliza la comunicación
 * entre los sistemas que participan en una operación de drones.
 */
public interface DroneMediator {

    /**
     * Recibe el aviso de un componente y decide cómo continuar el proceso.
     *
     * @param sender componente que genera el evento
     * @param evento nombre del evento ocurrido
     */
    void notificar(Object sender, String evento);

    /**
     * Inicia la operación de vigilancia de un drone.
     *
     * @param drone drone que ejecutará la operación
     * @return resumen de la operación
     */
    String iniciarOperacion(Drone drone);
}