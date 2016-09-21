package es.jjrp.bandabilidad.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import es.jjrp.bandabilidad.R;
import es.jjrp.bandabilidad.activities.DetalleMusicoActivity;
import es.jjrp.bandabilidad.bean.Musico;

/**
 * Adapter de músico para la lista
 * Created by Juanjo on 31/08/2016.
 */
public class MusicoArrayAdapter extends ArrayAdapter<Musico> {

    public MusicoArrayAdapter(Context context, List<Musico> musicos) {
        super(context, ListView.CHOICE_MODE_SINGLE, musicos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Musico musico = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.lista_musicos_layout, parent, false);

        }

        TextView tvOrden = (TextView) convertView.findViewById(R.id.tvOrden);
        TextView tvNombre = (TextView) convertView.findViewById(R.id.tvNombre);
        TextView tvApellidos = (TextView) convertView.findViewById(R.id.tvApellidos);

        tvOrden.setText("" + (position + 1));
        tvNombre.setText(musico.nombre);
        tvApellidos.setText(musico.apellidos);

        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DetalleMusicoActivity.class);
                intent.putExtra("musico", musico);
                getContext().startActivity(intent);
            }
        });

        return convertView;
    }

}
