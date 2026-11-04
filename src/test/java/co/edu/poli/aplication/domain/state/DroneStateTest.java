package co.edu.poli.aplication.domain.state;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class DroneStateTest {

    @Test
    void apagadoSoloPermiteEncender() {
        DroneState state = new OffState();

        assertEquals("APAGADO", state.nombre());
        assertEquals("EN ESPERA", state.encender().nombre());
        assertOperacionesNoPermitidas(
                state::apagar,
                state::despegar,
                state::iniciarAterrizaje,
                state::finalizarAterrizaje);
    }

    @Test
    void enEsperaPermiteApagarODespegar() {
        DroneState state = new ReadyState();

        assertEquals("EN ESPERA", state.nombre());
        assertEquals("APAGADO", state.apagar().nombre());
        assertEquals("VOLANDO", state.despegar().nombre());
        assertOperacionesNoPermitidas(
                state::encender,
                state::iniciarAterrizaje,
                state::finalizarAterrizaje);
    }

    @Test
    void volandoSoloPermiteIniciarAterrizaje() {
        DroneState state = new FlyingState();

        assertEquals("VOLANDO", state.nombre());
        assertEquals("ATERRIZANDO", state.iniciarAterrizaje().nombre());
        assertOperacionesNoPermitidas(
                state::encender,
                state::apagar,
                state::despegar,
                state::finalizarAterrizaje);
    }

    @Test
    void aterrizandoSoloPermiteFinalizarAterrizaje() {
        DroneState state = new LandingState();

        assertEquals("ATERRIZANDO", state.nombre());
        assertEquals("EN ESPERA", state.finalizarAterrizaje().nombre());
        assertOperacionesNoPermitidas(
                state::encender,
                state::apagar,
                state::despegar,
                state::iniciarAterrizaje);
    }

    private void assertOperacionesNoPermitidas(
            Runnable... operaciones) {
        for (Runnable operacion : operaciones) {
            assertThrows(IllegalStateException.class, operacion::run);
        }
    }
}
