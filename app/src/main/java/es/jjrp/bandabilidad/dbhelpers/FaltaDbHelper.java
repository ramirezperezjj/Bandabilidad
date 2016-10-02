package es.jjrp.bandabilidad.dbhelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Juanjo on 02/10/2016.
 */

public class FaltaDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_TABLE_FALTA = "FALTA";
    private static final String DATABASE_CREATE_FALTA =
            "create table IF NOT EXISTS FALTA(_id integer primary key autoincrement, "
                    + "musico_id integer not null,"
                    + "actuacion_id integer not null, "
                    + "tarde integer not null " +
                    ");";

    public FaltaDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE_FALTA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS FALTA");

        //Se crea la nueva versión de la tabla
        db.execSQL(DATABASE_CREATE_FALTA);
    }

    public void createActuacion(int idMusico, int idActuacion, boolean tarde) {

        ContentValues initialValues = new ContentValues();

        initialValues.put("musico_id", idMusico);
        initialValues.put("actuacion_id", idActuacion);
        initialValues.put("tarde", tarde ? 1 : 0);

        getWritableDatabase().insert(DATABASE_CREATE_FALTA, null, initialValues);
    }
}
