package co.edu.poli.aplication.domain.strategy;

/**
 * Estrategia que prioriza el rendimiento del dron
 * a costa de un mayor consumo energético.
 */
public class AltoRendimientoStrategy implements ConsumoStrategy {

    @Override
    public double execute(double distanciaKm) {
        return distanciaKm * 1.5;
    }

    @Override
    public String getNombre() {
        return "Modo Alto Rendimiento";
    }

    @Override
    public double getFactor() {
        return 1.5;
    }
}