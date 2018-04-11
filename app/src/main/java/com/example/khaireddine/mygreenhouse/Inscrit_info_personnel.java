package com.example.khaireddine.mygreenhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Inscrit_info_personnel extends AppCompatActivity {
    EditText FieldMail,FieldPassword,FieldPassword2,FieldCin,FieldNumber,FieldNom,FieldPrenom;
    TextView label_mail,label_password,label_password2,label_cin,label_numero,label_nom,label_prenom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscrit_info_personnel);
        FieldCin=(EditText)findViewById(R.id.cin);
        FieldNumber=(EditText)findViewById(R.id.numero);
        FieldNom=(EditText)findViewById(R.id.nom);
        FieldPrenom=(EditText)findViewById(R.id.prenom);
        label_cin=(TextView) findViewById(R.id.cin_label);
        label_nom=(TextView) findViewById(R.id.nom_label);
        label_numero=(TextView) findViewById(R.id.numero_label);
        label_prenom=(TextView) findViewById(R.id.prenom_label);
        setfocus(FieldNom,label_nom);
        setfocus(FieldPrenom,label_prenom);
        setfocus(FieldCin,label_cin);
        setfocus(FieldNumber,label_numero);
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
                else
                { label.setVisibility(View.INVISIBLE);
                    field.setHint(hint);}

            }});


    }

    public void To_compte_info(View view) {
        Intent intent_inscription = new Intent(this,Inscrit_info_compte.class);
        startActivity(intent_inscription);
    }
}
