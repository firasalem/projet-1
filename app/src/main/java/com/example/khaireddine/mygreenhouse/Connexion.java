package com.example.khaireddine.mygreenhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

public class Connexion extends AppCompatActivity {
    EditText FieldMail,FieldPassword;
    TextView label_mail,label_password,erreur_mail,erreur;
    String mail,mdp;
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
        erreur=(TextView)findViewById(R.id.erreur);
        erreur_mail=(TextView)findViewById(R.id.erreur_mail);
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
        mail=FieldMail.getText().toString();
        mdp= FieldPassword.getText().toString();
        if (test(mail,mdp)) {
            Intent intent_inscription = new Intent(this, Inscrit_info_personnel.class);
            startActivity(intent_inscription);
        }


    }

    private boolean test( String mail, String mdp) {
        if (!isEmailValid(mail)) {
            erreur_mail.setVisibility(View.VISIBLE);
            erreur_mail.setText("Adresse Email valide");
            return false;
        }
        else if ((!mdp.equals("28324821"))|| (!mail.equals("khaireddineguesmi@gmail.com")))
        {erreur.setVisibility(View.VISIBLE);
            erreur.setText("Invalide email ou Mot de passe");
        return false;
        }
        else{
            erreur_mail.setVisibility(View.INVISIBLE);
            erreur.setVisibility(View.INVISIBLE);
            return true;}
        }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        if (email.contains("@")&&email.contains(".com"))
        return true;
        else return false;
    }
    public void To_connexion(View view) {

    }
}
