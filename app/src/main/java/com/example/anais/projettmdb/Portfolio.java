package com.example.anais.projettmdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Portfolio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);
        Intent intent = getIntent();
        //Récupération des éléments
        String nomFilm =intent.getExtras().getString("nomFilm");
        String image = intent.getExtras().getString("image");
        String description =intent.getExtras().getString("description");
        String date = intent.getExtras().getString("date");
        float rating =intent.getExtras().getFloat("rating");
        String genre = intent.getExtras().getString("genre");

        //Affichage des éléments
        TextView titre = (TextView) findViewById(R.id.titre);
        ImageView imagePort = (ImageView) findViewById(R.id.image_port);
        TextView dateText = (TextView) findViewById(R.id.date);
        TextView categorie = (TextView) findViewById(R.id.categorie);
        TextView descriptionText = (TextView) findViewById(R.id.description);
        RatingBar note = (RatingBar) findViewById(R.id.rating);

        Picasso.with(this).load(image).into(imagePort);
        titre.setText(nomFilm);
        dateText.setText(date);
        categorie.setText(genre);
        descriptionText.setText(description);
        note.setRating(rating);
    }
}
