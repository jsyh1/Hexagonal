package co.edu.poli.aplication.mediator.components;

import co.edu.poli.aplication.mediator.DroneMediator;

/**
 * Sistema de navegación: prepara la ruta del drone.
 */
public class NavigationSystem {

    private final DroneMediator mediator;

    public NavigationSystem(DroneMediator mediator) {
        this.mediator = mediator;
    }

    public String prepararRuta() {
        String resultado = "Navegación: ruta preparada.";
        mediator.notificar(this, "RUTA_PREPARADA");
        return resultado;
    }
}