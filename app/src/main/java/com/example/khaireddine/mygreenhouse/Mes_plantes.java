package com.example.khaireddine.mygreenhouse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Mes_plantes extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_plantes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_plante);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_inscription = new Intent(Mes_plantes.this, Ajouter_plantes.class);
                startActivity(intent_inscription);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.list);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        SharedPreferences get_inf=getSharedPreferences("KEY_IDENTITY",MODE_PRIVATE);
        final String name=get_inf.getString("KEY_IDENTITY_INFO","");
        plantes(name);
    }
//TODO: /////////////////////////Afficher les plantes correspendant///////////////////////////////////////
void plantes(String chaine){
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
                URL url = new URL("http://192.168.43.94/pfe/selectnomplante.php?username="+username);

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
                loadIntoRecycleView(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    new Get_info(this,0).execute(chaine);
}
    private void loadIntoRecycleView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        List<String> plantes= new ArrayList<>();
        if (jsonArray.length()==0){
            Toast.makeText(Mes_plantes.this,"vous n'avez pas de plante",Toast.LENGTH_LONG).show();}
            else {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            plantes.add( obj.getString("nom"));
        }
        mAdapter = new Adapter_plantes_types(plantes);
        recyclerView.setAdapter(mAdapter);

    }}


}
