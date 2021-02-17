package com.example.tp04listview;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.tp04listview.MainActivity;
import com.example.tp04listview.PeliculaExtensa;
import com.example.tp04listview.R;
import com.example.tp04listview.infoPeliSeleccion;

import java.util.ArrayList;
import java.util.List;

public class PeliculasListAdapter extends ArrayAdapter<PeliculaExtensa> {
    ArrayList<PeliculaExtensa> personasList;
    private int resourceLayout;
    Context context;

    public PeliculasListAdapter(Context context, int resource, ArrayList<PeliculaExtensa> datosArray) {
        super(context, resource, datosArray);      /* Llamada al constructor de la clase Base (Super) */

        this.context        = context;
        this.resourceLayout = resource;
        this.personasList   = datosArray;
    }

    @SuppressLint("WrongViewCast")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PeliculaExtensa     dato;
        TextView    tvYear ;
        TextView    tvTitle;
        ImageView   ivPoster;

        View layoutInterno = convertView;

        if (layoutInterno == null) {
            LayoutInflater vi;
            vi              = LayoutInflater.from(this.context);
            layoutInterno   = vi.inflate(resourceLayout, null);
        }

        dato = getItem(position);

        if (dato != null) {

            // Obtengo la Rerferencia
            ivPoster = (ImageView) layoutInterno.findViewById(R.id.ivPoster);
            tvTitle  = (TextView) layoutInterno.findViewById(R.id.tvTitle);
            tvYear      = (TextView) layoutInterno.findViewById(R.id.tvYear);

            // Seteo las propiedades
            tvTitle.setText(dato.get_Title());
            tvYear.setText(dato.get_Year());
            mostrarFoto(dato.get_Poster(), ivPoster);
        }

        return layoutInterno;
    }
    private void mostrarFoto(String posterPelicula, ImageView ivPoster){
        Glide.with(this.context)
                .load(posterPelicula)
                .apply(new RequestOptions()
                        .centerCrop()
                        .dontAnimate()
                        .dontTransform().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .thumbnail(.5f)
                .into(ivPoster);
    }
}