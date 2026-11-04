package co.edu.poli.aplication.domain.state;

/** Estado de un dron encendido y preparado para volar. */
public final class ReadyState implements DroneState {

    @Override
    public DroneState encender() {
        return operacionNoPermitida("encender");
    }

    @Override
    public DroneState apagar() {
        return new OffState();
    }

    @Override
    public DroneState despegar() {
        return new FlyingState();
    }

    @Override
    public DroneState iniciarAterrizaje() {
        return operacionNoPermitida("iniciar el aterrizaje");
    }

    @Override
    public DroneState finalizarAterrizaje() {
        return operacionNoPermitida("finalizar el aterrizaje");
    }

    @Override
    public String nombre() {
        return "EN ESPERA";
    }
}
