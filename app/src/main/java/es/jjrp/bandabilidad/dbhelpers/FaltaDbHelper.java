package es.jjrp.bandabilidad.dbhelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.jjrp.bandabilidad.bean.Falta;

/**
 * Created by Juanjo on 02/10/2016.
 */

public class FaltaDbHelper extends BaseDbHelper {


    public FaltaDbHelper(Context context) {
        super(context);
    }


    public void createFalta(long idMusico, long idActuacion, boolean tarde) {

        ContentValues initialValues = new ContentValues();

        initialValues.put("musico_id", idMusico);
        initialValues.put("actuacion_id", idActuacion);
        initialValues.put("tarde", tarde ? 1 : 0);

        getWritableDatabase().insert(DATABASE_TABLE_FALTA, null, initialValues);
    }

    public List<Falta> obtenerFaltasPorActuacion(long actuacionId) {
        ArrayList<Falta> ret = new ArrayList<>();
        try {
            Cursor c =
                    getReadableDatabase().query(DATABASE_TABLE_FALTA, new String[]{
                                    "_id", "musico_id", "actuacion_id", "tarde"}, "actuacion_id=?",
                            new String[]{"" + actuacionId}, null, null, null);
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                Falta row = new Falta();
                int posCol = 0;
                row._id = c.getLong(posCol++);
                row.musicoId = c.getInt(posCol++);
                row.actuacionId = c.getLong(posCol++);
                row.tarde = c.getInt(posCol++) == 1 ? true : false;
                ret.add(row);
                c.moveToNext();
            }
            c.close();
        } catch (Exception e) {
            Log.e("Exception on query", e.toString());
        }
        return ret;
    }

    public boolean tieneFalta(long musicoId, long actuacionId) {
        boolean ret = false;
        try {
            Cursor c =
                    getReadableDatabase().query(DATABASE_TABLE_FALTA, new String[]{
                                    "_id", "musico_id", "actuacion_id", "tarde"},
                            "musico_id=" + musicoId + " and actuacion_id=" + actuacionId,
                            null, null, null, null);
            int numRows = c.getCount();
            if (numRows > 0) {
                ret = true;
            }
            c.close();
        } catch (Exception e) {
            Log.e("Exception on query", e.toString());
        }
        return ret;
    }

    public void deleteFaltaById(long rowId) {
        getWritableDatabase().delete(DATABASE_TABLE_FALTA, "_id=" + rowId, null);
    }

}
