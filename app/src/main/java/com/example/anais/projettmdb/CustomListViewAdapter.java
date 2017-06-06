package com.example.anais.projettmdb;

/**
 * Created by Victor and Anais on 05/06/2017.
 */

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;
import com.example.anais.projettmdb.R;
import com.example.anais.projettmdb.Movie;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomListViewAdapter extends ArrayAdapter<Movie> {

    Context context;

    //constructeur de la classe
    public CustomListViewAdapter(Context context, int ressourceId, List<Movie> movies){

        super(context, ressourceId, movies);
        this.context = context;

    }

    /*private view holder class*/
    private class ViewHolder{
        ImageView imageView;
        TextView txtTitle;
        TextView txtGenre;
        TextView txtDate;
        TextView txtNote;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        Movie movie = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
        {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txtGenre = (TextView) convertView.findViewById(R.id.genre);
            holder.txtDate = (TextView) convertView.findViewById(R.id.date);
           // holder.txtNote = (TextView) convertView.findViewById(R.id.note);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);

            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();


        LinkedList<String> listeGenre = new LinkedList<>();
        listeGenre.addAll(movie.getGenre());
        String genre_tostring = "";

        for(String genre : listeGenre){
            genre_tostring += genre + ", ";
        }

        holder.txtGenre.setText(genre_tostring);
        holder.txtDate.setText(movie.getDate());
       //holder.txtNote.setText(movie.getRating().toString());
        holder.txtTitle.setText(movie.getTitle());
        holder.imageView.setImageResource(movie.getImageId());

        return convertView;
    }

}
