package com.example.khaireddine.mygreenhouse;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Mes_Seres extends AppCompatActivity {

String msg_name1;
String name;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes__serres);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_serre);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        gridView = (GridView) findViewById(R.id.gridView1);
       // Intent get_msg=getIntent();
      // name=get_msg.getStringExtra("KEY_SERRE");
        SharedPreferences get_inf=getSharedPreferences("KEY_IDENTITY",MODE_PRIVATE);
        final String name=get_inf.getString("KEY_IDENTITY_INFO","");

        serres(name);
        FloatingActionButton add=(FloatingActionButton)findViewById(R.id.fab);
        //TODO: set alert dialog for serre information
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
               // Intent intent = new Intent(Mes_Seres.this, information_serrre.class);
               // startActivity(intent);
                TextView nom=v.findViewById(R.id.grid_item_label);
                String nom_serre=nom.getText().toString();
              //  serre_information(name,);
                serre_information(nom_serre,name);
             /*   AlertDialog.Builder builder = new AlertDialog.Builder(Mes_Seres.this);
                builder.setTitle(nom_serre);
                builder.setIcon(R.drawable.item_greenhouse);
                builder.setItems(colors_array,null);
                builder.setPositiveButton("Ok",null);
                builder.show();*/
            }
        });

        //TODO: set intent for the fab butoon
        add.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                Intent intent = new Intent(Mes_Seres.this, Ajouter_Serre.class);
                startActivity(intent);
            }
        });
            }
    //TODO: Ajouter les serre correspendant de l'utilisateur
    public void serres(String chaine){
    class Get_info extends AsyncTask<String, String, String> {
        String result;
        private Context context;
        private int request = 0;
        //flag 0 means get and 1 means post.(By default it is get.)
        public Get_info(Context context, int flag) {
            this.context = context;
            this.request = flag;
        }
        @Override
        protected String doInBackground(String[]arg0) {
            try {
                String username=(String)arg0[0];
                URL url = new URL("http://192.168.43.94/pfe/selectserre.php?email="+username);

                //Opening the URL using HttpURLConnection
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                //StringBuilder object to read the string from the service
                StringBuilder sb = new StringBuilder();

                //We will use a buffered reader to read the string from service
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                //A simple string to read values from each line
                String json;

                //reading until we don't find null
                while ((json = bufferedReader.readLine()) != null) {

                    //appending it to string builder
                    sb.append(json + "\n");
                }

                //finally returning the read string
                return sb.toString().trim();
            } catch (Exception e) {
                return "erreur";
            }

        }
        @Override
        protected void onPostExecute(String result) {
            try {
               // Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
                loadIntoGridView(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    new Get_info(this,0).execute(chaine);
    }
    private void loadIntoGridView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        if (jsonArray.length()==0){
            Toast.makeText(Mes_Seres.this,"vous n'avez pas de serre \n penser a ajouter serres",Toast.LENGTH_LONG).show();}
            else {
        String[] serres = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
           serres[i] = obj.getString("nom_serre");
        }
        gridView.setAdapter(new serreadapter(this, serres));

    }}
    void serre_information(String nom_serre,String nom_user){
        class Get_info extends AsyncTask<String, String, String> {
            String result;
            private Context context;
            private int request = 0;
            //flag 0 means get and 1 means post.(By default it is get.)
            public Get_info(Context context, int flag) {
                this.context = context;
                this.request = flag;
            }
            @Override
            protected String doInBackground(String[]arg0) {
                try {
                    String nom_serre=(String)arg0[0];
                    String username=(String)arg0[1];

                    URL url = new URL("http://192.168.43.94/pfe/info_serre.php?nom_serre="+ URLEncoder.encode(nom_serre, "UTF-8")+"&username="+username);

                    //Opening the URL using HttpURLConnection
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    //StringBuilder object to read the string from the service
                    StringBuilder sb = new StringBuilder();

                    //We will use a buffered reader to read the string from service
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    //A simple string to read values from each line
                    String json;

                    //reading until we don't find null
                    while ((json = bufferedReader.readLine()) != null) {

                        //appending it to string builder
                        sb.append(json + "\n");
                    }

                    //finally returning the read string
                    return sb.toString().trim();
                } catch (Exception e) {
                    return "erreur";
                }

            }
            @Override
            protected void onPostExecute(String result) {
                try {
                    loadIntoDialog_alert(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }
        new Get_info(this,0).execute(nom_serre,nom_user);
    }
    private void loadIntoDialog_alert(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] serres_info = new String[8];
            JSONObject obj = jsonArray.getJSONObject(0);
            String identifient = obj.getString("nom_serre");
            serres_info[0] = "Localisation : "+obj.getString("localisation");
            serres_info[1] = "Surface : "+obj.getString("surface");
            serres_info[2] = "Nombre de plateau : "+obj.getString("nombre_plateau");
            if (obj.getString("etat").equals("0")) {
                serres_info[3] ="Etat : Vide";
                serres_info[5] =" ";
                serres_info[6] =" ";
                serres_info[7]="";
            }
            else {
                serres_info[3] ="Etat : Utiliser";
                serres_info[5] = "Culture en cours : "+obj.getString("nom");
                String date=obj.getString("date_debut");
                String format=date.substring(8,10)+"/"+date.substring(5,7)+"/"+date.substring(0,4);
                serres_info[6] = "date de plantaion : "+format;

                String date_fin=obj.getString("date_fin");
                String format_fin=date_fin.substring(8,10)+"/"+date_fin.substring(5,7)+"/"+date_fin.substring(0,4);
                serres_info[7] = "date de recoltation : "+format_fin;
            }
        if (obj.getString("fonctionnement").equals("manuelle")) {serres_info[4] ="Fonctionnement :Manuelle";}
        else if (obj.getString("fonctionnement").equals("automatique")){serres_info[4] ="Fonctionnement :Automatique";}

           else { serres_info[4] ="Mode de fonctionnement :Non defini";}


      AlertDialog.Builder builder = new AlertDialog.Builder(Mes_Seres.this);
        builder.setTitle(identifient);
        builder.setIcon(R.drawable.item_greenhouse);
        builder.setItems(serres_info,null);
        builder.setPositiveButton("Ok",null);
        builder.show();
        //Toast.makeText(getApplicationContext(),identifient,Toast.LENGTH_SHORT).show();



    }
}
