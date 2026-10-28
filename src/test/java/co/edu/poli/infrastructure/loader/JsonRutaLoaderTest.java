package co.edu.poli.infrastructure.loader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import co.edu.poli.aplication.domain.strategy.PuntoGeografico;

/**
 * Prueba la carga y validación de rutas desde archivos JSON.
 */
class JsonRutaLoaderTest {

    @TempDir
    Path directorioTemporal;

    private final JsonRutaLoader loader = new JsonRutaLoader();

    @Test
    void debeCargarElOrigenYElDestinoDesdeJson() throws IOException {
        String json = """
                {
                  "origen": {
                    "nombre": "Punto Origen",
                    "latitud": 4.60971,
                    "longitud": -74.08175
                  },
                  "destino": {
                    "nombre": "Punto Destino",
                    "latitud": 4.70159,
                    "longitud": -74.14690
                  }
                }
                """;

        Path archivo = directorioTemporal.resolve("ruta.json");
        Files.writeString(archivo, json);

        List<PuntoGeografico> puntos =
                loader.cargar(archivo.toFile());

        assertEquals(2, puntos.size());

        assertEquals("Punto Origen", puntos.get(0).getNombre());
        assertEquals(4.60971, puntos.get(0).getLatitud(), 0.000001);
        assertEquals(-74.08175, puntos.get(0).getLongitud(), 0.000001);

        assertEquals("Punto Destino", puntos.get(1).getNombre());
        assertEquals(4.70159, puntos.get(1).getLatitud(), 0.000001);
        assertEquals(-74.14690, puntos.get(1).getLongitud(), 0.000001);
    }

    @Test
    void debeRechazarUnJsonSinOrigen() throws IOException {
        String json = """
                {
                  "destino": {
                    "nombre": "Punto Destino",
                    "latitud": 4.70159,
                    "longitud": -74.14690
                  }
                }
                """;

        Path archivo = directorioTemporal.resolve("sin-origen.json");
        Files.writeString(archivo, json);

        assertThrows(
                IllegalArgumentException.class,
                () -> loader.cargar(archivo.toFile()));
    }

    @Test
    void debeRechazarUnaLatitudFueraDelRango() throws IOException {
        String json = """
                {
                  "origen": {
                    "nombre": "Punto Origen",
                    "latitud": 95.0,
                    "longitud": -74.08175
                  },
                  "destino": {
                    "nombre": "Punto Destino",
                    "latitud": 4.70159,
                    "longitud": -74.14690
                  }
                }
                """;

        Path archivo = directorioTemporal.resolve("latitud-invalida.json");
        Files.writeString(archivo, json);

        assertThrows(
                IllegalArgumentException.class,
                () -> loader.cargar(archivo.toFile()));
    }

    @Test
    void debeRechazarUnJsonMalFormado() throws IOException {
        Path archivo = directorioTemporal.resolve("malformado.json");
        Files.writeString(archivo, "{");

        assertThrows(
                IOException.class,
                () -> loader.cargar(archivo.toFile()));
    }

    @Test
    void debeRechazarUnArchivoInexistente() {
        Path archivo =
                directorioTemporal.resolve("no-existe.json");

        assertThrows(
                IllegalArgumentException.class,
                () -> loader.cargar(archivo.toFile()));
    }
}