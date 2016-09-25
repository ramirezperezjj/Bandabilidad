package es.jjrp.bandabilidad.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.jjrp.bandabilidad.R;
import es.jjrp.bandabilidad.activities.ActuacionesActivity;
import es.jjrp.bandabilidad.activities.DetalleActuacionActivity;
import es.jjrp.bandabilidad.bean.Actuacion;
import es.jjrp.bandabilidad.utils.Constantes;

/**
 * Created by Juanjo on 22/09/2016.
 */

public class ActuacionesArrayAdapter extends ArrayAdapter<Actuacion> {

    public static final int MOSTRAR_DETALLE_ACTUACION = 3;

    public ActuacionesArrayAdapter(Context context, List<Actuacion> actuaciones) {
        super(context, R.layout.lista_actuaciones_layout, actuaciones);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Actuacion actuacion = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lista_actuaciones_layout, parent, false);

        }

        TextView tvNombre = (TextView) convertView.findViewById(R.id.tvListaNombreActuacion);
        TextView tvFecha = (TextView) convertView.findViewById(R.id.tvListaFechaActuacion);
        TextView tvTipo = (TextView) convertView.findViewById(R.id.tvListaTipoActuacion);

        tvNombre.setText(actuacion.nombre);
        tvFecha.setText(Constantes.SDF.format(actuacion.fecha));
        tvTipo.setText(actuacion.tipo.name());

        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DetalleActuacionActivity.class);
                intent.putExtra("actuacion", actuacion);
                ((ActuacionesActivity) getContext()).startActivityForResult(intent, MOSTRAR_DETALLE_ACTUACION);
            }
        });

        return convertView;
    }

}
