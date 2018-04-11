package com.example.khaireddine.mygreenhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Connexion extends AppCompatActivity {
    EditText FieldMail,FieldPassword;
    TextView label_mail,label_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        Toolbar toolbar =   (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FieldMail=(EditText)findViewById(R.id.mail);
        FieldPassword=(EditText)findViewById(R.id.password);
        label_mail= (TextView) findViewById(R.id.mail_label);
        label_password=(TextView) findViewById(R.id.password_label);
        FieldMail.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus==true)
                {
                    label_mail.setVisibility(View.VISIBLE);
                    FieldMail.setHint("");
                }
                else
                { label_mail.setVisibility(View.INVISIBLE);
                    FieldMail.setHint("Adresse mail");}

            }});
        FieldPassword.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus==true)
                {
                    label_password.setVisibility(View.VISIBLE);
                    FieldPassword.setHint("");
                }
                else
                { label_password.setVisibility(View.INVISIBLE);
                    FieldPassword.setHint("Mot de passe");}
            }});
    }

    public void ToInscription(View view) {
        Intent intent_inscription = new Intent(this, Inscrit_info_personnel.class);
        startActivity(intent_inscription);
    }

    public void To_connexion(View view) {

    }
}
