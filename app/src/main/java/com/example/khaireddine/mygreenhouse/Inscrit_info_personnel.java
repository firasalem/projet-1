package com.example.khaireddine.mygreenhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.text.Format;

public class Inscrit_info_personnel extends AppCompatActivity {
    EditText FieldCin,FieldNumber,FieldNom,FieldPrenom;
    String cin,number,nom,prenom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscrit_info_personnel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FieldCin=(EditText)findViewById(R.id.cin);
        FieldNumber=(EditText)findViewById(R.id.numero);
        FieldNom=(EditText)findViewById(R.id.nom);
        FieldPrenom=(EditText)findViewById(R.id.prenom);

    }

    public void To_compte_info(View view)
    {boolean verif=true;
    FieldNumber.setError(null);
        FieldNom.setError(null);
        FieldPrenom.setError(null);
        FieldCin.setError(null);
        nom= FieldNom.getText().toString();
        prenom=FieldPrenom.getText().toString();
        cin=FieldCin.getText().toString();
        number=FieldNumber.getText().toString();
        if (TextUtils.isEmpty(nom)) {FieldNom.setError("Champ vide");verif=false;}
        if(TextUtils.isEmpty(prenom)) {FieldPrenom.setError("Champ vide");verif=false;}
        if (TextUtils.isEmpty(cin)){FieldCin.setError("Champ vide");verif=false;}
        else if(cin.length()!=8){FieldCin.setError("CIN invalide");verif=false;}
        if (TextUtils.isEmpty(number)){FieldNumber.setError("Champ vide");verif=false;}
        else if(number.length()!=8){FieldNumber.setError("Numero invalide");verif=false;}
        if (verif){  Intent intent_Accueil = new Intent(this,Inscrit_info_compte.class);
                intent_Accueil.putExtra("KEY_nom",nom);
                intent_Accueil.putExtra("KEY_prenom",prenom);
                intent_Accueil.putExtra("KEY_numero",number);
                intent_Accueil.putExtra("KEY_cin",cin);
                startActivity(intent_Accueil);}
}
}
