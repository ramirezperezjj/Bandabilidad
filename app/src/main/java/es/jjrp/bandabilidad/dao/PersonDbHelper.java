package es.jjrp.bandabilidad.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import es.jjrp.bandabilidad.bean.Musico;

public class PersonDbHelper {
    private static final String DATABASE_CREATE =
            "create table MUSICO(_id integer primary key autoincrement, "
                    + "nombre text not null,"
                    + "apellidos text not null"
                    + ");";
    private static final String DATABASE_NAME = "PERSONALDB";
    private static final String DATABASE_TABLE = "MUSICO";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public PersonDbHelper(Context ctx) {
        try {
            db = ctx.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (Exception e) {
                Log.d("BASE_DATOS", "Error creando tabla");

            }
        } catch (Exception e1) {
            Log.d("BASE_DATOS", "Error creando database");
            db = null;
        }
    }

    public void close() {
        db.close();
    }

    public void createRow(String nombre, String apellidos) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("nombre", nombre);
        initialValues.put("apellidos", apellidos);
        db.insert(DATABASE_TABLE, null, initialValues);
    }

    public void deleteRow(long rowId) {
        db.delete(DATABASE_TABLE, "_id=" + rowId, null);
    }

    public List<Musico> fetchAllRows() {
        ArrayList<Musico> ret = new ArrayList<Musico>();
        try {
            Cursor c =
                    db.query(DATABASE_TABLE, new String[]{
                            "_id", "nombre", "apellidos"}, null, null, null, null, null);
            int numRows = c.getCount();
            c.moveToFirst();
            for (int i = 0; i < numRows; ++i) {
                Musico row = new Musico();
                row._id = c.getLong(0);
                row.nombre = c.getString(1);
                row.apellidos = c.getString(2);
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
                        "_id", "nombre", "apellidos"}, "_id=" + rowId, null, null, null, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            row._id = c.getLong(0);
            row.nombre = c.getString(1);
            row.apellidos = c.getString(2);
            return row;
        } else {
            row._id = -1;
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