package es.jjrp.bandabilidad.bean;

import java.util.Date;


/**
 * Representa a una actuacion
 * Created by Juanjo on 03/09/2016.
 */
public class Actuacion {
    public long _id;
    public TIPO tipo;
    public String nombre;
    public Date fecha;
    public int horas;
    public String ciudad;
    public double precio;


    public enum TIPO {
        PROCESION, PASACALLES, CONCIERTO, ROMERIA;

        public static TIPO getTipo(int value) {
            TIPO[] values = TIPO.values();
            for (TIPO t : values) {
                if (t.ordinal() == value) {
                    return t;
                }
            }
            return null;
        }
    }
}

