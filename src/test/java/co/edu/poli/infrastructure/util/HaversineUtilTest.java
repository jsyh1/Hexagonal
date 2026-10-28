package co.edu.poli.infrastructure.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import co.edu.poli.aplication.domain.strategy.PuntoGeografico;

/**
 * Prueba el cálculo de distancia geográfica.
 */
class HaversineUtilTest {

    @Test
    void debeCalcularLaDistanciaEntreBogotaYElDestino() {
        PuntoGeografico origen = new PuntoGeografico(
                "Punto Origen",
                4.60971,
                -74.08175);

        PuntoGeografico destino = new PuntoGeografico(
                "Punto Destino",
                4.70159,
                -74.14690);

        double distancia =
                HaversineUtil.calcularDistanciaKm(origen, destino);

        assertEquals(12.51055, distancia, 0.01);
    }

    @Test
    void dosPuntosIgualesDebenTenerDistanciaCero() {
        PuntoGeografico origen = new PuntoGeografico(
                "Origen",
                4.60971,
                -74.08175);

        PuntoGeografico destino = new PuntoGeografico(
                "Destino",
                4.60971,
                -74.08175);

        assertEquals(
                0.0,
                HaversineUtil.calcularDistanciaKm(origen, destino),
                0.0001);
    }

    @Test
    void debeRechazarUnPuntoNulo() {
        PuntoGeografico destino = new PuntoGeografico(
                "Destino",
                4.70159,
                -74.14690);

        assertThrows(
                IllegalArgumentException.class,
                () -> HaversineUtil.calcularDistanciaKm(
                        null, destino));
    }

    @Test
    void debeRechazarUnaLatitudFueraDelRango() {
        PuntoGeografico origen = new PuntoGeografico(
                "Origen",
                91.0,
                -74.08175);

        PuntoGeografico destino = new PuntoGeografico(
                "Destino",
                4.70159,
                -74.14690);

        assertThrows(
                IllegalArgumentException.class,
                () -> HaversineUtil.calcularDistanciaKm(
                        origen, destino));
    }

    @Test
    void debeRechazarUnaLongitudFueraDelRango() {
        PuntoGeografico origen = new PuntoGeografico(
                "Origen",
                4.60971,
                -181.0);

        PuntoGeografico destino = new PuntoGeografico(
                "Destino",
                4.70159,
                -74.14690);

        assertThrows(
                IllegalArgumentException.class,
                () -> HaversineUtil.calcularDistanciaKm(
                        origen, destino));
    }
}