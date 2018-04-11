package com.example.khaireddine.mygreenhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

public class Cycle_Vie extends AppCompatActivity{
EditText FieldTemperature,FieldHumidite,Fieldco2,FieldTemp_eau,FieldHumidite_sol,FieldLumiere;
TextView label_temp,label_humid,label_co2,label_temp_eau,label_humid_sol,label_lumiere;
TextView nombre_periode;
    int i,n,indice;String message,ch_n;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle__vie);
        Button next=(Button)findViewById(R.id.suivant);
        FieldTemperature=(EditText)findViewById(R.id.temperature);
        FieldHumidite=(EditText)findViewById(R.id.humidite);
        Fieldco2=(EditText)findViewById(R.id.co2);
        FieldTemp_eau=(EditText)findViewById(R.id.temp_eau);
        FieldHumidite_sol=(EditText)findViewById(R.id.hum_sol);
        FieldLumiere=(EditText)findViewById(R.id.lumiere);

        label_temp=(TextView) findViewById(R.id.label_temperature);
        label_temp_eau=(TextView) findViewById(R.id.label_temp_eau);
        label_co2=(TextView) findViewById(R.id.label_co2);
        label_humid=(TextView) findViewById(R.id.label_humidite);
        label_humid_sol=(TextView) findViewById(R.id.label_hum_sol);
        label_lumiere=(TextView) findViewById(R.id.label_lumiere);
        setfocus(FieldTemperature,label_temp);
        setfocus(FieldTemp_eau,label_temp_eau);
        setfocus(FieldHumidite,label_humid);
        setfocus(FieldHumidite_sol,label_humid_sol);
        setfocus(FieldLumiere,label_lumiere);
        setfocus(Fieldco2,label_co2);

        nombre_periode=(TextView)findViewById(R.id.label_activity) ;
        Intent intent = getIntent();
       message = intent.getStringExtra("KEY");
       ch_n=intent.getStringExtra("KEY1");
        i=Integer.parseInt(message);
        n=Integer.parseInt(ch_n);
        indice=n-i+1;
        if (i>=1) {nombre_periode.setText("Période "+String.valueOf(indice));}

    }
    public void next(View view) {


            if (i>1)

            {    i=i-1;
                message=String.valueOf(i);
                Intent intent = new Intent(this, Cycle_Vie.class);
                intent.putExtra("KEY", message);
                intent.putExtra("KEY1",ch_n);
                startActivity(intent);
            }
            else {Intent intent_inscription = new Intent(this, Mes_plantes.class);
                startActivity(intent_inscription);}
        }
    public void setfocus(final EditText field, final TextView label) {
        final String hint = label.getText().toString();
        field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    label.setVisibility(View.VISIBLE);
                    field.setHint("");
                } else {
                    label.setVisibility(View.INVISIBLE);
                    field.setHint(hint);
                }

            }
        });

    }
    }

