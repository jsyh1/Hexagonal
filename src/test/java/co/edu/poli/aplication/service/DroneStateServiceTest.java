package co.edu.poli.aplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.poli.aplication.domain.model.Drone;

class DroneStateServiceTest {

    private DroneStateService service;
    private Drone drone;

    @BeforeEach
    void setUp() {
        service = new DroneStateService();
        drone = new Drone(1, "SERIAL-1", "Modelo", 1.0);
        service.registrarDrone(drone);
    }

    @Test
    void droneRegistradoIniciaApagado() {
        assertEquals("APAGADO", service.obtenerEstado(drone));
    }

    @Test
    void permiteCompletarElCicloOperativo() {
        service.encender(drone);
        assertEquals("EN ESPERA", service.obtenerEstado(drone));

        service.despegar(drone);
        assertEquals("VOLANDO", service.obtenerEstado(drone));

        service.iniciarAterrizaje(drone);
        assertEquals("ATERRIZANDO", service.obtenerEstado(drone));

        service.finalizarAterrizaje(drone);
        assertEquals("EN ESPERA", service.obtenerEstado(drone));

        service.apagar(drone);
        assertEquals("APAGADO", service.obtenerEstado(drone));
    }

    @Test
    void rechazaTransicionesNoPermitidasSinCambiarElEstado() {
        assertThrows(
                IllegalStateException.class,
                () -> service.despegar(drone));
        assertEquals("APAGADO", service.obtenerEstado(drone));

        service.encender(drone);
        assertThrows(
                IllegalStateException.class,
                () -> service.iniciarAterrizaje(drone));
        assertEquals("EN ESPERA", service.obtenerEstado(drone));

        service.despegar(drone);
        assertThrows(
                IllegalStateException.class,
                () -> service.apagar(drone));
        assertEquals("VOLANDO", service.obtenerEstado(drone));

        service.iniciarAterrizaje(drone);
        assertThrows(
                IllegalStateException.class,
                () -> service.despegar(drone));
        assertEquals("ATERRIZANDO", service.obtenerEstado(drone));
    }

    @Test
    void registrarNuevamenteNoReiniciaUnDronYaOperativo() {
        service.encender(drone);

        service.registrarDrone(drone);

        assertEquals("EN ESPERA", service.obtenerEstado(drone));
    }

    @Test
    void mantieneEstadosIndependientesParaCadaDron() {
        Drone segundoDrone = new Drone(2, "SERIAL-2", "Modelo", 1.0);
        service.registrarDrone(segundoDrone);

        service.encender(drone);

        assertEquals("EN ESPERA", service.obtenerEstado(drone));
        assertEquals("APAGADO", service.obtenerEstado(segundoDrone));
    }

    @Test
    void rechazaIdsInvalidosYDronesNoRegistrados() {
        assertThrows(
                IllegalArgumentException.class,
                () -> service.registrarDrone(
                        new Drone(0, "SERIAL-0", "Modelo", 1.0)));
        assertThrows(
                IllegalArgumentException.class,
                () -> service.obtenerEstado(
                        new Drone(2, "SERIAL-2", "Modelo", 1.0)));
        assertThrows(
                IllegalArgumentException.class,
                () -> service.encender(
                        new Drone(2, "SERIAL-2", "Modelo", 1.0)));
    }

    @Test
    void alEliminarUnDronDejaDeExponerSuEstado() {
        service.eliminarDrone(drone);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.obtenerEstado(drone));
    }
}
