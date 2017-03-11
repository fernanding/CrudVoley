package es.vcarmen.interfazaparcamiento.Modelo;

/**
 * Created by faper-pc on 08/03/2017.
 */

public class Propietarios {
    private String dni;
    private String nombre;
    private String apellidos;


    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Propietarios() {


    }


    public Propietarios(String dni, String nombre, String apellidos) {
        super();
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }
    @Override
    public String toString() {
        return "{" +
                "dni:'" + dni + '\'' +
                ", nombre:'" + nombre + '\'' +
                ", apellidos:'" + apellidos + '\'' +
                '}';
    }
}
