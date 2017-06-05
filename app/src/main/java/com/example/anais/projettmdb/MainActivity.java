package com.example.anais.projettmdb;

import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import com.example.anais.projettmdb.R;
import com.example.anais.projettmdb.CustomListViewAdapter;
import com.example.anais.projettmdb.Movie;



public class MainActivity extends Activity implements OnItemClickListener {

    public static final String[] titles = new String[] {"Logan", "SpiderMan 2", "Terminator", "Logan", "SpiderMan 2", "Terminator", "Logan", "SpiderMan 2", "Terminator","Alien Covenant"};

    public static final String[] descriptions = new String[] {
            "Action, Thriller, Réaction",
            "Un film d'araignées",
            "Un film de robot",
            "Un film de griffes",
            "Un film d'araignées",
            "Un film de robot",
            "Un film de griffes",
            "Un film d'araignées",
            "Un film de robot",
            "Un film d'aliens"};

    public static final Integer[] images = {
            R.mipmap.logan,
            R.mipmap.spiderman,
            R.mipmap.terminator2,
            R.mipmap.logan,
            R.mipmap.spiderman,
            R.mipmap.terminator2,
            R.mipmap.logan,
            R.mipmap.spiderman,
            R.mipmap.terminator2,

            R.mipmap.aliencovenantaffiche
        };

    ListView listView;

    List<Movie> listMovie;

    /** Appelé à la création de l'activité **/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listMovie = new ArrayList<Movie>();



        /** POOUR TEST **/
        LinkedList<String> listeBidon = new LinkedList<String>();
        listeBidon.add("Action");
        listeBidon.add("Thriller");
        listeBidon.add("Réaction");
        /** POOUR TEST **/


        for (int i = 0; i < titles.length; i++) {
            Movie movie = new Movie(images[i], titles[i], descriptions[i], "France", "2017-01-12", listeBidon, 10.0);
            listMovie.add(movie);
        }

        listView = (ListView) findViewById(R.id.list);

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



}
