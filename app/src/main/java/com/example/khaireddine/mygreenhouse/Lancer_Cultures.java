package com.example.khaireddine.mygreenhouse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class Lancer_Cultures extends AppCompatActivity {
    String[] SPINNERLIST = {"Tomate", "Fraise", "Orge", "pepper"};
    String[] SPINNER_LIST_SERRE = {"SERRE1", "SERRE2", "SERRE3", "SERRE4"};
    private String mSpinnerPlante = "";
    Spinner plant_spinner,serre_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancer__cultures);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
              R.layout.spinnertxt, SPINNERLIST);
       plant_spinner = (Spinner)
                findViewById(R.id.plant_spinner_choix);
        plant_spinner.setAdapter(arrayAdapter);
        ArrayAdapter<String>serreAdapter = new ArrayAdapter<String>(this,
                R.layout.spinnertxt, SPINNER_LIST_SERRE);
       serre_spinner = (Spinner)
                findViewById(R.id.serre_spinner_choix);
        serre_spinner.setAdapter(serreAdapter);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_items);
        serreAdapter.setDropDownViewResource(R.layout.spinner_items);
    }
    }

