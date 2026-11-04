package co.edu.poli.aplication.domain.state;

/** Estado de un dron apagado. */
public final class OffState implements DroneState {

    @Override
    public DroneState encender() {
        return new ReadyState();
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
        return operacionNoPermitida("finalizar el aterrizaje");
    }

    @Override
    public String nombre() {
        return "APAGADO";
    }
}
