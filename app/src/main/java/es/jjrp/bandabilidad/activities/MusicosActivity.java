package es.jjrp.bandabilidad.activities;

import android.os.Bundle;
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
    PersonDbHelper pHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musicos);

        Button bNuevo = (Button) findViewById(R.id.btnNuevoMusico);
        bNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pHelper.createRow("code1", "name1");

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

    private void borrarMusico() {
        ListView musicos = (ListView) findViewById(R.id.listMusicos);
        Object sel = musicos.getSelectedItem();

        cargarMusicos();
    }

    private void cargarMusicos() {
        String[] values = buscarMusicos();

        ListView musicos = (ListView) findViewById(R.id.listMusicos);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, android.R.id.text1, values);
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
