package co.edu.poli.aplication.domain.model;

/**
 * Representa un piloto encargado de operar un drone.
 */
public class Piloto {

    private int id;
    private String nombre;
    private String telefono;
    private String licencia;

    public Piloto() {
    }

    public Piloto(int id, String nombre, String telefono, String licencia) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.licencia = licencia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLicencia() {
        return licencia;
    }

    public void setLicencia(String licencia) {
        this.licencia = licencia;
    }
}