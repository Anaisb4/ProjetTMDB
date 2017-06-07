package com.example.anais.projettmdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    final String EXTRA_NOMFILM = "nom_film";
    final String EXTRA_NBRELEM = "nbr_elem";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final Spinner nbr = (Spinner) findViewById(R.id.nbr);
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
                Intent versRecherche = new Intent(Main2Activity.this, MainActivity.class);
                versRecherche.putExtra(EXTRA_NOMFILM, nomFilm.getText().toString());
                versRecherche.putExtra(EXTRA_NBRELEM, nbr.getSelectedItem().toString());
                startActivity(versRecherche);
            }
        });
    }
}
