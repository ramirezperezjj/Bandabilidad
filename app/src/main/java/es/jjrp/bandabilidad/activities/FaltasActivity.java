package es.jjrp.bandabilidad.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import es.jjrp.bandabilidad.R;
import es.jjrp.bandabilidad.dbhelpers.FaltaDbHelper;
import es.jjrp.bandabilidad.utils.Constantes;

public class FaltasActivity extends AppCompatActivity {
    FaltaDbHelper faltaDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faltas);
        faltaDbHelper = new FaltaDbHelper(this, Constantes.DATABASE_NAME, null, Constantes.DATABASE_VERSION);


    }
}
