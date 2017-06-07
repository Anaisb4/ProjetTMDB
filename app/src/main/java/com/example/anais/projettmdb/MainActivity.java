package com.example.anais.projettmdb;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
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


public class MainActivity extends Activity implements OnItemClickListener {
    int tailleListe = 0;
    int nbrAffiche = 0;
    ArrayList<Movie> listMovie;
    HashMap<Integer, String> listGenre;
    ListView listView;
    ArrayList<Movie> listMovieAffiche;
    int nbrElem;
    int nbrMoviePage;
    RequestQueue request;

    /** Appelé à la création de l'activité **/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button prec = (Button) findViewById(R.id.boutonPrecedent);
        final Button suiv = (Button) findViewById(R.id.boutonSuivant);

        //Récupération des éléemnts passer entre activité
        Intent intent = getIntent();
        if (intent != null) {
            String nomFilm = intent.getExtras().getString("nomFilm");
            nbrElem = Integer.parseInt(intent.getExtras().getString("nbElem"));
            listMovie = new ArrayList<Movie>();
            listGenre = new HashMap<Integer, String>();

            this.request = Volley.newRequestQueue(this);

            listView = (ListView) findViewById(R.id.list);
            loadGenres();
            getMovies(nomFilm);
            Log.v("ERREUR", String.valueOf(this.listMovie.size()));
            /*if(tailleListe!=0) {
                int i = 0;
                listMovieAffiche=new ArrayList<>();
                while (tailleListe<=nbrAffiche && i<=nbrElem){
                    listMovieAffiche.add(listMovie.get(i));
                    i++;
                    nbrAffiche++;
                }
                nbrMoviePage=i;
                //On désactive le clique sur le bouton précédent à la première exécution
                prec.setEnabled(false);*/
                CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, this.listMovie);
                listView.setAdapter(adapter);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(this);
            /*} else {
                Toast.makeText(this, "La recherche n'a renvoyé aucun résultat", Toast.LENGTH_SHORT).show();
                finish();
            }*/
        } else {
            finish();
        }

        //Au clic sur les boutons suivant où précédent on charge les nouveaux films
        /*prec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On charge les n précédents films
                listMovieAffiche.clear();
                nbrAffiche=nbrAffiche-nbrMoviePage-nbrElem;
                int i=nbrAffiche;
                while (tailleListe<=nbrAffiche && i<=nbrAffiche+nbrElem){
                    listMovieAffiche.add(listMovie.get(i));
                    i++;
                    nbrAffiche++;
                }
                nbrMoviePage=i;
                if(nbrAffiche<=nbrElem){
                    //Si le nombre d'élement afficher correspond à une seule page alors on ne peux pas cliquer sur précédent
                    prec.setEnabled(false);
                }
            }
        });
        suiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On charge les n précédents films
                listMovieAffiche.clear();
                int i=0;
                while (tailleListe<=nbrAffiche && i<=nbrElem){
                    listMovieAffiche.add(listMovie.get(i));
                    i++;
                    nbrAffiche++;
                }
                nbrMoviePage=i;
                if(nbrAffiche==tailleListe){
                    //Si le nombre d'élement afficher correspond au nombre de film trouvé alors on ne peux pas cliquer sur suivant
                    suiv.setEnabled(false);
                }
            }
        });*/
    }

    public void afficheElem(){

    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Toast toast = Toast.makeText(getApplicationContext(), "Item " + (position + 1) + ": " + listMovie.get(position), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void getMovies(String nomFilm){
        listMovie = new ArrayList<Movie>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.themoviedb.org/3/search/movie?api_key=77d0dc6aad700683ac51c5e76d4fc356&query="+nomFilm+"&page=1";


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
    }

    public void loadGenres()
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



    }

    public LinkedList<String> getGenres(JSONArray listGenreMovie)
    {
        LinkedList<String> genre = new LinkedList<>();
        for(int i=0; i<listGenreMovie.length(); i++) {
            try {
                if(listGenre.containsKey(Integer.parseInt(listGenreMovie.get(i).toString()))){
                    genre.add(listGenre.get(Integer.parseInt(listGenreMovie.get(i).toString())));
                }
            } catch (JSONException e) {
                Log.v("ERREUR",e.toString());
            }

        }
        return genre;
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On récupère l'élément sélectionner
        String item = parent.getItemAtPosition(position).toString();
        Movie movieSelect = null;
        // On affiche sa fiche
        Intent versFicheMovie = new Intent(MainActivity.this,Portfolio.class);
        for(Movie movie : listMovie){
            if(movie.getTitle()==item){
                movieSelect = movie;
                String listeGenre = "";
                for(String genre : movie.getGenre()){
                    listeGenre=genre+", ";
                }
                if(listeGenre!=""){listeGenre = listeGenre.substring(0,listeGenre.length()-2);}
                break;
            }
        }
        versFicheMovie.putExtra("nomFilm", movieSelect.getTitle());
        versFicheMovie.putExtra("image", movieSelect.getImage());
        versFicheMovie.putExtra("description", movieSelect.getDescription());
        versFicheMovie.putExtra("date", movieSelect.getDate());
        versFicheMovie.putExtra("rating", movieSelect.getRating());
        versFicheMovie.putExtra("genre", listGenre);
        startActivity(versFicheMovie);
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}
