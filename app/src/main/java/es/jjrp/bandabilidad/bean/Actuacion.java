package es.jjrp.bandabilidad.bean;

import java.io.Serializable;
import java.util.Date;


/**
 * Representa a una actuacion
 * Created by Juanjo on 03/09/2016.
 */
public class Actuacion implements Serializable {
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

        public static String[] stringValues() {
            String[] res = new String[TIPO.values().length];
            int pos = 0;
            for (TIPO t : TIPO.values()) {
                res[pos++] = t.name();
            }
            return res;
        }
    }
}

