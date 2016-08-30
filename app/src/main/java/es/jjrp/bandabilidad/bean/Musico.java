package es.jjrp.bandabilidad.bean;

/**
 * Created by Juanjo on 23/08/2016.
 */
public class Musico {
    public long _id;
    public String nombre;
    public String apellidos;
    public String gender;

    public String toString() {
        return "" + nombre + " - " + apellidos + ". " + gender;
    }

}

