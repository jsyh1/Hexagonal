package co.edu.poli.aplication.domain.state;

/** Estado de un dron que está ejecutando el proceso de aterrizaje. */
public final class LandingState implements DroneState {

    @Override
    public DroneState encender() {
        return operacionNoPermitida("encender");
    }

    @Override
    public DroneState apagar() {
        return operacionNoPermitida("apagar");
    }

    @Override
    public DroneState despegar() {
        return operacionNoPermitida("despegar");
    }

    @Override
    public DroneState iniciarAterrizaje() {
        return operacionNoPermitida("iniciar el aterrizaje");
    }

    @Override
    public DroneState finalizarAterrizaje() {
        return new ReadyState();
    }

    @Override
    public String nombre() {
        return "ATERRIZANDO";
    }
}
