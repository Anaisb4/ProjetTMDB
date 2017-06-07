package com.example.anais.projettmdb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static HashMap<Integer, String> listGenre = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Spinner nbr = (Spinner) findViewById(R.id.nbr);
        final EditText edit = (EditText) findViewById(R.id.nomFilm);
        final Context context = this;


        List<Integer> nbrElem = new ArrayList<>();
        nbrElem.add(5);
        nbrElem.add(10);
        nbrElem.add(15);
        nbrElem.add(20);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, nbrElem);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nbr.setAdapter(adapter);
        nbr.setSelection(0);

        Button bouton = (Button) findViewById(R.id.search);
        bouton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText nomFilm = (EditText) findViewById(R.id.nomFilm);


                if(!edit.getText().toString().equals(""))
                {
                    RequestQueue queue = Volley.newRequestQueue(context);
                    String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=77d0dc6aad700683ac51c5e76d4fc356&language=fr-FR";

                    JsonObjectRequest jsObject = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {

                                Intent versRecherche = new Intent(MainActivity.this, ListeActivity.class);

                                versRecherche.putExtra("nomFilm", nomFilm.getText().toString());
                                versRecherche.putExtra("nbElem", nbr.getSelectedItem().toString());

                                JSONArray jsonArray = response.getJSONArray("genres");

                                for (int i=0; i< jsonArray.length(); i++) {

                                    JSONObject jsonGenre = jsonArray.getJSONObject(i);

                                    int id = Integer.parseInt(jsonGenre.getString("id"));
                                    String genre = jsonGenre.getString("name");

                                    listGenre.put(id, genre);
                                }

                                startActivity(versRecherche);
                            } catch (JSONException e) {
                                Log.v("ERREUR", e.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.v("ERREUR", error.toString());
                        }
                    });

                    queue.add(jsObject);















                }

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText("");
            }
        });
    }
}
