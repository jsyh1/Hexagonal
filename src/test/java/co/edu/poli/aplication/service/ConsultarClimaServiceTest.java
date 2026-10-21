
package co.edu.poli.aplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.poli.aplication.domain.model.weather.Weather;
import co.edu.poli.aplication.port.out.GeocodingPort;
import co.edu.poli.aplication.port.out.GeocodingPort.Coordenadas;
import co.edu.poli.aplication.port.out.WeatherApiPort;

class ConsultarClimaServiceTest {

    private FakeGeocodingPort geocodingPort;
    private FakeWeatherApiPort weatherApiPort;
    private ConsultarClimaService service;

    @BeforeEach
    void setUp() {
        geocodingPort = new FakeGeocodingPort();
        weatherApiPort = new FakeWeatherApiPort();

        service = new ConsultarClimaService(
                geocodingPort,
                weatherApiPort
        );
    }

    @Test
    void debeConsultarClimaDeUnaCiudad() throws Exception {
        String ciudad = "Bogotá";
        Coordenadas coordenadas = new Coordenadas(4.7110, -74.0721);
        Weather climaEsperado = new Weather(1L, 24.0, 55.0, 12.0, 1010.0, 9000.0);
        geocodingPort.coordenadas = coordenadas;
        weatherApiPort.clima = climaEsperado;

        Weather resultado = service.consultarClima(ciudad);

        assertSame(climaEsperado, resultado);
        assertEquals(ciudad, geocodingPort.ultimaCiudadConsultada);
        assertEquals(1, geocodingPort.invocaciones);
        assertSame(coordenadas, weatherApiPort.ultimaCoordenadaConsultada);
        assertEquals(1, weatherApiPort.invocaciones);
    }

    @Test
    void debePropagarErrorSiFallaLaGeolocalizacion() {
        String ciudad = "CiudadInventada";
        geocodingPort.error = new RuntimeException("Ciudad no encontrada");

        assertThrows(RuntimeException.class,
                () -> service.consultarClima(ciudad));

        assertEquals(ciudad, geocodingPort.ultimaCiudadConsultada);
        assertEquals(1, geocodingPort.invocaciones);
        assertEquals(0, weatherApiPort.invocaciones);
    }

    @Test
    void debePropagarErrorSiFallaLaApiDelClima() throws Exception {
        String ciudad = "Medellín";
        Coordenadas coordenadas = new Coordenadas(6.2442, -75.5812);
        geocodingPort.coordenadas = coordenadas;
        weatherApiPort.error = new RuntimeException("Error de la API");

        assertThrows(RuntimeException.class,
                () -> service.consultarClima(ciudad));

        assertEquals(ciudad, geocodingPort.ultimaCiudadConsultada);
        assertEquals(1, geocodingPort.invocaciones);
        assertSame(coordenadas, weatherApiPort.ultimaCoordenadaConsultada);
        assertEquals(1, weatherApiPort.invocaciones);
    }

    @Test
    void debeConsultarLaApiConLasCoordenadasEncontradas() throws Exception {
        String ciudad = "Cali";
        Coordenadas coordenadas = new Coordenadas(3.4516, -76.5320);
        Weather climaEsperado = new Weather(2L, 28.0, 60.0, 15.0, 1015.0, 7000.0);
        geocodingPort.coordenadas = coordenadas;
        weatherApiPort.clima = climaEsperado;

        service.consultarClima(ciudad);

        assertEquals(ciudad, geocodingPort.ultimaCiudadConsultada);
        assertEquals(1, geocodingPort.invocaciones);
        assertSame(coordenadas, weatherApiPort.ultimaCoordenadaConsultada);
        assertEquals(1, weatherApiPort.invocaciones);
    }

    private static class FakeGeocodingPort implements GeocodingPort {
        private String ultimaCiudadConsultada;
        private Coordenadas coordenadas;
        private Exception error;
        private int invocaciones;

        @Override
        public Coordenadas buscarCiudad(String ciudad) throws Exception {
            ultimaCiudadConsultada = ciudad;
            invocaciones++;
            if (error != null) {
                throw error;
            }
            return coordenadas;
        }
    }

    private static class FakeWeatherApiPort implements WeatherApiPort {
        private Coordenadas ultimaCoordenadaConsultada;
        private Weather clima;
        private Exception error;
        private int invocaciones;

        @Override
        public Weather consultarClima(Coordenadas coordenadas) throws Exception {
            ultimaCoordenadaConsultada = coordenadas;
            invocaciones++;
            if (error != null) {
                throw error;
            }
            return clima;
        }
    }
}
