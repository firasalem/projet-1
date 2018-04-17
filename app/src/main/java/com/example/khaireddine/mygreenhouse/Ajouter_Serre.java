package com.example.khaireddine.mygreenhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Ajouter_Serre extends AppCompatActivity {

    private EditText Fieldnomserre;
    private EditText Fieldhauteur;
    private TextView label_nomserre;
    private TextView label_hateur;
    private EditText Fielnombreplateau;
    private TextView label_nombreplateau;
    private TextView label_adresse;
    private EditText Fieldadresse;
    private TextView label_fonctionnement;
    private RadioButton buttonauto;
    private RadioButton buttonmanuelle;
    private RadioGroup radiogrp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter__serre);
        Fieldnomserre=(EditText)findViewById(R.id.nom_serre);
        Fieldhauteur=(EditText)findViewById(R.id.hauteur);
        label_fonctionnement= (TextView) findViewById(R.id.fct);

        label_nomserre= (TextView) findViewById(R.id.label_Nom_Serre);
        label_hateur=(TextView) findViewById(R.id.label_hauteur);
        Fielnombreplateau=(EditText)findViewById(R.id.nombre_plateau);
        label_nombreplateau= (TextView) findViewById(R.id.label_nombre_plateau);
        label_adresse=(TextView) findViewById(R.id.label_adr);
        Fieldadresse = (EditText) findViewById(R.id.adr);

        Fieldnomserre.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus==true)
                {
                    label_nomserre.setVisibility(View.VISIBLE);
                    Fieldnomserre.setHint("");
                }
                else
                { label_nomserre.setVisibility(View.INVISIBLE);
                    Fieldnomserre.setHint("Nom serre");}

            }});
        Fieldhauteur.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus==true)
                {
                    label_hateur.setVisibility(View.VISIBLE);
                    Fieldhauteur.setHint("");
                }
                else
                { label_hateur.setVisibility(View.INVISIBLE);
                    Fieldhauteur.setHint("hauteur");}
            }});

        Fieldadresse.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus==true)
                {
                    label_adresse.setVisibility(View.VISIBLE);
                    Fieldadresse.setHint("");
                }
                else
                { label_adresse.setVisibility(View.INVISIBLE);
                    Fieldadresse.setHint("adresse");}

            }});
        Fielnombreplateau.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus==true)
                {
                    label_nombreplateau.setVisibility(View.VISIBLE);
                    Fielnombreplateau.setHint("");
                }
                else
                { label_nombreplateau.setVisibility(View.INVISIBLE);
                    Fielnombreplateau.setHint("nombre plateau");}
            }});
    }
    public void nouveau_serre(View view) {
        Intent intent_ajoutserre = new Intent(this, Mes_Seres.class);
        startActivity(intent_ajoutserre);
    }

    }

