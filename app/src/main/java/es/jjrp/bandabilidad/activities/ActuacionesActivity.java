package es.jjrp.bandabilidad.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Date;

import es.jjrp.bandabilidad.R;
import es.jjrp.bandabilidad.bean.Actuacion;
import es.jjrp.bandabilidad.dbhelpers.ActuacionDbHelper;

public class ActuacionesActivity extends AppCompatActivity {

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

                dbActuaciones.createActuacion(Actuacion.TIPO.CONCIERTO, "concierto de verano arriba", new Date(), 2, "Rute", 0);

                String escribir = dbActuaciones.obtenerTodasActuaciones().get(0)._id + dbActuaciones.obtenerTodasActuaciones().get(0).nombre;
                Toast.makeText(getApplicationContext(), escribir, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
