
package co.edu.poli.aplication.weather;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.poli.aplication.domain.model.Drone;
import co.edu.poli.aplication.domain.model.weather.Weather;
import co.edu.poli.aplication.port.in.ConsultarClimaUseCase;

class WeatherNotificationServiceTest {

    private FakeConsultarClimaUseCase consultarClima;
    private List<Drone> dronesNotificados;
    private WeatherNotificationService service;

    @BeforeEach
    void setUp() {
        consultarClima = new FakeConsultarClimaUseCase();
        dronesNotificados = new ArrayList<>();
        service = new WeatherNotificationService(
                consultarClima,
                (drone, clima) -> dronesNotificados.add(drone)
        );
    }

    @Test
    void debeConsultarClimaYNotificarATodosLosDronesSuscritos() throws Exception {
        Drone drone1 = new Drone(1, "D-001", "Mavic", 1.5);
        Drone drone2 = new Drone(2, "D-002", "Phantom", 2.0);
        Weather clima = new Weather(99L, 23.5, 50.0, 18.0, 1012.0, 8000.0);
        consultarClima.clima = clima;

        service.suscribir(drone1);
        service.suscribir(drone2);

        assertDoesNotThrow(() -> service.actualizarDesdeCiudad("Bogotá"));
        assertEquals("Bogotá", consultarClima.ultimaCiudadConsultada);
        assertEquals(1, consultarClima.invocaciones);
        assertEquals(2, dronesNotificados.size());
        assertTrue(dronesNotificados.contains(drone1));
        assertTrue(dronesNotificados.contains(drone2));
    }

    @Test
    void debeIgnorarSuscripcionesNulasYNoNotificarCuandoSeDaDeBaja() throws Exception {
        Drone drone = new Drone(3, "D-003", "Mavic", 1.2);
        Weather clima = new Weather(100L, 20.0, 60.0, 12.0, 1008.0, 7000.0);
        consultarClima.clima = clima;

        service.suscribir(null);
        service.suscribir(drone);
        service.darDeBaja(drone);

        service.actualizarDesdeCiudad("Medellín");

        assertEquals("Medellín", consultarClima.ultimaCiudadConsultada);
        assertEquals(1, consultarClima.invocaciones);
        assertEquals(List.of(), dronesNotificados);
    }

    @Test
    void debePropagarErrorSiFallaLaConsultaYNoNotificar() {
        consultarClima.error = new RuntimeException("Error de consulta");

        assertThrows(RuntimeException.class,
                () -> service.actualizarDesdeCiudad("Cali"));

        assertEquals("Cali", consultarClima.ultimaCiudadConsultada);
        assertEquals(1, consultarClima.invocaciones);
        assertEquals(List.of(), dronesNotificados);
    }

    @Test
    void debePropagarExcepcionesComprobadas() {
        consultarClima.error = new java.io.IOException("API no disponible");

        assertThrows(java.io.IOException.class,
                () -> service.actualizarDesdeCiudad("Barranquilla"));

        assertEquals("Barranquilla", consultarClima.ultimaCiudadConsultada);
        assertEquals(1, consultarClima.invocaciones);
        assertEquals(List.of(), dronesNotificados);
    }

    private static class FakeConsultarClimaUseCase implements ConsultarClimaUseCase {
        private String ultimaCiudadConsultada;
        private Weather clima;
        private Exception error;
        private int invocaciones;

        @Override
        public Weather consultarClima(String ciudad) throws Exception {
            ultimaCiudadConsultada = ciudad;
            invocaciones++;
            if (error != null) {
                throw error;
            }
            return clima;
        }
    }
}
