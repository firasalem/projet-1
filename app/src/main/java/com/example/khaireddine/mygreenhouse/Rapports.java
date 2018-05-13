package com.example.khaireddine.mygreenhouse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class Rapports extends AppCompatActivity  {
MaterialBetterSpinner spinner_serre_user;
    String[] SPINNERLIST = {""};
    int id=R.id.navigation_temperature;
    private TextView mTextMessage,error;
    GraphView graph;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String value_serre=spinner_serre_user.getText().toString();
            switch (item.getItemId()) {

                case R.id.navigation_temperature:
                mTextMessage.setText("Température");
                id=R.id.navigation_temperature;
                  /*  LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                            new DataPoint(0, 1),
                            new DataPoint(1, 5),
                            new DataPoint(2, 3)});
                    graph.removeAllSeries();
                    graph.addSeries(series);*/
                    try {
                        show(value_serre,R.id.navigation_temperature);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return true;
                case R.id.navigation_humidite:
                  mTextMessage.setText("Humidité");
                    id=R.id.navigation_humidite;
                    try {
                        show(value_serre,R.id.navigation_humidite);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return true;

                case R.id.navigation_lumiere:
                mTextMessage.setText("Lumiére ambiante");
                    id=R.id.navigation_lumiere;
                    try {
                        show(value_serre,R.id.navigation_lumiere);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return true;
                case R.id.navigation_co2:
                  mTextMessage.setText("Co2 dans l'air");
                    id=R.id.navigation_co2;
                    try {
                        show(value_serre,R.id.navigation_co2);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return true;
                case R.id.navigation_sol:
                  mTextMessage.setText("Humidité du sol");
                    id=R.id.navigation_sol;
                    try {
                        show(value_serre,R.id.navigation_sol);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapports);
        mTextMessage = (TextView) findViewById(R.id.titlee);
        error=(TextView)findViewById(R.id.err_choix);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        graph = (GraphView)findViewById(R.id.graph);
        mTextMessage.setText("Température");
        /*LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3)});
        graph.removeAllSeries();
        graph.addSeries(series);*/
        spinner_serre_user=(MaterialBetterSpinner)findViewById(R.id.spinner_serre_user);
        SharedPreferences get_inf=getSharedPreferences("KEY_IDENTITY",MODE_PRIVATE);
        final String name=get_inf.getString("KEY_IDENTITY_INFO","");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_items, SPINNERLIST);
        spinner_serre_user.setAdapter(adapter);
        serre_spinner_add(name);
 spinner_serre_user.addTextChangedListener(new TextWatcher() {
     @Override
     public void beforeTextChanged(CharSequence s, int start, int count, int after) {

     }

     @Override
     public void onTextChanged(CharSequence s, int start, int before, int count) {

     }

     @Override
     public void afterTextChanged(Editable s) {
         String item=spinner_serre_user.getText().toString();
         Toast.makeText(getApplicationContext(),item,Toast.LENGTH_LONG).show();
         try {
             show(item,id);
         } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
         }

     }
 });

 }

    void serre_spinner_add(String username){
        class Get_serres extends AsyncTask<String, String, String> {

            String result;
            private Context context;
            private int request = 0;
            //flag 0 means get and 1 means post.(By default it is get.)
            public Get_serres(Context context, int flag) {
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
                    loadIntoSpinner(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            private void loadIntoSpinner(String json) throws JSONException {
                JSONArray jsonArray = new JSONArray(json);
                if (jsonArray.length()==0){
                    Toast.makeText(Rapports.this,"vous n'avez pas de serre \n penser a ajouter serres",Toast.LENGTH_LONG).show();}
                else {
                    String[] serres = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        serres[i] = obj.getString("nom_serre");
                    }
             /*   ArrayAdapter<String>serreAdapter = new ArrayAdapter<String>(Lancer_Cultures.this, R.layout.spinnertxt,serres);
                serre_spinner.setAdapter(serreAdapter);
                serreAdapter.setDropDownViewResource(R.layout.spinner_items);*/

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Rapports.this, R.layout.spinner_items, serres);
                    spinner_serre_user.setAdapter(adapter);
                }}
        }
        new Get_serres(this,0).execute(username);
    }
    private void show(String serre, final int id_courbe) throws UnsupportedEncodingException {
        class Lancer extends AsyncTask<String, String, String> {
            String result;
            private Context context;
            private int request = 0;

            //flag 0 means get and 1 means post.(By default it is get.)
            public Lancer(Context context, int flag) {
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
                try {
                    loadIntoGraph(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            void loadIntoGraph(String json) throws JSONException {
                int j = 0;
                int k= 0;
                int j1 = 0;
                int k1= 0;
                String ch11;
                    JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    ch11=obj.getString("nom_zone");
                    if (ch11.toString().equals("left1")) {j1=j1+1;}else{k1=k1+1;}}
                    DataPoint[] temp=new DataPoint[j1];
                    DataPoint[] humid=new DataPoint[j1];
                    DataPoint[] humid_sol=new DataPoint[j1];
                    DataPoint[] lum=new DataPoint[j1];
                    DataPoint[] co2=new DataPoint[j1];
                DataPoint[] temp_Left=new DataPoint[k1];
                DataPoint[] humid_Left=new DataPoint[k1];
                DataPoint[] humid_sol_Left=new DataPoint[k1];
                DataPoint[] lum_Left=new DataPoint[k1];
                DataPoint[] co2_Left=new DataPoint[k1];
                String ch="";
                    if (jsonArray.length()==0){
                        graph.removeAllSeries();
                        Toast.makeText(Rapports.this,"No value",Toast.LENGTH_LONG).show();}
                    else {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            ch=obj.getString("nom_zone");
                           if (ch.toString().equals("left1")) {

                                temp[j] = new DataPoint(j, obj.getDouble("temperature"));
                                humid[j] = new DataPoint(j, obj.getDouble("humidite"));
                                co2[j] = new DataPoint(j, obj.getDouble("so2"));
                                humid_sol[j] = new DataPoint(j, obj.getDouble("humidite_sol"));
                                lum[j] = new DataPoint(j, obj.getDouble("lumiere"));
                                j=j+1;
                           }else{
                               Toast.makeText(getApplicationContext(),ch,Toast.LENGTH_SHORT).show();
                                temp_Left[k] = new DataPoint(k, obj.getDouble("temperature"));
                                humid_Left[k] = new DataPoint(k, obj.getDouble("humidite"));
                                co2_Left[k] = new DataPoint(k, obj.getDouble("so2"));
                                humid_sol_Left[k] = new DataPoint(k, obj.getDouble("humidite_sol"));
                                lum_Left[k] = new DataPoint(k, obj.getDouble("lumiere"));
                                k=k+1;
                           }
                        }
                        Toast.makeText(getApplicationContext(),ch,Toast.LENGTH_SHORT).show();
                    switch (id_courbe){
                        case R.id.navigation_temperature:add_series(temp,temp_Left);break;
                        case R.id.navigation_humidite:add_series(humid,humid_Left);break;
                        case R.id.navigation_sol:add_series(humid_sol,humid_sol_Left);break;
                        case R.id.navigation_lumiere:add_series(lum,lum_Left);break;
                        case R.id.navigation_co2:add_series(co2,co2_Left);break;
                    }
                }}
            void add_series( DataPoint[] para,DataPoint[] para1){
                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(para);
                series.setTitle("Random Curve 1");
                series.setColor(Color.GREEN);
                series.setDrawDataPoints(true);
                series.setDataPointsRadius(10);
                series.setThickness(8);
                LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(para1);
                series1.setTitle("Random Curve 1");
                series1.setColor(Color.RED);
                series1.setDrawDataPoints(true);
                series1.setDataPointsRadius(10);
                series1.setThickness(8);
                graph.removeAllSeries();
               graph.addSeries(series);
               graph.addSeries(series1);
                graph.getViewport().setScalable(true);

// activate horizontal scrolling
                graph.getViewport().setScrollable(true);

// activate horizontal and vertical zooming and scrolling
                graph.getViewport().setScalableY(true);

// activate vertical scrolling
                graph.getViewport().setScrollableY(true);
            }
        }
        SharedPreferences get_inf=getSharedPreferences("KEY_IDENTITY",MODE_PRIVATE);
        String user=get_inf.getString("KEY_IDENTITY_INFO","");
        new Lancer(this,0).execute("http://192.168.43.94/pfe/mesure.php?nom_serre="+URLEncoder.encode(serre,"UTF-8"));
    }


}
