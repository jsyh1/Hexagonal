package co.edu.poli.aplication.domain.model;

/**
 * Representa un drone dentro del sistema.
 */
public class Drone {

    private int id;
    private String serial;
    private String modelo;
    private double peso;

    public Drone() {
    }

    public Drone(int id, String serial, String modelo, double peso) {
        this.id = id;
        this.serial = serial;
        this.modelo = modelo;
        this.peso = peso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }
}