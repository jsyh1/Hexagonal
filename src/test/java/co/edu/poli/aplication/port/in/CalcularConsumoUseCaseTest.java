package co.edu.poli.aplication.port.in;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import co.edu.poli.aplication.domain.strategy.AhorroStrategy;
import co.edu.poli.aplication.domain.strategy.NormalStrategy;

/**
 * Prueba el contexto del patrón Strategy.
 */
class CalcularConsumoUseCaseTest {

    @Test
    void debeExigirUnaEstrategiaAntesDeCalcular() {
        CalcularConsumoUseCase contexto =
                new CalcularConsumoUseCase();

        assertThrows(
                IllegalStateException.class,
                () -> contexto.calcularConsumo(10.0));
    }

    @Test
    void debeCalcularConLaEstrategiaSeleccionada() {
        CalcularConsumoUseCase contexto =
                new CalcularConsumoUseCase();

        contexto.setStrategy(new AhorroStrategy());

        assertEquals(
                5.0,
                contexto.calcularConsumo(10.0),
                0.0001);
    }

    @Test
    void debePermitirCambiarLaEstrategia() {
        CalcularConsumoUseCase contexto =
                new CalcularConsumoUseCase();

        contexto.setStrategy(new AhorroStrategy());

        assertEquals(
                5.0,
                contexto.calcularConsumo(10.0),
                0.0001);

        contexto.setStrategy(new NormalStrategy());

        assertEquals(
                10.0,
                contexto.calcularConsumo(10.0),
                0.0001);
    }

    @Test
    void debeRechazarUnaEstrategiaNula() {
        CalcularConsumoUseCase contexto =
                new CalcularConsumoUseCase();

        assertThrows(
                IllegalArgumentException.class,
                () -> contexto.setStrategy(null));
    }

    @Test
    void debeRechazarDistanciasNegativas() {
        CalcularConsumoUseCase contexto =
                new CalcularConsumoUseCase();

        contexto.setStrategy(new NormalStrategy());

        assertThrows(
                IllegalArgumentException.class,
                () -> contexto.calcularConsumo(-1.0));
    }

    @Test
    void debeRechazarDistanciasNoFinitas() {
        CalcularConsumoUseCase contexto =
                new CalcularConsumoUseCase();

        contexto.setStrategy(new NormalStrategy());

        assertThrows(
                IllegalArgumentException.class,
                () -> contexto.calcularConsumo(Double.NaN));

        assertThrows(
                IllegalArgumentException.class,
                () -> contexto.calcularConsumo(Double.POSITIVE_INFINITY));
    }
}