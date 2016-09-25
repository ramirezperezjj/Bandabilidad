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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_actuacion);
        final Spinner sp = (Spinner) findViewById(R.id.spinner);
        sp.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Actuacion.TIPO.stringValues()));
        dbHelper = new ActuacionDbHelper(this);

        actuacion = (Actuacion) getIntent().getSerializableExtra("actuacion");
        if (actuacion == null) {
            //Nueva actuación
            Button b = (Button) findViewById(R.id.btnDetalleActuacionBorrar);
            ((ViewGroup) b.getParent()).removeView(b);
            sp.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Actuacion.TIPO.stringValues()));

            final TextView tv = (TextView) findViewById(R.id.tvDetalleActuacion);
            final TextView fechaHora = (TextView) findViewById(R.id.tvDetalleActuacionFechaHora);
            fechaHora.setText(Constantes.SDF.format(new Date()));
            final TextView tvCiudad = (TextView) findViewById(R.id.tvDetalleCiudad);

            b = (Button) findViewById(R.id.btnDetalleActuacionGuardar);
            b.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    try {
                        Date fec = Constantes.SDF.parse(fechaHora.getText().toString());
                        dbHelper.createActuacion(Actuacion.TIPO.getTipo(sp.getSelectedItemPosition()), tv.getText().toString(), fec, 0, tvCiudad.getText().toString(), 0);
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(DetalleActuacionActivity.this, "Error guardando: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } else {
            //Detalle para guardar la existente
            TextView tv = (TextView) findViewById(R.id.tvDetalleActuacion);
            tv.setText(actuacion.nombre);
            tv = (TextView) findViewById(R.id.tvDetalleCiudad);
            tv.setText(actuacion.ciudad);
            sp.setSelection(actuacion.tipo.ordinal());
            tv = (TextView) findViewById(R.id.tvDetalleActuacionFechaHora);
            tv.setText(Constantes.SDF.format(actuacion.fecha));

            Button b = (Button) findViewById(R.id.btnDetalleActuacionGuardar);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    guardarActucion();
                }
            });
            b = (Button) findViewById(R.id.btnDetalleActuacionBorrar);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    borrarActucion();
                    Toast.makeText(DetalleActuacionActivity.this, "Borrado", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }

    }

    private void borrarActucion() {

        dbHelper.deleteAcutacionById(actuacion._id);
    }

    private void guardarActucion() {
        try {
            Actuacion guardar = new Actuacion();
            Spinner sp = (Spinner) findViewById(R.id.spinner);

            guardar.tipo = Actuacion.TIPO.getTipo(sp.getSelectedItemPosition());
            guardar._id = actuacion._id;
            String strFecha = ((TextView) findViewById(R.id.tvDetalleActuacionFechaHora)).getText().toString();
            guardar.fecha = Constantes.SDF.parse(strFecha);
            guardar.nombre = ((TextView) findViewById(R.id.tvDetalleActuacion)).getText().toString();
            guardar.ciudad = ((TextView) findViewById(R.id.tvDetalleCiudad)).getText().toString();
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
