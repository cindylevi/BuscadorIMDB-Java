package com.example.tp04listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class infoPeliSeleccion extends AppCompatActivity {

    PeliculaExtensa ObjPeli;
    TextView tvTitle, tvYear, tvActors, tvRated, tvRuntime, tvPlot, tvAwards;
    ImageView ivImagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_peli_seleccion);
        ObtengoReferencias();
        infoPeliSeleccion.TraerResultadoByID ObtenerJSON = new infoPeliSeleccion.TraerResultadoByID();
        ObtenerJSON.execute();
    }

    public void ObtengoReferencias() {
        tvTitle= findViewById(R.id.tvTitle);
        tvYear= findViewById(R.id.tvAnio);
        tvActors= findViewById(R.id.tvActors);
        tvRated= findViewById(R.id.tvRated);
        tvRuntime= findViewById(R.id.tvRuntime);
        tvPlot= findViewById(R.id.tvPlot);
        tvAwards= findViewById(R.id.tvAwards);
        ivImagen = findViewById(R.id.ivPoster);
    }

    public void MuestroDatos(){
        tvTitle.setText(ObjPeli.get_Title());
        tvYear.setText(ObjPeli.get_Year());
        tvRuntime.setText(ObjPeli.get_Runtime());
        tvRated.setText(ObjPeli.get_Rated());
        tvActors.setText(ObjPeli.get_Actors());
        tvAwards.setText(ObjPeli.get_Awards());
        tvPlot.setText(ObjPeli.get_Plot());
        mostrarFoto(ObjPeli.get_Poster());
    }

    private void mostrarFoto(String posterPelicula){
        Glide.with(infoPeliSeleccion.this)
                .load(posterPelicula)
                .apply(new RequestOptions()
                        .centerCrop()
                        .dontAnimate()
                        .dontTransform().diskCacheStrategy(DiskCacheStrategy.RESOURCE))
                .thumbnail(.5f)
                .into(ivImagen);
    }

    private class TraerResultadoByID extends AsyncTask<Void,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {
            HttpURLConnection miConexion = null;
            URL strAPIUrl;
// Estoy en el Background Thread.
            BufferedReader responseReader;
            String responseLine, strResultado = "";
            StringBuilder sbResponse;
            try {
                strAPIUrl = new URL("https://omdbapi.com/?apikey=2432d6a9&i=" + Manejo.idModi);
                miConexion = (HttpURLConnection) strAPIUrl.openConnection();
                miConexion.setRequestMethod("GET");
                if (miConexion.getResponseCode() == 200) {
                    responseReader = new BufferedReader(new InputStreamReader(miConexion.getInputStream()));
                    sbResponse = new StringBuilder();
                    while ((responseLine = responseReader.readLine()) != null) {
                        sbResponse.append(responseLine);
                    }
                    responseReader.close();
                    strResultado = sbResponse.toString();
                } else {

                }
            } catch (Exception e) {
                Log.e("CINDY ", e.getMessage());
            } finally {
                if (miConexion != null){
                    miConexion.disconnect();
                }
            }
            return strResultado;
        }

        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);
            PasarJSON(resultado);
            MuestroDatos();
        }

        private void PasarJSON(String resultado) {
            JsonObject JSONrespuesta;

            JSONrespuesta = JsonParser.parseString(resultado).getAsJsonObject();
            String Title  = JSONrespuesta.get("Title").getAsString();
            String Year = JSONrespuesta.get("Year").getAsString();
            String rated = JSONrespuesta.get("Rated").getAsString();
            String runtime = JSONrespuesta.get("Runtime").getAsString();
            String actors = JSONrespuesta.get("Actors").getAsString();
            String plot = JSONrespuesta.get("Plot").getAsString();
            String awards = JSONrespuesta.get("Awards").getAsString();
            String Poster = JSONrespuesta.get("Poster").getAsString();
            ObjPeli = new PeliculaExtensa(Title, rated, runtime, Year, actors, plot, awards,Poster);
        }
    }
}