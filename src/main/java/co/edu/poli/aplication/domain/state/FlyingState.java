package co.edu.poli.aplication.domain.state;

/** Estado de un dron en vuelo. */
public final class FlyingState extends OffState.RestrictedState {

    @Override
    public DroneState iniciarAterrizaje() {
        return new LandingState();
    }

    @Override
    public DroneState finalizarAterrizaje() {
        throw operacionNoPermitida("finalizar el aterrizaje");
    }

    @Override
    public String nombre() {
        return "VOLANDO";
    }
}
