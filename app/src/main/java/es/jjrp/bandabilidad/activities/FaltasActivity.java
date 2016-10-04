package es.jjrp.bandabilidad.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import es.jjrp.bandabilidad.R;
import es.jjrp.bandabilidad.adapters.FaltasRecyclerAdapter;
import es.jjrp.bandabilidad.bean.Actuacion;
import es.jjrp.bandabilidad.bean.Falta;
import es.jjrp.bandabilidad.bean.Musico;
import es.jjrp.bandabilidad.dbhelpers.ActuacionDbHelper;
import es.jjrp.bandabilidad.dbhelpers.FaltaDbHelper;
import es.jjrp.bandabilidad.dbhelpers.MusicoDbHelper;

public class FaltasActivity extends AppCompatActivity {
    FaltaDbHelper faltaDbHelper;
    MusicoDbHelper musicoDbHelper;
    ActuacionDbHelper actuacionDbHelper;
    CheckBox cbTarde;
    Spinner spActuacion;
    Spinner spMusico;
    Button btnAnotar;
    RecyclerView rvListaFaltas;

    List<Musico> listaMusicos;
    List<Actuacion> listaActuaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faltas);
        faltaDbHelper = new FaltaDbHelper(this);
        musicoDbHelper = new MusicoDbHelper(this);
        actuacionDbHelper = new ActuacionDbHelper(this);

        cbTarde = (CheckBox) findViewById(R.id.cbTarde);
        spActuacion = (Spinner) findViewById(R.id.spActuacion);
        spMusico = (Spinner) findViewById(R.id.spMusico);
        btnAnotar = (Button) findViewById(R.id.btnAnotarFalta);
        rvListaFaltas = (RecyclerView) findViewById(R.id.rvListaFaltas);
        rvListaFaltas.setHasFixedSize(true);
        rvListaFaltas.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        spActuacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                rellenarListaFaltas();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cargarDatos();
        rellenarSpinners();
        rellenarListaFaltas();
        btnAnotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarFalta();
            }
        });

    }

    private void rellenarListaFaltas() {

        List<Falta> faltas = faltaDbHelper.obtenerFaltasPorActuacion(listaActuaciones.get(spActuacion.getSelectedItemPosition())._id);
        FaltasRecyclerAdapter adaptador = new FaltasRecyclerAdapter(faltas, this);

        rvListaFaltas.setAdapter(adaptador);

    }

    private void guardarFalta() {
        int selectedPosActuacion = spActuacion.getSelectedItemPosition();
        int selectedPosMusico = spMusico.getSelectedItemPosition();
        if (selectedPosActuacion < 0 || selectedPosMusico < 0) {
            Toast.makeText(this, "Debe seleccionar un músico y una actuación", Toast.LENGTH_SHORT).show();
        } else {
            long idMusico = listaMusicos.get(selectedPosMusico)._id;
            long idActuacion = listaActuaciones.get(selectedPosActuacion)._id;
            if (!faltaDbHelper.tieneFalta(idMusico, idActuacion)) {
                faltaDbHelper.createFalta(idMusico, idActuacion, cbTarde.isChecked());
                Toast.makeText(this, "Anotada falta", Toast.LENGTH_SHORT).show();
                rellenarListaFaltas();
            } else {
                Toast.makeText(this, "Ya tiene falta en esta actuación", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void cargarDatos() {
        listaMusicos = musicoDbHelper.fetchAllRows();
        listaActuaciones = actuacionDbHelper.obtenerTodasActuaciones();
    }

    private void rellenarSpinners() {
        String[] arrayMusicos = getStringsDeMusicos();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayMusicos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMusico.setAdapter(dataAdapter);

        String[] arrayActuaciones = getStringsDeActuaciones();
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayActuaciones);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spActuacion.setAdapter(dataAdapter);
    }

    private String[] getStringsDeMusicos() {
        if (listaMusicos == null) return new String[0];
        String[] res = new String[listaMusicos.size()];
        int i = 0;
        for (Musico m : listaMusicos) {
            res[i++] = m.toString();
        }
        return res;
    }

    private String[] getStringsDeActuaciones() {
        if (listaActuaciones == null) return new String[0];
        String[] res = new String[listaActuaciones.size()];
        int i = 0;
        for (Actuacion a : listaActuaciones) {
            res[i++] = a.toString();
        }
        return res;
    }
}
