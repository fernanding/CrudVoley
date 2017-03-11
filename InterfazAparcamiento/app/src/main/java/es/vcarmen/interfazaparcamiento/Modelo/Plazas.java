package es.vcarmen.interfazaparcamiento.Modelo;

/**
 * Created by faper-pc on 11/03/2017.
 */

public class Plazas {
    private int numplaza;
    private int planta;
    private float mantenimiento;
public String  Fdni;

    public Plazas() {


    }


    public Plazas(int numplaza, int planta, int mantenimiento, String fdni) {
        super();
        this.numplaza =numplaza;
        this.planta = planta;
        this.mantenimiento = mantenimiento;
        this.Fdni=fdni;
    }
    @Override
    public String toString() {
        return "{" +
                "numplaza:'" + numplaza + '\'' +
                ", planta:'" + planta + '\'' +
                ", mantenimiento:'" + mantenimiento + '\'' +
                ", dnipropietario:'" + Fdni + '\'' +
                '}';
    }
}




