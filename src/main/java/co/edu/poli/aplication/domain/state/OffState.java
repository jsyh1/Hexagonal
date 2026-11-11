package co.edu.poli.aplication.domain.state;

/** Estado de un dron apagado. */
public final class OffState implements DroneState {

    @Override
    public DroneState encender() {
        return new ReadyState();
    }

    @Override
    public DroneState apagar() {
        throw operacionNoPermitida("apagar");
    }

    @Override
    public DroneState despegar() {
        throw operacionNoPermitida("despegar");
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
        return "APAGADO";
    }

    static abstract class RestrictedState implements DroneState {
        @Override
        public DroneState encender() {
            throw operacionNoPermitida("encender");
        }

        @Override
        public DroneState apagar() {
            throw operacionNoPermitida("apagar");
        }

        @Override
        public DroneState despegar() {
            throw operacionNoPermitida("despegar");
        }

        @Override
        public DroneState iniciarAterrizaje() {
            throw operacionNoPermitida("iniciar el aterrizaje");
        }

    }
}
