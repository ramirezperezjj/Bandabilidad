package es.jjrp.bandabilidad.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import es.jjrp.bandabilidad.R;
import es.jjrp.bandabilidad.dbhelpers.MusicoDbHelper;

public class NuevoMusicoActivity extends AppCompatActivity {
    MusicoDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_musico);
        db = new MusicoDbHelper(this);
        Button b = (Button) findViewById(R.id.btNuevoGuardar);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tNombre = (TextView) findViewById(R.id.textNuevoNombre);
                TextView tApellido = (TextView) findViewById(R.id.textNuevoApellido);
//                TextView tOrden = (TextView) findViewById(R.id.textNuevoNumero);
                if (tNombre.getText() == null || tNombre.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getBaseContext(), "Falta el nombre", Toast.LENGTH_SHORT).show();
                } else if (tApellido.getText() == null || tApellido.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getBaseContext(), "Falta el apellido", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        db.createRow(tNombre.getText().toString(), tApellido.getText().toString());
//                    finishActivity(Activity.RESULT_OK);
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), "Error creando músico: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}
