package co.edu.poli.aplication.domain.state;

/** Comportamiento disponible para un estado operativo de un dron. */
public interface DroneState {

    DroneState encender();

    DroneState apagar();

    DroneState despegar();

    DroneState iniciarAterrizaje();

    DroneState finalizarAterrizaje();

    String nombre();

    default IllegalStateException operacionNoPermitida(String operacion) {
        return new IllegalStateException(
                "No se puede " + operacion + " cuando el dron está "
                        + nombre() + ".");
    }
}
