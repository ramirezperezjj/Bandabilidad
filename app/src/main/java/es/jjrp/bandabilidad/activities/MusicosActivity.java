package es.jjrp.bandabilidad.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import es.jjrp.bandabilidad.R;
import es.jjrp.bandabilidad.bean.Musico;
import es.jjrp.bandabilidad.dao.PersonDbHelper;

public class MusicosActivity extends AppCompatActivity {
    private static final int NUEVO_MUSICO_REQUEST = 1;
    PersonDbHelper pHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicos);

        Button bNuevo = (Button) findViewById(R.id.btnNuevoMusico);
        bNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NuevoMusicoActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
                startActivityForResult(intent, NUEVO_MUSICO_REQUEST);

                cargarMusicos();
            }
        });

        Button bBorrar = (Button) findViewById(R.id.btnBorrarMusico);
        bBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarMusico();
            }
        });

        pHelper = new PersonDbHelper(this);

        cargarMusicos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case NUEVO_MUSICO_REQUEST:
                cargarMusicos();
                break;
            default:
                break;
        }

    }

    private void borrarMusico() {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.dialog_borrar_message)
                .setTitle(R.string.dialog_borrar_title);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                ListView musicos = (ListView) findViewById(R.id.listMusicos);
                if (musicos.getCheckedItemPosition() >= 0) {
                    String sel = musicos.getItemAtPosition(musicos.getCheckedItemPosition()).toString();
                    String musicoId = sel.split("_")[0];
                    pHelper.deleteRow(Long.parseLong(musicoId));
                    cargarMusicos();
                }
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

    private void cargarMusicos() {
        String[] values = buscarMusicos();

        ListView musicos = (ListView) findViewById(R.id.listMusicos);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, android.R.id.text1, values);
        musicos.setAdapter(adapter);
    }

    private String[] buscarMusicos() {

        List<Musico> musicos = pHelper.fetchAllRows();
        String[] m = new String[musicos.size()];
        for (int i = 0; i < musicos.size(); i++) {
            m[i] = musicos.get(i).toString();
        }
        return m;

    }

}
