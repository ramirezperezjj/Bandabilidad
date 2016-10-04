package es.jjrp.bandabilidad.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import es.jjrp.bandabilidad.R;
import es.jjrp.bandabilidad.bean.Falta;
import es.jjrp.bandabilidad.bean.Musico;
import es.jjrp.bandabilidad.dbhelpers.MusicoDbHelper;

/**
 * Created by Juanjo on 04/10/2016.
 */

public class FaltasRecyclerAdapter extends RecyclerView.Adapter<FaltasRecyclerAdapter.FaltasViewHolder> {

    MusicoDbHelper mDbHelper;
    private List<Falta> datos;

    //...

    public FaltasRecyclerAdapter(List<Falta> datos, Context context) {
        this.datos = datos;
        mDbHelper = new MusicoDbHelper(context);
    }

    @Override
    public FaltasViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lista_musicos_layout, viewGroup, false);

        FaltasViewHolder tvh = new FaltasViewHolder(itemView);

        return tvh;
    }

    @Override
    public void onBindViewHolder(FaltasViewHolder viewHolder, int pos) {
        Falta item = datos.get(pos);

        viewHolder.bindFalta(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public static class FaltasViewHolder
            extends RecyclerView.ViewHolder {

        MusicoDbHelper musicoHelper;
        private TextView tvNombre;
        private TextView tvApellidos;

        public FaltasViewHolder(View itemView) {
            super(itemView);
            musicoHelper = new MusicoDbHelper(itemView.getContext());
            tvNombre = (TextView) itemView.findViewById(R.id.tvNombre);
            tvApellidos = (TextView) itemView.findViewById(R.id.tvApellidos);
            ((ViewGroup) itemView.findViewById(R.id.tvPunto).getParent()).removeView(itemView.findViewById(R.id.tvPunto));
            ((ViewGroup) itemView.findViewById(R.id.tvOrden).getParent()).removeView(itemView.findViewById(R.id.tvOrden));
        }

        public void bindFalta(Falta f) {
            Musico m = musicoHelper.fetchRow(f.musicoId);
            tvNombre.setText(m.nombre);
            tvApellidos.setText(m.apellidos);
        }
    }
}
