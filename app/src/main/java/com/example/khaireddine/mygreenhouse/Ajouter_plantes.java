package com.example.khaireddine.mygreenhouse;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.Locale;

public class Ajouter_plantes extends AppCompatActivity  {
    EditText FieldNom,FieldFamille,FieldPeriode;
    Integer nbr_jour_cycle,jour_periode,total_jours=0;;
    String nom_plante,famille;
NumberPicker Picker_nbr_jour_cycle,picker_nbr_jour_periode;
Button button_ajouter, button_ajouter_periode;
    int numero_periode=1;
    int temp,humid,humid_sol,lum,co2;
    JSONArray jsonArray = new JSONArray();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_plantes);
        FieldNom=(EditText)findViewById(R.id.nom_plante);
        FieldFamille=(EditText)findViewById(R.id.famille);
        Picker_nbr_jour_cycle=(NumberPicker) findViewById(R.id.cycle);
        button_ajouter=(Button)findViewById(R.id.add);
        button_ajouter_periode=(Button)findViewById(R.id.ajout_periode);
        button_ajouter_periode.setText("Ajouter periode ("+String.valueOf(numero_periode)+")");
        Picker_nbr_jour_cycle.setMinValue(20);
        Picker_nbr_jour_cycle.setMaxValue(120);
        NumberPicker.Formatter formatter=new NumberPicker.Formatter() {
            @Override
            public String format(int i) {return NumberFormat.getIntegerInstance().format((Integer)i).toString()+" jours";}};
        Picker_nbr_jour_cycle.setFormatter(formatter);
    }
//TODO:////////////////////////////////Methode Ajouter periode/////////////////////////////////////////////////////////
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void ajouter_periode(View view) {
        LayoutInflater inflater =getLayoutInflater();
        View alertLayout=inflater.inflate(R.layout.alert_layout,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(Ajouter_plantes.this);
        builder.setTitle("Condition periode "+numero_periode);
        builder.setIcon(R.drawable.plantes);
        builder.setView(alertLayout);
       final  TextView value_seek_temperature=(TextView) alertLayout.findViewById(R.id.val_seek_temp);
        final  TextView value_seek_humidit=(TextView) alertLayout.findViewById(R.id.val_seek_humid);
        final  TextView value_seek_humid_sol=(TextView) alertLayout.findViewById(R.id.val_seek_humid_sol);
        final  TextView value_seek_lumiere=(TextView) alertLayout.findViewById(R.id.val_seek_lum);
        final  TextView value_seek_co2=(TextView) alertLayout.findViewById(R.id.val_seek_co2);
        final SeekBar seek_temperature=(SeekBar) alertLayout.findViewById(R.id.temp_seek);
        final SeekBar seek_humidit=(SeekBar) alertLayout.findViewById(R.id.humid_seek);
        final SeekBar seek_humid_sol=(SeekBar) alertLayout.findViewById(R.id.humid_sol_seek);
        final SeekBar seek_lumiere=(SeekBar) alertLayout.findViewById(R.id.lum_seek);
        final SeekBar seek_co2=(SeekBar) alertLayout.findViewById(R.id.co2_seek);
        picker_nbr_jour_periode=(NumberPicker) alertLayout.findViewById(R.id.picker_nbr_jours_periode);
        seek_temperature.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value_seek_temperature.setText(String.valueOf(i));
                temp=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seek_humidit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value_seek_humidit.setText(String.valueOf(i));
                humid=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seek_humid_sol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value_seek_humid_sol.setText(String.valueOf(i));
                humid_sol=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seek_lumiere.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value_seek_lumiere.setText(String.valueOf(i));
                lum=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seek_co2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                value_seek_co2.setText(String.valueOf(i));
                co2=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        NumberPicker.Formatter formatter=new NumberPicker.Formatter() {
            @Override
            public String format(int i) {return NumberFormat.getIntegerInstance().format((Integer)i).toString()+" jours";}};
       builder.setNegativeButton("Annuler",null).setPositiveButton("ajouter", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
                  jour_periode=picker_nbr_jour_periode.getValue();
                   total_jours=total_jours+jour_periode;
                   JSONObject obj = new JSONObject();
                   jour_periode=picker_nbr_jour_periode.getValue();
                   try {
                       obj.put("jours_periode",jour_periode);
                       obj.put("temperature", temp);
                       obj.put("humidite", humid);
                       obj.put("humidite_sol", humid_sol);
                       obj.put("lumiere", lum);
                       obj.put("co2", co2);
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
                   jsonArray.put(obj);
               button_ajouter.setVisibility(View.VISIBLE);
               numero_periode=numero_periode+1;;
               button_ajouter_periode.setText("Ajouter periode ("+String.valueOf(numero_periode)+")");
               }
           });
       AlertDialog dialog=builder.create();
       dialog.show();

    }
