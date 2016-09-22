package es.jjrp.bandabilidad.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import es.jjrp.bandabilidad.R;
import es.jjrp.bandabilidad.bean.Actuacion;
import es.jjrp.bandabilidad.dbhelpers.ActuacionDbHelper;

public class NuevaActuacionActivity extends AppCompatActivity {

    ActuacionDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_actuacion);
        dbHelper = new ActuacionDbHelper(this);

        final Spinner sp = (Spinner) findViewById(R.id.spNuevaActuacion);
        sp.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Actuacion.TIPO.stringValues()));

        final TextView tv = (TextView) findViewById(R.id.tvNuevaActuacionNombre);

        Button b = (Button) findViewById(R.id.btnNuevaActuacionGuardar);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                try {
                    dbHelper.createActuacion(Actuacion.TIPO.getTipo(sp.getSelectedItemPosition()), tv.getText().toString(), new Date(), 0, "fijo", 0);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(NuevaActuacionActivity.this, "Error guardando: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
