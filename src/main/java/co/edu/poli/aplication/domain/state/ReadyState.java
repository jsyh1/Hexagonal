package co.edu.poli.aplication.domain.state;

/** Estado de un dron encendido y preparado para volar. */
public final class ReadyState implements DroneState {

    @Override
    public DroneState encender() {
        throw operacionNoPermitida("encender");
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
        throw operacionNoPermitida("iniciar el aterrizaje");
    }

    @Override
    public DroneState finalizarAterrizaje() {
        throw operacionNoPermitida("finalizar el aterrizaje");
    }

    @Override
    public String nombre() {
        return "EN ESPERA";
    }
}
