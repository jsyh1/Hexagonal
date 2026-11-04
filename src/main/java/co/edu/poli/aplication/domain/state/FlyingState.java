package co.edu.poli.aplication.domain.state;

/** Estado de un dron en vuelo. */
public final class FlyingState implements DroneState {

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
        return new LandingState();
    }

    @Override
    public DroneState finalizarAterrizaje() {
        return operacionNoPermitida("finalizar el aterrizaje");
    }

    @Override
    public String nombre() {
        return "VOLANDO";
    }
}
