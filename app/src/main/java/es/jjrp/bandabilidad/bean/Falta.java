package es.jjrp.bandabilidad.bean;

import java.io.Serializable;

/**
 * Created by Juanjo on 04/10/2016.
 */

public class Falta implements Serializable {

    public long _id;
    public long musicoId;
    public long actuacionId;
    public boolean tarde;

    public Falta(long _id, long musicoId, long actuacionId, boolean tarde) {
        this._id = _id;
        this.musicoId = musicoId;
        this.actuacionId = actuacionId;
        this.tarde = tarde;
    }

    public Falta() {
    }
}
