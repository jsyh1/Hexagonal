package co.edu.poli.aplication.domain.strategy;

/**
 * Estrategia de bajo consumo energético.
 */
public class AhorroStrategy implements ConsumoStrategy {

    @Override
    public double execute(double distanciaKm) {
        return distanciaKm * 0.5;
    }

    @Override
    public String getNombre() {
        return "Modo Ahorro";
    }

    @Override
    public double getFactor() {
        return 0.5;
    }
}