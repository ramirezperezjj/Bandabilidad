package es.jjrp.bandabilidad.dbhelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.jjrp.bandabilidad.bean.Actuacion;
import es.jjrp.bandabilidad.utils.Constantes;

/**
 * Created by Juanjo on 03/09/2016.
 */
public class ActuacionDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_CREATE_ACTUACION =
            "create table IF NOT EXISTS ACTUACION(_id integer primary key autoincrement, "
                    + "tipo integer not null,"
                    + "fecha datetime,"
                    + "horas integer,"
                    + "nombre text not null,"
                    + "ciudad text,"
                    + "precio real"
                    + ");";

    private static final String DATABASE_TABLE_ACTUACION = "ACTUACION";


    public ActuacionDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


//    public ActuacionDbHelper(Context ctx) {
//        try {
//            db = ctx.openOrCreateDatabase(Constantes.DATABASE_NAME, Context.MODE_PRIVATE, null);
//            try {
//
//
//            } catch (Exception e) {
//                Log.d("BASE_DATOS", "No se crea la tabla porque: " + e.getMessage());
//            }
//        } catch (Exception e1) {
//            Log.d("BASE_DATOS", "Error creando database");
//            db = null;
//        }
//    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_ACTUACION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS ACTUACION");

        //Se crea la nueva versión de la tabla
        db.execSQL(DATABASE_CREATE_ACTUACION);
    }

    public void createActuacion(Actuacion.TIPO tipo, String nombre, Date fecha, int horas, String ciudad, double precio) {

        ContentValues initialValues = new ContentValues();

        initialValues.put("tipo", tipo.ordinal());
        initialValues.put("nombre", nombre);
        initialValues.put("fecha", Constantes.SDF.format(fecha));
        initialValues.put("horas", horas);
        initialValues.put("ciudad", ciudad);
        initialValues.put("precio", precio);
        getWritableDatabase().insert(DATABASE_TABLE_ACTUACION, null, initialValues);
    }

    public List<Actuacion> obtenerTodasActuaciones() {
        ArrayList<Actuacion> ret = new ArrayList<>();
        try {
            Cursor c =
                    getReadableDatabase().query(DATABASE_TABLE_ACTUACION, new String[]{
                            "_id", "tipo", "fecha", "horas", "nombre", "ciudad", "precio"}, null, null, null, null, null);
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                Actuacion row = new Actuacion();
                int posCol = 0;
                row._id = c.getLong(posCol++);
                row.tipo = Actuacion.TIPO.getTipo(c.getInt(posCol++));
                row.fecha = Constantes.SDF.parse(c.getString(posCol++));
                row.horas = c.getInt(posCol++);
                row.nombre = c.getString(posCol++);
                row.ciudad = c.getString(posCol++);
                row.precio = c.getDouble(posCol++);
                ret.add(row);
                c.moveToNext();
            }
        } catch (Exception e) {
            Log.e("Exception on query", e.toString());
        }
        return ret;
    }

    public void updateActuacion(Actuacion actuacion) {
        ContentValues args = new ContentValues();
        args.put("ciudad", actuacion.ciudad);
        args.put("horas", actuacion.horas);
        args.put("nombre", actuacion.nombre);
        args.put("precio", actuacion.precio);
        args.put("fecha", Constantes.SDF.format(actuacion.fecha));
        args.put("tipo", actuacion.tipo.ordinal());

        getWritableDatabase().update(DATABASE_TABLE_ACTUACION, args, "_id=" + actuacion._id, null);
    }

    public void deleteAcutacionById(long rowId) {
        getWritableDatabase().delete(DATABASE_TABLE_ACTUACION, "_id=" + rowId, null);
    }
}
