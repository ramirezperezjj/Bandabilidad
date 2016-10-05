package es.jjrp.bandabilidad.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import es.jjrp.bandabilidad.R;
import es.jjrp.bandabilidad.activities.FaltasActivity;
import es.jjrp.bandabilidad.bean.Falta;
import es.jjrp.bandabilidad.bean.Musico;
import es.jjrp.bandabilidad.dbhelpers.FaltaDbHelper;
import es.jjrp.bandabilidad.dbhelpers.MusicoDbHelper;

/**
 * Created by Juanjo on 04/10/2016.
 */

public class FaltasRecyclerAdapter extends RecyclerView.Adapter<FaltasRecyclerAdapter.FaltasViewHolder> {

    MusicoDbHelper mDbHelper;
    FaltaDbHelper fDbHelper;
    private List<Falta> datos;

    //...

    public FaltasRecyclerAdapter(List<Falta> datos, Context context) {
        this.datos = datos;
        mDbHelper = new MusicoDbHelper(context);
        fDbHelper = new FaltaDbHelper(context);
    }

    @Override
    public FaltasViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.lista_musicos_layout, viewGroup, false);

        FaltasViewHolder tvh = new FaltasViewHolder(itemView);
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                manageOnLongClick(view);
                return false;
            }
        });
        return tvh;
    }

    private void manageOnLongClick(View view) {
        final RecyclerView rv = (RecyclerView) view.getParent();
        ((FaltasActivity) rv.getContext()).openContextMenu(view);
        final int pos = rv.getChildAdapterPosition(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage(R.string.dialog_borrar_message)
                .setTitle(R.string.dialog_borrar_title);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                fDbHelper.deleteFaltaById(datos.get(pos)._id);
                datos.remove(pos);
                rv.getAdapter().notifyDataSetChanged();

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();


    }

    @Override
    public void onBindViewHolder(FaltasViewHolder viewHolder, int pos) {
        Falta item = datos.get(pos);

        viewHolder.bindFalta(item, pos);
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
        private TextView tvOrden;

        public FaltasViewHolder(View itemView) {
            super(itemView);
            musicoHelper = new MusicoDbHelper(itemView.getContext());
            tvNombre = (TextView) itemView.findViewById(R.id.tvNombre);
            tvApellidos = (TextView) itemView.findViewById(R.id.tvApellidos);
            tvOrden = (TextView) itemView.findViewById(R.id.tvOrden);
        }

        public void bindFalta(Falta f, int pos) {
            Musico m = musicoHelper.fetchRow(f.musicoId);
            tvNombre.setText(m.nombre);
            tvApellidos.setText(m.apellidos + (f.tarde ? ". Tarde" : ""));
            tvOrden.setText("" + (pos + 1));
        }
    }
}
