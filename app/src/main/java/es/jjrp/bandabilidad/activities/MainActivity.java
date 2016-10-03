package es.jjrp.bandabilidad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import es.jjrp.bandabilidad.R;
import es.jjrp.bandabilidad.dbhelpers.BaseDbHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //para forzar la inicialización de la base de datos
        BaseDbHelper dbHelper = new BaseDbHelper(this);
        Button b = (Button) findViewById(R.id.btnMainMusicos);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MusicosActivity.class);
                startActivity(intent);
            }
        });
        b = (Button) findViewById(R.id.btnMainActuaciones);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ActuacionesActivity.class);
                startActivity(intent);
            }
        });
        b = (Button) findViewById(R.id.btnMainFaltas);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), FaltasActivity.class);
                startActivity(intent);
            }
        });
    }


}
