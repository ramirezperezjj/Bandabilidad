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
import es.jjrp.bandabilidad.utils.Constantes;

public class DetalleMusicoActivity extends AppCompatActivity {
    MusicoDbHelper pHelper;
    Musico musico;
    Button btnBorrar;
    Button btnGuardar;
    TextView tvNombre;
    TextView tvApellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_musico);
        pHelper = new MusicoDbHelper(this, Constantes.DATABASE_NAME, null, Constantes.DATABASE_VERSION);
        musico = (Musico) getIntent().getSerializableExtra("musico");
        btnBorrar = (Button) findViewById(R.id.btnDetalleBorrar);
        btnGuardar = (Button) findViewById(R.id.btnDetalleGuardar);
        tvNombre = (TextView) findViewById(R.id.tvDetalleNombre);
        tvApellido = (TextView) findViewById(R.id.tvDetalleApellidos);

        if (musico == null) {
//Nuevo músico
            ((ViewGroup) btnBorrar.getParent()).removeView(btnBorrar);
            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    nuevoMusico();
                }
            });

        } else {
            //Detalle, actualizar
            tvNombre.setText(musico.nombre);
            tvApellido.setText(musico.apellidos);

            btnBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    borrarMusico();
                }
            });
            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    guardarMusico();
                }
            });
        }
    }

    private void nuevoMusico() {
        String nombre = tvNombre.getText().toString();
        String apellidos = tvApellido.getText().toString();
        if (nombre == null || nombre.isEmpty() || apellidos == null || apellidos.isEmpty()) {
            Toast.makeText(getBaseContext(), "Falta el nombre o apellido", Toast.LENGTH_SHORT).show();
        } else {
            try {
                pHelper.createRow(tvNombre.getText().toString(), tvApellido.getText().toString());
                finish();
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), "Error creando músico: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void guardarMusico() {
        String nombre = tvNombre.getText().toString();
        String apellidos = tvApellido.getText().toString();
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
