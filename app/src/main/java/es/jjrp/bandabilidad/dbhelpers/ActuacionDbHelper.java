package es.jjrp.bandabilidad.dbhelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.jjrp.bandabilidad.bean.Actuacion;
import es.jjrp.bandabilidad.utils.Constantes;

/**
 * Created by Juanjo on 03/09/2016.
 */
public class ActuacionDbHelper {
    private static final String DATABASE_CREATE_ACTUACION =
            "create table ACTUACION(_id integer primary key autoincrement, "
                    + "tipo integer not null,"
                    + "fecha datetime,"
                    + "horas integer,"
                    + "nombre text not null,"
                    + "ciudad text,"
                    + "precio real"
                    + ");";
    private static final String DATABASE_CREATE_TIPO_ACTUACION =
            "create table TIPO_ACTUACION(_id integer primary key, "
                    + "desc text not null"
                    + ");";
    private static final String DATABASE_NAME = "PERSONALDB";
    private static final String DATABASE_TABLE_ACTUACION = "ACTUACION";
    private static final String DATABASE_TABLE_TIPO_ACTUACION = "TIPO_ACTUACION";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public ActuacionDbHelper(Context ctx) {
        try {
            db = ctx.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            try {
                db.execSQL(DATABASE_CREATE_ACTUACION);
                db.execSQL(DATABASE_CREATE_TIPO_ACTUACION);
                //Si llega aquí, ha creado la tabla, y estará vacía, la rellenamos
                //con datos fijos
                createTipoActuacion(0, "Procesión");
                createTipoActuacion(1, "Pasacalles");
                createTipoActuacion(2, "Concierto");
                createTipoActuacion(3, "Romería");


            } catch (Exception e) {
                Log.d("BASE_DATOS", "No se crea la tabla porque: " + e.getMessage());
            }
        } catch (Exception e1) {
            Log.d("BASE_DATOS", "Error creando database");
            db = null;
        }
    }

    public void close() {
        db.close();
    }

    public void createTipoActuacion(int id, String desc) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("_id", id);
        initialValues.put("desc", desc);
        db.insert(DATABASE_TABLE_TIPO_ACTUACION, null, initialValues);
    }

    public void createActuacion(Actuacion.TIPO tipo, String nombre, Date fecha, int horas, String ciudad, double precio) {

        ContentValues initialValues = new ContentValues();

        initialValues.put("tipo", tipo.ordinal());
        initialValues.put("nombre", nombre);
        initialValues.put("fecha", Constantes.SDF.format(fecha));
        initialValues.put("horas", horas);
        initialValues.put("ciudad", ciudad);
        initialValues.put("precio", precio);
        db.insert(DATABASE_TABLE_ACTUACION, null, initialValues);
    }

    public List<Actuacion> obtenerTodasActuaciones() {
        ArrayList<Actuacion> ret = new ArrayList<>();
        try {
            Cursor c =
                    db.query(DATABASE_TABLE_ACTUACION, new String[]{
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
}
