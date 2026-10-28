package co.edu.poli.aplication.domain.strategy;

/**
 * Estrategia de consumo energético estándar.
 */
public class NormalStrategy implements ConsumoStrategy {

    @Override
    public double execute(double distanciaKm) {
        return distanciaKm * 1.0;
    }

    @Override
    public String getNombre() {
        return "Modo Normal";
    }

    @Override
    public double getFactor() {
        return 1.0;
    }
}