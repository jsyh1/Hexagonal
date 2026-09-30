package co.edu.poli.aplication.domain.model;

/**
 * Representa un drone especializado en actividades de agricultura.
 */
public class Agricultura extends Drone {

    private double capacidadTanque;

    public Agricultura() {
    }

    public Agricultura(int id, String serial, String modelo,
                       String fabricante, double peso,
                       double capacidadTanque) {

        super(id, serial, modelo, fabricante, peso);
        this.capacidadTanque = capacidadTanque;
    }

    public double getCapacidadTanque() {
        return capacidadTanque;
    }

    public void setCapacidadTanque(double capacidadTanque) {
        this.capacidadTanque = capacidadTanque;
    }
}