package co.edu.poli.aplication.domain.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Prueba las estrategias de consumo energético.
 */
class ConsumoStrategyTest {

    @Test
    void modoAhorroDebeConsumirLaMitadDeLaDistancia() {
        ConsumoStrategy strategy = new AhorroStrategy();

        assertEquals(5.0, strategy.execute(10.0), 0.0001);
        assertEquals("Modo Ahorro", strategy.getNombre());
        assertEquals(0.5, strategy.getFactor(), 0.0001);
    }

    @Test
    void modoNormalDebeConsumirLaDistanciaCompleta() {
        ConsumoStrategy strategy = new NormalStrategy();

        assertEquals(10.0, strategy.execute(10.0), 0.0001);
        assertEquals("Modo Normal", strategy.getNombre());
        assertEquals(1.0, strategy.getFactor(), 0.0001);
    }

    @Test
    void altoRendimientoDebeAumentarElConsumo() {
        ConsumoStrategy strategy = new AltoRendimientoStrategy();

        assertEquals(15.0, strategy.execute(10.0), 0.0001);
        assertEquals(
                "Modo Alto Rendimiento",
                strategy.getNombre());
        assertEquals(1.5, strategy.getFactor(), 0.0001);
    }

    @Test
    void distanciaCeroDebeProducirConsumoCero() {
        ConsumoStrategy ahorro = new AhorroStrategy();
        ConsumoStrategy normal = new NormalStrategy();
        ConsumoStrategy rendimiento = new AltoRendimientoStrategy();

        assertEquals(0.0, ahorro.execute(0.0), 0.0001);
        assertEquals(0.0, normal.execute(0.0), 0.0001);
        assertEquals(0.0, rendimiento.execute(0.0), 0.0001);
    }
}