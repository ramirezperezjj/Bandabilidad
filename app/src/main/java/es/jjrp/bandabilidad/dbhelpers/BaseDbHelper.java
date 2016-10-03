package es.jjrp.bandabilidad.dbhelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import es.jjrp.bandabilidad.utils.Constantes;

/**
 * Created by Juanjo on 03/10/2016.
 */

public class BaseDbHelper extends SQLiteOpenHelper {

    protected static final String DATABASE_TABLE_MUSICO = "MUSICO";
    protected static final String DATABASE_TABLE_FALTA = "FALTA";
    protected static final String DATABASE_TABLE_ACTUACION = "ACTUACION";
    protected static final String DATABASE_CREATE_ACTUACION =
            "create table IF NOT EXISTS ACTUACION(_id integer primary key autoincrement, "
                    + "tipo integer not null,"
                    + "fecha datetime,"
                    + "horas integer,"
                    + "nombre text not null,"
                    + "ciudad text,"
                    + "precio real"
                    + ");";
    private static final String DATABASE_CREATE_MUSICO =
            "create table IF NOT EXISTS MUSICO(_id integer primary key autoincrement, "
                    + "orden integer,"
                    + "nombre text not null,"
                    + "apellidos text not null"
                    + ");";
    private static final String DATABASE_CREATE_FALTA =
            "create table IF NOT EXISTS FALTA(_id integer primary key autoincrement, "
                    + "musico_id integer not null,"
                    + "actuacion_id integer not null, "
                    + "tarde integer not null " +
                    ");";


    public BaseDbHelper(Context context) {
        super(context, Constantes.DATABASE_NAME, null, Constantes.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_MUSICO);
        db.execSQL(DATABASE_CREATE_FALTA);
        db.execSQL(DATABASE_CREATE_ACTUACION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {

    }
}
