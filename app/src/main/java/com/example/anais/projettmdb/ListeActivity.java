package com.example.anais.projettmdb;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ListeActivity extends Activity implements OnItemClickListener {
    int tailleListe = 0;
    int nbrAffiche = 0;
    ArrayList<Movie> listMovie;

    ListView listView;
    ArrayList<Movie> listMovieAffiche;
    int nbrElem;
    int nbrMoviePage;
    RequestQueue request;

    /** Appelé à la création de l'activité **/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final Button prec = (Button) findViewById(R.id.boutonPrecedent);
        final Button suiv = (Button) findViewById(R.id.boutonSuivant);

        //Récupération des éléemnts passer entre activité
        Intent intent = getIntent();
        if (intent != null) {
            String nomFilm = intent.getExtras().getString("nomFilm");
            nomFilm = nomFilm.replace(" ","+");
            nbrElem = Integer.parseInt(intent.getExtras().getString("nbElem"));
            listMovie = new ArrayList<Movie>();


            this.request = Volley.newRequestQueue(this);

            listView = (ListView) findViewById(R.id.list);
            //récupération des catégories
            //loadGenres();

            //récupération des films

            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "https://api.themoviedb.org/3/search/movie?api_key=77d0dc6aad700683ac51c5e76d4fc356&query="+nomFilm+"&page=1";

            final Context context = this;

            final JsonObjectRequest jsObject = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {

                        JSONArray jsonArray = response.getJSONArray("results");

                        for (int i=0; i< jsonArray.length(); i++) {

                            JSONObject jsonMovie = jsonArray.getJSONObject(i);
                            JSONArray genreID = jsonMovie.getJSONArray("genre_ids");

                            LinkedList<String> genreList = new LinkedList<String>();
                            genreList.addAll(getGenres(genreID));//getGenre --> return LinkedList genre

                            String title = jsonMovie.getString("title");
                            String image = jsonMovie.getString("poster_path");
                            String description = jsonMovie.getString("overview");
                            String origine = jsonMovie.getString("original_language");
                            String  date = jsonMovie.getString("release_date");
                            Double rating = Double.parseDouble(jsonMovie.getString("vote_average"));

                            Movie newMovie = new Movie(image,title, description,origine,date,genreList, rating);
                            listMovie.add(newMovie);
                        }

                        CustomListViewAdapter adapter = new CustomListViewAdapter(context, R.layout.list_item, listMovie);

                        listView.setAdapter(adapter);

                        tailleListe = listMovie.size();

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

            listView.setOnItemClickListener(this);

        } else {
            finish();
        }
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        // On récupère l'élément sélectionner
        Movie movieSelect = (Movie) parent.getItemAtPosition(position);
        // On affiche sa fiche
        Intent versFicheMovie = new Intent(ListeActivity.this,Portfolio.class);

        String listeGenre = "";
        LinkedList<String> genreLst = movieSelect.getGenre();

        for(String genre : genreLst){
            listeGenre+=genre+", ";
        }

        if(!listeGenre.equals("")){
            listeGenre = listeGenre.substring(0,listeGenre.length()-2);
        }


        versFicheMovie.putExtra("nomFilm", movieSelect.getTitle());
        versFicheMovie.putExtra("image", movieSelect.getImage());
        versFicheMovie.putExtra("description", movieSelect.getDescription());
        versFicheMovie.putExtra("date", movieSelect.getDate());
        versFicheMovie.putExtra("rating", movieSelect.getRating());
        Log.v("ERREUR",movieSelect.getRating()+"");
        versFicheMovie.putExtra("genre", listeGenre);
        startActivity(versFicheMovie);
    }

    /*public void loadGenres()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.themoviedb.org/3/genre/movie/list?api_key=77d0dc6aad700683ac51c5e76d4fc356&language=fr-FR";

        JsonObjectRequest jsObject = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("genres");

                    for (int i=0; i< jsonArray.length(); i++) {

                        JSONObject jsonGenre = jsonArray.getJSONObject(i);

                        int id = Integer.parseInt(jsonGenre.getString("id"));
                        String genre = jsonGenre.getString("name");

                        listGenre.put(id, genre);
                    }
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

    }*/

    public LinkedList<String> getGenres(JSONArray listGenreMovie)
    {
        LinkedList<String> genre = new LinkedList<>();
        for(int i=0; i<listGenreMovie.length(); i++) {
            try {
                if(MainActivity.listGenre.containsKey(Integer.parseInt(listGenreMovie.get(i).toString()))){
                    genre.add(MainActivity.listGenre.get(Integer.parseInt(listGenreMovie.get(i).toString())));
                }
            } catch (JSONException e) {
                Log.v("ERREUR",e.toString());
            }

        }
        return genre;
    }

   /* public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On récupère l'élément sélectionner
        String item = parent.getItemAtPosition(position).toString();
        Movie movieSelect = null;
        // On affiche sa fiche
        Intent versFicheMovie = new Intent(ListeActivity.this,Portfolio.class);
        for(Movie movie : listMovie){
            if(movie.getTitle()==item){
                movieSelect = movie;
                String listGnre = "";
                for(String genre : movie.getGenre()){
                    listGnre=genre+", ";
                }
                if(listGnre!=""){listGnre = listGnre.substring(0,listGnre.length()-2);}
                break;
            }
        }
        versFicheMovie.putExtra("nomFilm", movieSelect.getTitle());
        versFicheMovie.putExtra("image", movieSelect.getImage());
        versFicheMovie.putExtra("description", movieSelect.getDescription());
        versFicheMovie.putExtra("date", movieSelect.getDate());
        versFicheMovie.putExtra("rating", movieSelect.getRating());
        versFicheMovie.putExtra("genre", listGnre);
        startActivity(versFicheMovie);
    }*/
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
