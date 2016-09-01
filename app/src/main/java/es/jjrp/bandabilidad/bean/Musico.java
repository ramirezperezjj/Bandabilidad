package es.jjrp.bandabilidad.bean;

/**
 * Representa a un músico
 * Created by Juanjo on 23/08/2016.
 */
public class Musico {
    public long _id;
    public String nombre;
    public String apellidos;
    public int orden;

    public Musico(long _id, String nombre, String apellidos, int orden) {
        this._id = _id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.orden = orden;
    }

    public Musico() {

    }

    public String toString() {
        return "" + orden + ": " + nombre + " - " + apellidos;
    }

}

