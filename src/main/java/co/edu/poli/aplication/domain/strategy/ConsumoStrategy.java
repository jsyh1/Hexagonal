package co.edu.poli.aplication.domain.strategy;

/**
 * Define el contrato para las estrategias de consumo energético.
 */
public interface ConsumoStrategy {

    /**
     * Calcula el consumo estimado del recorrido.
     *
     * @param distanciaKm distancia del recorrido en kilómetros
     * @return consumo estimado
     */
    double execute(double distanciaKm);

    /**
     * Obtiene el nombre de la estrategia.
     *
     * @return nombre de la estrategia
     */
    String getNombre();

    /**
     * Obtiene el factor de consumo de la estrategia.
     *
     * @return factor aplicado a la distancia
     */
    double getFactor();
}