package co.edu.poli.aplication.domain.state;

/** Estado de un dron que está ejecutando el proceso de aterrizaje. */
public final class LandingState extends OffState.RestrictedState {

    @Override
    public DroneState finalizarAterrizaje() {
        return new ReadyState();
    }

    @Override
    public String nombre() {
        return "ATERRIZANDO";
    }
}
