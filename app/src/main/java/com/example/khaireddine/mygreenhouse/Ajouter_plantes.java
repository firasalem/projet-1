package com.example.khaireddine.mygreenhouse;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.lang.reflect.Field;

public class Ajouter_plantes extends AppCompatActivity  {
    EditText FieldNom,FieldFamille,FieldCycle,FieldPeriode;
    TextView label_nom,label_famille,label_cycle,label_periode;
    int nbr_periode;
    String ch_nbr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_plantes);
        FieldNom=(EditText)findViewById(R.id.nom_plante);
        FieldFamille=(EditText)findViewById(R.id.famille);
        FieldCycle=(EditText)findViewById(R.id.cycle);
        FieldPeriode=(EditText)findViewById(R.id.nbr_periode);
        label_cycle=(TextView)findViewById(R.id.label_cycle);
        label_famille=(TextView)findViewById(R.id.label_famille);
        label_nom=(TextView)findViewById(R.id.label_nom_plante);
        label_periode=(TextView)findViewById(R.id.label_nbr_periode);
       setfocus(FieldNom,label_nom);
        setfocus(FieldFamille,label_famille);
        setfocus(FieldCycle,label_cycle);
        setfocus(FieldPeriode,label_periode);
    }
    public  void setfocus(final EditText field, final TextView label)
    {
        final String hint= label.getText().toString();
        field.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus==true)
                {
                    label.setVisibility(View.VISIBLE);
                    field.setHint("");
                }
                else {
                    String ch = field.getText().toString();
                    if (TextUtils.isEmpty(ch)) {
                        label.setVisibility(View.INVISIBLE);
                        field.setHint(hint);
                    }
                    else {label.setVisibility(View.VISIBLE);}
                }

            }});


    }

    public void To_Cycle_vie(View view) {
        Intent intent = new Intent(this, Cycle_Vie.class);
        ch_nbr=FieldPeriode.getText().toString();
        intent.putExtra("KEY", ch_nbr);
        intent.putExtra("KEY1", ch_nbr);
        startActivity(intent);
    }
}
