package com.example.tp04listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvDatosBusq;
    Button btnBuscar;
    EditText edPalabraBuscar;
    TextView tvError;
    ArrayList<PeliculaExtensa> ArrayRespuesta;
    String Palabrabuscar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ObtenerReferencias();
        SetearListeners();
    }

    private void CargarDatos() {

        PeliculasListAdapter adapter;


            //adapter         = new ArrayAdapter<Persona>(this, android.R.layout.simple_list_item_1, datosArrayList);
        adapter = new PeliculasListAdapter(this, R.layout.item_persona, ArrayRespuesta);
        lvDatosBusq.setAdapter(adapter);
    }

    private void SetearListeners() {
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Palabrabuscar = edPalabraBuscar.getText().toString();
                if(Palabrabuscar.length() != 0) {
                    MainActivity.TraerResultadoBusqueda Resultado =new MainActivity.TraerResultadoBusqueda();
                    Resultado.execute();
                }
                else {
                    tvError.setText("Ingrese una palabra de busqueda");
                }
            }
        });
        lvDatosBusq.setOnItemClickListener(lvDatosBusq_Click);
    }


    private void ObtenerReferencias() {
        lvDatosBusq = findViewById(R.id.lvDatosBusqueda);
        btnBuscar = findViewById(R.id.btnBuscar);
        edPalabraBuscar = findViewById(R.id.edPalabraBusca);
        tvError = findViewById(R.id.tvError);
    }
    AdapterView.OnItemClickListener lvDatosBusq_Click = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            PeliculaExtensa pelicula = (PeliculaExtensa) lvDatosBusq.getItemAtPosition(position);
            String peliID = pelicula.get_imdbID();
            Manejo.idModi = peliID;
            Intent intent = new Intent(getApplicationContext(), infoPeliSeleccion.class);
            startActivity(intent);

        }
    };
    private class TraerResultadoBusqueda extends AsyncTask<Void,Void,String> {
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
                strAPIUrl = new URL("https://omdbapi.com/?apikey=2432d6a9&s=" + Palabrabuscar );
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
        }

        private void PasarJSON(String resultado) {
            JsonObject JSONrespuesta, JSONCategoria;
            JsonArray categoriasJSONArr;
            ArrayRespuesta = new ArrayList<>();

            JSONrespuesta = JsonParser.parseString(resultado).getAsJsonObject();

            categoriasJSONArr = JSONrespuesta.getAsJsonArray("Search");

            for(int i =0; i < categoriasJSONArr.size(); i++){
                JSONCategoria = categoriasJSONArr.get(i).getAsJsonObject();
                String Title  = JSONCategoria.get("Title").getAsString();
                String Year = JSONCategoria.get("Year").getAsString();
                String imdbID = JSONCategoria.get("imdbID").getAsString();
                String Poster = JSONCategoria.get("Poster").getAsString();
                PeliculaExtensa rbObjeto = new PeliculaExtensa(Title, Year, imdbID, Poster);
                ArrayRespuesta.add(rbObjeto);
            }
            CargarDatos();
        }
    }
}