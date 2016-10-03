package es.jjrp.bandabilidad.dbhelpers;

import android.content.ContentValues;
import android.content.Context;

/**
 * Created by Juanjo on 02/10/2016.
 */

public class FaltaDbHelper extends BaseDbHelper {


    public FaltaDbHelper(Context context) {
        super(context);
    }


    public void createActuacion(long idMusico, long idActuacion, boolean tarde) {

        ContentValues initialValues = new ContentValues();

        initialValues.put("musico_id", idMusico);
        initialValues.put("actuacion_id", idActuacion);
        initialValues.put("tarde", tarde ? 1 : 0);

        getWritableDatabase().insert(DATABASE_TABLE_FALTA, null, initialValues);
    }
}
