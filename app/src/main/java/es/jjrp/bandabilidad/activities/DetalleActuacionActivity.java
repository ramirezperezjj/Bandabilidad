package es.jjrp.bandabilidad.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import es.jjrp.bandabilidad.R;
import es.jjrp.bandabilidad.bean.Actuacion;
import es.jjrp.bandabilidad.dbhelpers.ActuacionDbHelper;
import es.jjrp.bandabilidad.utils.Constantes;

public class DetalleActuacionActivity extends AppCompatActivity {
    Actuacion actuacion;
    ActuacionDbHelper dbHelper;
    Spinner sp;
    Button btnBorrar;
    Button btnGuardar;
    TextView tvDetalleActuacion;
    TextView tvFechaHora;
    TextView tvCiudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_actuacion);

        sp = (Spinner) findViewById(R.id.spinner);
        btnBorrar = (Button) findViewById(R.id.btnDetalleActuacionBorrar);
        btnGuardar = (Button) findViewById(R.id.btnDetalleActuacionGuardar);
        tvDetalleActuacion = (TextView) findViewById(R.id.tvDetalleActuacion);
        tvFechaHora = (TextView) findViewById(R.id.tvDetalleActuacionFechaHora);
        tvCiudad = (TextView) findViewById(R.id.tvDetalleCiudad);

        dbHelper = new ActuacionDbHelper(this, Constantes.DATABASE_NAME, null, Constantes.DATABASE_VERSION);
        actuacion = (Actuacion) getIntent().getSerializableExtra("actuacion");
        sp.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Actuacion.TIPO.stringValues()));

        if (actuacion == null) {
            //Nueva actuación
            ((ViewGroup) btnBorrar.getParent()).removeView(btnBorrar);
            tvFechaHora.setText(Constantes.SDF.format(new Date()));
            btnGuardar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    nuevaActuacion();
                }
            });


        } else {
            //Detalle para guardar la existente

            tvDetalleActuacion.setText(actuacion.nombre);
            tvCiudad.setText(actuacion.ciudad);
            sp.setSelection(actuacion.tipo.ordinal());
            tvFechaHora.setText(Constantes.SDF.format(actuacion.fecha));

            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    guardarActucion();
                }
            });
            btnBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    borrarActucion();
                    Toast.makeText(DetalleActuacionActivity.this, "Borrado", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }

    }

    private void nuevaActuacion() {
        try {
            Date fec = Constantes.SDF.parse(tvFechaHora.getText().toString());
            dbHelper.createActuacion(Actuacion.TIPO.getTipo(sp.getSelectedItemPosition()), tvDetalleActuacion.getText().toString(), fec, 0, tvCiudad.getText().toString(), 0);
            finish();
        } catch (Exception e) {
            Toast.makeText(DetalleActuacionActivity.this, "Error guardando: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void borrarActucion() {

        dbHelper.deleteAcutacionById(actuacion._id);
    }

    private void guardarActucion() {
        try {
            Actuacion guardar = new Actuacion();

            guardar.tipo = Actuacion.TIPO.getTipo(sp.getSelectedItemPosition());
            guardar._id = actuacion._id;
            guardar.fecha = Constantes.SDF.parse(tvFechaHora.getText().toString());
            guardar.nombre = tvDetalleActuacion.getText().toString();
            guardar.ciudad = tvCiudad.getText().toString();
            if (guardar.nombre == null || guardar.nombre.isEmpty() || guardar.tipo == null) {
                Toast.makeText(DetalleActuacionActivity.this, "Falta nombre o tipo", Toast.LENGTH_SHORT).show();
            } else {
                dbHelper.updateActuacion(guardar);
                Toast.makeText(DetalleActuacionActivity.this, "Acutalizado", Toast.LENGTH_SHORT).show();
                finish();

            }

        } catch (Exception e) {
            Toast.makeText(this, "Error guardando: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
