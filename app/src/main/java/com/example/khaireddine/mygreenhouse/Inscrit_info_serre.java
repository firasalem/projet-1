package com.example.khaireddine.mygreenhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class Inscrit_info_serre extends AppCompatActivity {
    String[] SPINNERLIST = {"Tomate", "Fraise", "Orge", "pepper"};
    private String mSpinnerPlante = "";
    MaterialBetterSpinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscrit_info_serre);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        MaterialBetterSpinner materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.plant_spinner);
        materialDesignSpinner.setAdapter(arrayAdapter);
    }

    public void To_accueil(View view) {
        Intent intent_inscription = new Intent(this, Accueil.class);
        startActivity(intent_inscription);
    }
}
