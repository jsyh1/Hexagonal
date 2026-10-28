package co.edu.poli.aplication.domain.strategy;

/**
 * Contexto del patrón Strategy para calcular el consumo energético.
 */
public class CalcularConsumoUseCase {

    private ConsumoStrategy strategy;

    /**
     * Instala la estrategia que se utilizará en el cálculo.
     *
     * @param strategy estrategia seleccionada
     */
    public void setStrategy(ConsumoStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException(
                    "La estrategia no puede ser nula.");
        }

        this.strategy = strategy;
    }

    /**
     * Ejecuta el cálculo mediante la estrategia activa.
     *
     * @param distanciaKm distancia del recorrido en kilómetros
     * @return consumo energético estimado
     */
    public double calcularConsumo(double distanciaKm) {

        if (strategy == null) {
            throw new IllegalStateException(
                    "Primero debes seleccionar una estrategia.");
        }

        if (!Double.isFinite(distanciaKm) || distanciaKm < 0) {
            throw new IllegalArgumentException(
                    "La distancia debe ser un número válido no negativo.");
        }

        return strategy.execute(distanciaKm);
    }
}