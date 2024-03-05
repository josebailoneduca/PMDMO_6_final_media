package com.imagenprogramada.mediaplayer;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.imagenprogramada.mediaplayer.databinding.ItemListaBinding;


import java.util.List;

/**
 * Adaptador para la lista de bicicletas
 *
 * @author Jose Javier Bailon Ortiz
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Medios.Medio> mValues;
    /**
     * Referencia al fragmento para tener acceso a la funcion de mandar email
     */
    FirstFragment fragment;

    /**
     * Configuracion inicial del adaptador
     * @param items Lista de elementos
     * @param fragment Referencia al fragmento en el que esta la funcion de email
     */
    public MyItemRecyclerViewAdapter(List<Medios.Medio> items, FirstFragment fragment) {
        mValues = items;
        this.fragment=fragment;
    }


    /**
     * Creacion de vistas
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ItemListaBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    /**
     * Rellenar una vista con los datos que tocan
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mImagen.setImageBitmap(holder.mItem.getImagen());
        holder.mNombre.setText(holder.mItem.getNombre());
        holder.mDescripcion.setText(holder.mItem.getDescripcion());
        switch (holder.mItem.getTipo()){
            case Tipos.audio:
                holder.mTipo.setImageResource(R.drawable.audio);
                break;
            case  Tipos.video:
                holder.mTipo.setImageResource(R.drawable.video);
                break;
            case  Tipos.streaming:
                holder.mTipo.setImageResource(R.drawable.streaming);
        }
        holder.mBtnPlay.setOnClickListener(v -> fragment.play(holder.mItem) );
    }



    @Override
    public int getItemCount() {
        return mValues.size();
    }

    /**
     * Clase de vistas de items
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public Medios.Medio mItem;
        public final TextView mNombre;
        public final TextView mDescripcion;
        public final ImageView mTipo;
        public final ImageView mImagen;
        public final ImageButton mBtnPlay;


        public ViewHolder(ItemListaBinding binding) {
            super(binding.getRoot());
            mNombre = binding.txtTitulo;
            mDescripcion= binding.txtDescripcion;
            mTipo = binding.icoTipo;
            mBtnPlay = binding.btnPlay;
            mImagen = binding.imagen;
        }
    }
}