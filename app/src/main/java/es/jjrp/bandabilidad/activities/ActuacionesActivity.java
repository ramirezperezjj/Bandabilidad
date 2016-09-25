package es.jjrp.bandabilidad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import es.jjrp.bandabilidad.R;
import es.jjrp.bandabilidad.adapters.ActuacionesArrayAdapter;
import es.jjrp.bandabilidad.bean.Actuacion;
import es.jjrp.bandabilidad.dbhelpers.ActuacionDbHelper;

public class ActuacionesActivity extends AppCompatActivity {

    private static final int NUEVO_ACTUACION_REQUEST = 4;
    ActuacionDbHelper dbActuaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actuaciones);
        dbActuaciones = new ActuacionDbHelper(this);

        Button b = (Button) findViewById(R.id.btnNuevoActuacion);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getBaseContext(), DetalleActuacionActivity.class);
                startActivityForResult(intent, NUEVO_ACTUACION_REQUEST);
            }
        });

        cargarActuaciones();
    }

    private void cargarActuaciones() {
        List<Actuacion> values = dbActuaciones.obtenerTodasActuaciones();

        ListView musicos = (ListView) findViewById(R.id.listaActuaciones);
        ActuacionesArrayAdapter adapter = new ActuacionesArrayAdapter(this, values);
        musicos.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case NUEVO_ACTUACION_REQUEST:
                cargarActuaciones();
                break;
            default:
                cargarActuaciones();
                break;
        }

    }
}
