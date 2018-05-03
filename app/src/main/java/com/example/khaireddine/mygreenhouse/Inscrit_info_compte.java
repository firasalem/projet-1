package com.example.khaireddine.mygreenhouse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.text.Format;

public class Inscrit_info_compte extends AppCompatActivity {
    private EditText FieldEMail,FieldMdp,FieldMdp2,FieldUsername;
    String Input_mail,Input_mdp,Input_mdp2,Input_username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscrit_info_compte);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_compte);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FieldEMail = (EditText) findViewById(R.id.mail_inscrit);
        FieldMdp = (EditText) findViewById(R.id.password_inscrit);
        FieldMdp2 = (EditText) findViewById(R.id.password2_inscrit);
        FieldUsername=(EditText) findViewById(R.id.username_inscrit);
    }
    public void Inscription_Accueil(View view)
    {
      add_user();
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@")&&email.contains(".com");
    }
    private void add_user() {
        class CreateAccount extends AsyncTask<String, String, String> {
            String result;
            private Context context;
            private int request = 0;
            //flag 0 means get and 1 means post.(By default it is get.)
            public CreateAccount(Context context, int flag) {
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
                FieldEMail.setError(null);
                FieldMdp.setError(null);
                FieldMdp2.setError(null);
                boolean verif1=true;
                Input_mail=FieldEMail.getText().toString();
                Input_mdp=FieldMdp.getText().toString();
                Input_mdp2=FieldMdp2.getText().toString();
                if (TextUtils.isEmpty(Input_mail)) {FieldEMail.setError("Champ vide");verif1=false;}
                else if (!isEmailValid(Input_mail)) {FieldEMail.setError("Email invalide");verif1=false;}
                if (TextUtils.isEmpty(Input_mdp)) {FieldMdp.setError("Champ vide");verif1=false;}
                else if (Input_mdp.length()<8) {FieldMdp.setError("Champ vide");verif1=false;}
                if (TextUtils.isEmpty(Input_mdp2)) {FieldMdp2.setError("champ vide");verif1=false;}
                else if (!(Input_mdp2.equals(Input_mdp))) {
                    FieldMdp2.setError("mode de passe incorrect");
                    FieldMdp.setError("mode de passe incorrect");
                    verif1=false;}
                if (verif1){
                    Intent get_data=getIntent();
                    String get_nom=get_data.getStringExtra("KEY_nom");
                    String get_prenom=get_data.getStringExtra("KEY_prenom");
                    Input_username=FieldUsername.getText().toString();
                    if (result.equals("ok"))
                    {
                    Intent intent_Accueil = new Intent(getApplicationContext(), Accueil.class);
                        intent_Accueil.putExtra("KEY_1_name",get_nom);
                        intent_Accueil.putExtra("KEY_last_name",get_prenom);
                        intent_Accueil.putExtra("KEY_user",Input_username);
                        startActivity(intent_Accueil);
                        SharedPreferences.Editor editor=getSharedPreferences("KEY_inscription_share",MODE_PRIVATE).edit();
                        editor.putString("KEY_inscription",get_prenom+" "+get_nom);
                        editor.commit();
                        SharedPreferences.Editor usser_editor=getSharedPreferences("KEY_IDENTITY",MODE_PRIVATE).edit();
                        usser_editor.putString("KEY_IDENTITY_INFO", FieldUsername.getText().toString());
                        editor.commit();
                    }
                    else
                    { //Toast.makeText(getApplicationContext(),get_nom,Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),"Probléme de Connexion",Toast.LENGTH_SHORT).show();
                    }

                }}}
        Intent get_data=getIntent();
        String get_nom=get_data.getStringExtra("KEY_nom");
        String get_prenom=get_data.getStringExtra("KEY_prenom");
        String get_cin=get_data.getStringExtra("KEY_cin");
        String get_num=get_data.getStringExtra("KEY_numero");
        Input_mail=FieldEMail.getText().toString();
        Input_mdp=FieldMdp.getText().toString();
        Input_mdp2=FieldMdp2.getText().toString();
        Input_username=FieldUsername.getText().toString();
        new CreateAccount(this,0).execute("http://192.168.43.94/pfe/insert_user.php?username="+Input_username+"&firstname="+get_nom+"&lastname="+get_prenom+"&email="+Input_mail+"&password="+Input_mdp+"&cin="+get_cin+"&num_tel="+get_num+"&state=1");
    }
}
