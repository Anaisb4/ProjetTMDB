package com.example.anais.projettmdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Spinner nbr = (Spinner) findViewById(R.id.nbr);
        final EditText edit = (EditText) findViewById(R.id.nomFilm);

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
                EditText nomFilm = (EditText) findViewById(R.id.nomFilm);
                Intent versRecherche = new Intent(MainActivity.this, ListeActivity.class);
                versRecherche.putExtra("nomFilm", nomFilm.getText().toString());
                versRecherche.putExtra("nbElem", nbr.getSelectedItem().toString());

                if(!edit.getText().toString().equals(""))
                {
                    startActivity(versRecherche);
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
