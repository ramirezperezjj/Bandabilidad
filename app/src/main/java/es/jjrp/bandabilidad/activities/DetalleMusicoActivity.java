package es.jjrp.bandabilidad.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        TextView tv = (TextView) findViewById(R.id.tvDetalleNombre);
        tv.setText(musico.nombre);
        tv = (TextView) findViewById(R.id.tvDetalleApellidos);
        tv.setText(musico.apellidos);

        Button b = (Button) findViewById(R.id.btnDetalleBorrar);
        b.setOnClickListener(new View.Onc);
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
