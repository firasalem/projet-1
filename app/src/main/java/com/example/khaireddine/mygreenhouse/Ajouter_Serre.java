package com.example.khaireddine.mygreenhouse;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

public class Ajouter_Serre extends AppCompatActivity {

    private EditText Field_nom_serre;
    private EditText Field_surface;
    private EditText Field_nombre_plateau;
    private EditText Field_adresse;
    private TextView label_fonctionnement;
    private RadioButton buttonauto;
    private RadioButton buttonmanuelle;
    private RadioGroup radiogrp;


String mode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter__serre);
        Field_nom_serre=(EditText)findViewById(R.id.nom_serre);
        Field_surface=(EditText)findViewById(R.id.hauteur);
        label_fonctionnement= (TextView) findViewById(R.id.fct);
        Field_nombre_plateau=(EditText)findViewById(R.id.nombre_plateau);
        Field_adresse = (EditText) findViewById(R.id.adr);
radiogrp=(RadioGroup)findViewById(R.id.radiofct);

    }
    public void nouveau_serre(View view) throws UnsupportedEncodingException {
     //   Intent get_msg=getIntent();
     //   String name=get_msg.getStringExtra("KEY_SERRE");
        SharedPreferences get_inf=getSharedPreferences("KEY_IDENTITY",MODE_PRIVATE);
       String name=get_inf.getString("KEY_IDENTITY_INFO","");
        add_serre(name);
    }
    private void add_serre(String user) throws UnsupportedEncodingException {
        class Create_serre extends AsyncTask<String, String, String> {
            String result;
            private Context context;
            private int request = 0;
            String Input_nom_serre;
            String Input_nombre_plateau;
            String Input_adresse;
            String Input_surface;

            //flag 0 means get and 1 means post.(By default it is get.)
            public Create_serre(Context context, int flag) {
                this.context = context;
                this.request = flag;
            }
            @Override
            protected String doInBackground(String[]arg0) {
                try{
                    String link=(String)arg0[0];
                    URL url = new URL(link);
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(new URI(link));
                    HttpResponse response = client.execute(request);
                    BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";
                    while ((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    in.close();
                    return sb.toString();
                } catch(Exception e){
                    return new String("Exception: " + e.getMessage());
                }


            }
            @Override
            protected void onPostExecute(String result) {
                Field_nom_serre.setError(null);
                Field_nombre_plateau.setError(null);
                Field_adresse.setError(null);
                Field_surface.setError(null);
                boolean verif=true;
                Input_nom_serre=Field_nom_serre.getText().toString();
                Input_nombre_plateau=Field_nombre_plateau.getText().toString();
                Input_adresse=Field_adresse.getText().toString();
                Input_surface=Field_surface.getText().toString();
                if (TextUtils.isEmpty(Input_nom_serre)) {Field_nom_serre.setError("Champ vide");verif=false;}
                if (TextUtils.isEmpty(Input_nombre_plateau)) {Field_nombre_plateau.setError("Champ vide");verif=false;}
                if (TextUtils.isEmpty(Input_adresse)) {Field_adresse.setError("Champ vide");verif=false;}
                if (TextUtils.isEmpty(Input_surface)) {Field_surface.setError("Champ vide");verif=false;}
                if (verif)
                {
                    if (result.equals("ok"))
                    {
                        Intent intent_ajoutserre = new Intent(getApplicationContext(), Mes_Seres.class);
                        startActivity(intent_ajoutserre);
                    }
                    else if (result.equals("0")){
                       Field_nom_serre.setError("Identifient déja existe");}
                    else
                    { //Toast.makeText(getApplicationContext(),get_nom,Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),"Probléme de Connexion",Toast.LENGTH_SHORT).show();
                    }

                }}}
        String nom=Field_nom_serre.getText().toString();
        String surface=Field_surface.getText().toString();;
        String nbr_plat=Field_nombre_plateau.getText().toString();;
        String localisation= Field_adresse.getText().toString();

        new Create_serre(this,0).execute("http://192.168.43.94/pfe/insertserre.php?nombre_plateau="+nbr_plat+"&surface="+surface+"&localisation="+localisation+"&fonctionnement="+mode+"&nom_serre="+ URLEncoder.encode(nom,"UTF-8")+"&username="+user);
    }


    public void is_checked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.auto:
                if (checked)
                    mode="automatique";
                Toast.makeText(this,mode,Toast.LENGTH_SHORT).show();
                break;
            case R.id.manuelle:
                if (checked)
                    mode="manuelle";
                Toast.makeText(this,mode,Toast.LENGTH_SHORT).show();
                break;
        }
    }}
