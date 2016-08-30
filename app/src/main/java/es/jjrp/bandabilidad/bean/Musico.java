package es.jjrp.bandabilidad.bean;

/**
 * Representa a un músico
 * Created by Juanjo on 23/08/2016.
 */
public class Musico {
    public long _id;
    public String nombre;
    public String apellidos;
    public String gender;

    public String toString() {
        return "" + _id + "_" + nombre + " - " + apellidos + ". " + (gender != null ? gender : "");
    }

}

