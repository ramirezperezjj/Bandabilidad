package es.jjrp.bandabilidad.dbhelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.jjrp.bandabilidad.bean.Musico;

public class MusicoDbHelper {
    private static final String DATABASE_CREATE =
            "create table MUSICO(_id integer primary key autoincrement, "
                    + "orden integer,"
                    + "nombre text not null,"
                    + "apellidos text not null"
                    + ");";
    private static final String DATABASE_NAME = "PERSONALDB";
    private static final String DATABASE_TABLE = "MUSICO";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public MusicoDbHelper(Context ctx) {
        try {
            db = ctx.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            try {
                db.execSQL(DATABASE_CREATE);
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

    public void createRow(Integer orden, String nombre, String apellidos) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("orden", orden);
        initialValues.put("nombre", nombre);
        initialValues.put("apellidos", apellidos);
        db.insert(DATABASE_TABLE, null, initialValues);
    }

    public void createRow(String nombre, String apellidos) {
        createRow(null, nombre, apellidos);
    }

    public void deleteMusicoById(long rowId) {
        db.delete(DATABASE_TABLE, "_id=" + rowId, null);
    }

    public void deleteMusicoByOrden(int orden) {
        db.delete(DATABASE_TABLE, "orden=" + orden, null);
    }

    public List<Musico> fetchAllRows() {
        ArrayList<Musico> ret = new ArrayList<Musico>();
        try {
            Cursor c =
                    db.query(DATABASE_TABLE, new String[]{
                            "_id", "orden", "nombre", "apellidos"}, null, null, null, null, null);
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                Musico row = new Musico();
                int posCol = 0;
                row._id = c.getLong(posCol++);
                row.orden = c.getInt(posCol++);
                row.nombre = c.getString(posCol++);
                row.apellidos = c.getString(posCol++);
                ret.add(row);
                c.moveToNext();
            }
        } catch (Exception e) {
            Log.e("Exception on query", e.toString());
        }
        return ret;
    }

    public Musico fetchRow(long rowId) {
        Musico row = new Musico();
        Cursor c =
                db.query(DATABASE_TABLE, new String[]{
                        "_id", "orden", "nombre", "apellidos"}, "_id=" + rowId, null, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            int posCol = 0;
            row._id = c.getLong(posCol++);
            row.orden = c.getInt(posCol++);
            row.nombre = c.getString(posCol++);
            row.apellidos = c.getString(posCol++);
            return row;
        } else {
            row._id = row.orden = -1;
            row.nombre = row.apellidos = null;
        }
        return row;
    }

    public void updateRow(long rowId, String nombre, String apellidos) {
        ContentValues args = new ContentValues();
        args.put("nombre", nombre);
        args.put("apellidos", apellidos);
        db.update(DATABASE_TABLE, args, "_id=" + rowId, null);
    }

    public Cursor getAllRows() {
        try {
            return db.query(DATABASE_TABLE, new String[]{
                    "_id", "nombre", "apellidos"}, null, null, null, null, null);
        } catch (SQLException e) {
            Log.e("Exception on query", e.toString());
            return null;
        }
    }


}