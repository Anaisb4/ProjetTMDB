package com.example.anais.projettmdb;

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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.anais.projettmdb.R;
import com.example.anais.projettmdb.CustomListViewAdapter;
import com.example.anais.projettmdb.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity implements OnItemClickListener {

    List<Movie> listMovie;
    HashMap<Integer, String> listGenre;
    ListView listView;

    RequestQueue request;

    /** Appelé à la création de l'activité **/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listMovie = new ArrayList<Movie>();
        listGenre = new HashMap<Integer, String>();

        this.request = Volley.newRequestQueue(this);

        listView = (ListView) findViewById(R.id.list);

        loadGenres();
        getMovies("Tarzan");


        CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.list_item, listMovie);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        Toast toast = Toast.makeText(getApplicationContext(), "Item " + (position + 1) + ": " + listMovie.get(position), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }

    public void getMovies(String nomFilm){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.themoviedb.org/3/search/movie?api_key=77d0dc6aad700683ac51c5e76d4fc356&query=Tarzan&page=1";


        final JsonObjectRequest jsObject = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray jsonArray = response.getJSONArray("results");

                    for (int i=0; i< jsonArray.length(); i++) {

                        JSONObject jsonMovie = jsonArray.getJSONObject(i);
                        JSONArray genreID = jsonMovie.getJSONArray("genre_ids");

                        //A IMPLEMENTER
                        LinkedList<String> genreList = new LinkedList<String>();
                        genreList.addAll(getGenres(genreID));//getGenre --> return LinkedList genre

                        //A IMPLEMENTER
                        String title = jsonMovie.getString("title");
                        String image = jsonMovie.getString("backdrop_path");
                        String description = jsonMovie.getString("overview");
                        String origine = jsonMovie.getString("original_language");
                        String  date = jsonMovie.getString("release_date");
                        Double rating = Double.parseDouble(jsonMovie.getString("vote_average"));

                        Movie newMovie = new Movie(image,title, description,origine,date,genreList, rating);
                        listMovie.add(newMovie);

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
}
