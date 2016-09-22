package es.jjrp.bandabilidad.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import es.jjrp.bandabilidad.R;
import es.jjrp.bandabilidad.bean.Actuacion;

public class DetalleActuacionActivity extends AppCompatActivity {
    Actuacion actuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_actuacion);
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        sp.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Actuacion.TIPO.stringValues()));


        actuacion = (Actuacion) getIntent().getSerializableExtra("actuacion");


        TextView tv = (TextView) findViewById(R.id.tvDetalleActuacion);
        tv.setText(actuacion.nombre);
        tv = (TextView) findViewById(R.id.tvDetalleCiudad);
        tv.setText(actuacion.ciudad);
        sp.setSelection(actuacion.tipo.ordinal());
    }
}