//TODO://////////////////////////////// Buttoon ajouter : control input and call web service /////////////////////////////////////////////////////////
    public void ajouter(View view) throws JSONException {

        nbr_jour_cycle=Picker_nbr_jour_cycle.getValue();
        if (FieldNom.getText().toString().isEmpty()||FieldNom.getText().toString().equals("0"))
        {FieldNom.setError("champs vide");}
        else if (jsonArray.length()==0)
        {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Erreur")
        .setMessage("Vous devez ajouter les condition de chaque periode du cycle de vie de la plante \nSi la plante a les meme condition pendant tous le cycle ajouter une suele periode dont le nombre de jours est le cycle de vie")
        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
             public void onClick(DialogInterface dialog, int which) {
             }
        }).show();
        }
        else if (total_jours!=nbr_jour_cycle)
        {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Erreur")
            .setMessage("Le total nombre de jours des periode ne correspend pas au cycle de vie.\nSi la plante a les meme condition pendant tous le cycle ajouter une suele periode dont le nombre de jours est le cycle de vie ")
            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).show();
        jsonArray=new JSONArray();
        total_jours=0;
        numero_periode=1;
            button_ajouter_periode.setText("Ajouter periode ("+String.valueOf(numero_periode)+")");
        }
        else {add_plant(jsonArray);}
}
//TODO:////////////////////////////////web service( send json array)/////////////////////////////////////////////////////////
    private void add_plant(final JSONArray data) {
        class Ajouter_plante extends AsyncTask<String, String, String> {
            String result;
            private Context context;
            private int request = 0;
            //flag 0 means get and 1 means post.(By default it is get.)
            public Ajouter_plante(Context context, int flag) {
                this.context = context;
                this.request = flag;
            }
            @Override
            protected String doInBackground(String[]arg0) {
               try{nbr_jour_cycle=Picker_nbr_jour_cycle.getValue();
                   nom_plante=FieldNom.getText().toString();
                   famille=FieldFamille.getText().toString();
                   SharedPreferences get_inf=getSharedPreferences("KEY_IDENTITY",MODE_PRIVATE);
                   String user=get_inf.getString("KEY_IDENTITY_INFO","");

                    String link="http://192.168.43.94/pfe/inserer_plante.php?nom_plante="+URLEncoder.encode(nom_plante,"UTF-8")+"&famille="+URLEncoder.encode(famille,"UTF-8")+"&environnement="+URLEncoder.encode(String.valueOf(data),"UTF-8")+"&cycle="+nbr_jour_cycle+"&user="+URLEncoder.encode(user,"UTF-8");
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
             //Toast.makeText(getApplicationContext(),get_nom,Toast.LENGTH_LONG).show();
                if (result.equals("Ce plante existe deja")){ Toast.makeText(getApplicationContext(),"Ce plante existe deja",Toast.LENGTH_SHORT).show();}
                else if (result.equals("ok")){
                    Intent intent_ajout_mes_plante=new Intent(getApplicationContext(),Mes_plantes.class);
                startActivity(intent_ajout_mes_plante);
                }
                else {
                    Toast.makeText(getApplicationContext(),"probléme de connexion",Toast.LENGTH_SHORT).show();}
                }

                }
        new Ajouter_plante(this,0).execute(jsonArray.toString());
    }
}
