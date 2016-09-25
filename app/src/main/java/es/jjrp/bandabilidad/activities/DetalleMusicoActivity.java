package es.jjrp.bandabilidad.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import es.jjrp.bandabilidad.R;
import es.jjrp.bandabilidad.bean.Musico;
import es.jjrp.bandabilidad.dbhelpers.MusicoDbHelper;

public class DetalleMusicoActivity extends AppCompatActivity {
    MusicoDbHelper pHelper;
    Musico musico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_musico);
        pHelper = new MusicoDbHelper(this);

        musico = (Musico) getIntent().getSerializableExtra("musico");
        if (musico == null) {
            Button b = (Button) findViewById(R.id.btnDetalleBorrar);
            ((ViewGroup) b.getParent()).removeView(b);

            b = (Button) findViewById(R.id.btnDetalleGuardar);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView tNombre = (TextView) findViewById(R.id.tvDetalleNombre);
                    TextView tApellido = (TextView) findViewById(R.id.tvDetalleApellidos);
//                TextView tOrden = (TextView) findViewById(R.id.textNuevoNumero);
                    if (tNombre.getText() == null || tNombre.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getBaseContext(), "Falta el nombre", Toast.LENGTH_SHORT).show();
                    } else if (tApellido.getText() == null || tApellido.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getBaseContext(), "Falta el apellido", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            pHelper.createRow(tNombre.getText().toString(), tApellido.getText().toString());
//                    finishActivity(Activity.RESULT_OK);
                            finish();
                        } catch (Exception e) {
                            Toast.makeText(getBaseContext(), "Error creando músico: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        } else {
            //Detalle, actualizar
            TextView tv = (TextView) findViewById(R.id.tvDetalleNombre);
            tv.setText(musico.nombre);
            tv = (TextView) findViewById(R.id.tvDetalleApellidos);
            tv.setText(musico.apellidos);

            Button b = (Button) findViewById(R.id.btnDetalleBorrar);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    borrarMusico();
                }
            });

            b = (Button) findViewById(R.id.btnDetalleGuardar);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    guardarMusico();
                }
            });
        }
    }

    private void guardarMusico() {
        TextView tv = (TextView) findViewById(R.id.tvDetalleNombre);
        String nombre = tv.getText().toString();
        tv = (TextView) findViewById(R.id.tvDetalleApellidos);
        String apellidos = tv.getText().toString();
        if (nombre == null || nombre.isEmpty() || apellidos == null || apellidos.isEmpty()) {
            Toast.makeText(this, "El nombre y apellidos son obligatorios", Toast.LENGTH_SHORT).show();
        } else {
            try {
                pHelper.updateRow(musico._id, nombre, apellidos);
                Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "Error guardando", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void borrarMusico() {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_borrar_message)
                .setTitle(R.string.dialog_borrar_title);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                pHelper.deleteMusicoById(musico._id);
                finish();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
// Set other dialog properties


// Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();


    }
}
