package com.example.khaireddine.mygreenhouse;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.series.DataPoint;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Lancer_Cultures extends AppCompatActivity {
    String[] SPINNERLIST = {""};
    private String mSpinnerPlante = "";
    Spinner plant_spinner;
    Spinner serre_spinner;
    TextView erreur_serre, erreur_plante;
    MaterialBetterSpinner spinner_serre, spinner_plante;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lancer__cultures);
        erreur_plante = (TextView) findViewById(R.id.error_plante);
        erreur_serre = (TextView) findViewById(R.id.error_serre);
        spinner_serre = (MaterialBetterSpinner) findViewById(R.id.spinner1);
        spinner_plante = (MaterialBetterSpinner) findViewById(R.id.spinner2);
        SharedPreferences get_inf = getSharedPreferences("KEY_IDENTITY", MODE_PRIVATE);
        final String name = get_inf.getString("KEY_IDENTITY_INFO", "");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_items, SPINNERLIST);

        spinner_serre.setAdapter(adapter);
        spinner_plante.setAdapter(adapter);
        serre_spinner_add(name);
        plante_spinner_add(name);
        spinner_plante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erreur_plante.setVisibility(View.INVISIBLE);
            }
        });
        spinner_serre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erreur_serre.setVisibility(View.INVISIBLE);
            }
        });
    }

    //TODO:///////////////////////////////////Add data to spinner SERRE//////////////////
    void serre_spinner_add(String username) {
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
            protected String doInBackground(String[] arg0) {
                try {
                    String username = (String) arg0[0];
                    URL url = new URL("http://192.168.43.94/pfe/selectserre.php?email=" + username);

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
                if (jsonArray.length() == 0) {
                    Toast.makeText(Lancer_Cultures.this, "vous n'avez pas de serre \n penser a ajouter serres", Toast.LENGTH_LONG).show();
                } else {
                    String[] serres = new String[jsonArray.length()];
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        serres[i] = obj.getString("nom_serre");
                    }
             /*   ArrayAdapter<String>serreAdapter = new ArrayAdapter<String>(Lancer_Cultures.this, R.layout.spinnertxt,serres);
                serre_spinner.setAdapter(serreAdapter);
                serreAdapter.setDropDownViewResource(R.layout.spinner_items);*/

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Lancer_Cultures.this, R.layout.spinner_items, serres);
                    spinner_serre.setAdapter(adapter);
                }
            }
        }
        new Get_serres(this, 0).execute(username);
    }

    //TODO:///////////////////////////////////Add data to spinner PLANATE//////////////////
    void plante_spinner_add(String chaine) {
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
            protected String doInBackground(String[] arg0) {
                try {
                    String username = (String) arg0[0];
                    URL url = new URL("http://192.168.43.94/pfe/selectnomplante.php?username=" + username);

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
                    loadIntoSpinner_plante(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            private void loadIntoSpinner_plante(String json) throws JSONException {
                JSONArray jsonArray = new JSONArray(json);
                List<String> plantes = new ArrayList<>();
                if (jsonArray.length() == 0) {
                    Toast.makeText(Lancer_Cultures.this, "vous n'avez pas de plante", Toast.LENGTH_LONG).show();
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        plantes.add(obj.getString("nom"));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Lancer_Cultures.this, R.layout.spinner_items, plantes);
                    spinner_plante.setAdapter(adapter);

                }
            }
        }
        new Get_info(this, 0).execute(chaine);
    }

    public void To_add_type(View view) {
        Intent to_add_type = new Intent(this, Ajouter_plantes.class);
        startActivity(to_add_type);
    }

    public void lancer(View view) {
        boolean verif = true;
        String value_serre = spinner_serre.getText().toString();
        String value_plante = spinner_plante.getText().toString();
        if ((value_plante.equals(""))) {
            erreur_plante.setVisibility(View.VISIBLE);
            verif = false;
        }
        if (value_serre.equals((""))) {
            erreur_serre.setVisibility(View.VISIBLE);
            verif = false;
        }
        if (verif) {

            try {
                lancer_culture(value_serre, value_plante);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO:///////////////////////////////////Add culture to data base//////////////////
    private void lancer_culture(String serre, String plante) throws UnsupportedEncodingException {
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
            protected String doInBackground(String[] arg0) {
                try {
                    String link = (String) arg0[0];
                    URL url = new URL(link);

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
             if( result.equals("pl")){Toast.makeText(getApplicationContext(),"Serre deja planter",Toast.LENGTH_SHORT).show();}
                else if (result.equals("erreur")) {Toast.makeText(getApplicationContext(),"probléme de connexion",Toast.LENGTH_SHORT).show();}
                else{ try {
                     loadIntoGraph(result);
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
                 Intent culture_accueil=new Intent(getApplicationContext(),Accueil.class);
                startActivity(culture_accueil);
             }
            }
        }
        SharedPreferences get_inf = getSharedPreferences("KEY_IDENTITY", MODE_PRIVATE);
        String user = get_inf.getString("KEY_IDENTITY_INFO", "");
        new Lancer(this, 0).execute("http://192.168.43.94/pfe/lancerculture.php?nom=" + URLEncoder.encode(plante, "UTF-8") + "&nom_serre=" + URLEncoder.encode(serre, "UTF-8") + "&username=" + user);
    }
    void loadIntoGraph(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        String[] dates=new String[2];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
               dates[0]=obj.getString("date debut");
                dates[1]=obj.getString("date fin");
            }
        try {
            addEvent(dates[0],dates[1]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void addEvent(String datedebut,String datefin) throws ParseException {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, 1);
        }
else{
        ContentResolver cr = getContentResolver();
        ContentValues contentValues = new ContentValues();

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2018, 04, 12);
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
      beginTime.setTime(sdf.parse(datedebut));
        Calendar endTime = Calendar.getInstance();
        endTime.set(2018,04,25);
        endTime.setTime(sdf.parse(datefin));
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, beginTime.getTimeInMillis());
        values.put(CalendarContract.Events.DTEND, endTime.getTimeInMillis());
            String value_serre = spinner_serre.getText().toString();
            String value_plante = spinner_plante.getText().toString();
        values.put(CalendarContract.Events.TITLE, "Cycle de vie(Serre :"+value_serre+"| plante:" +value_plante+")");
        values.put(CalendarContract.Events.DESCRIPTION, "Cycle de vie du plante ");
        values.put(CalendarContract.Events.CALENDAR_ID, 2);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "Europe/London");
        values.put(CalendarContract.Events.EVENT_LOCATION, value_serre);
            values.put(CalendarContract.Events.EVENT_COLOR,"0xFF4CAF50");
        cr.insert(CalendarContract.Events.CONTENT_URI, values);}
    }
}

