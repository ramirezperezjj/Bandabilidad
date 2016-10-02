package es.jjrp.bandabilidad.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import es.jjrp.bandabilidad.R;
import es.jjrp.bandabilidad.adapters.MusicoArrayAdapter;
import es.jjrp.bandabilidad.bean.Musico;
import es.jjrp.bandabilidad.dbhelpers.MusicoDbHelper;
import es.jjrp.bandabilidad.utils.Constantes;

public class MusicosActivity extends AppCompatActivity {
    private static final int NUEVO_MUSICO_REQUEST = 1;
    MusicoDbHelper pHelper;
    FloatingActionButton bNuevo;
    ListView musicos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicos);
        bNuevo = (FloatingActionButton) findViewById(R.id.fbtnNuevo);
        musicos = (ListView) findViewById(R.id.listMusicos);

        bNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DetalleMusicoActivity.class);
                startActivityForResult(intent, NUEVO_MUSICO_REQUEST);
            }
        });


        pHelper = new MusicoDbHelper(this, Constantes.DATABASE_NAME, null, Constantes.DATABASE_VERSION);

        cargarMusicos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case NUEVO_MUSICO_REQUEST:
                cargarMusicos();
                break;
            case MusicoArrayAdapter.MOSTRAR_DETALLE_MUSICO:
                cargarMusicos();
                break;
            default:
                break;
        }

    }

    private void cargarMusicos() {
        List<Musico> values = buscarMusicos();

        MusicoArrayAdapter adapter = new MusicoArrayAdapter(this, values);
        musicos.setAdapter(adapter);
    }

    private List<Musico> buscarMusicos() {
        List<Musico> musicos = pHelper.fetchAllRows();
        return musicos;
    }

}
