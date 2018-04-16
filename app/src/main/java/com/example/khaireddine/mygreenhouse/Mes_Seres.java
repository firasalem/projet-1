package com.example.khaireddine.mygreenhouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Mes_Seres extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        setContentView(R.layout.activity_mes__serres);
        final GridView gridView = (GridView) findViewById(R.id.gridView1);
        FloatingActionButton add=(FloatingActionButton)findViewById(R.id.fab);
        List<String> MOBILE_OS = new ArrayList<>();

        MOBILE_OS.add("Tomate");
        MOBILE_OS.add("Tomate");
        MOBILE_OS.add("Tomate");
        MOBILE_OS.add("Tomate");
        MOBILE_OS.add("Tomate");
        MOBILE_OS.add("Tomate");
        MOBILE_OS.add("Tomate");

        gridView.setAdapter(new serreadapter(this, MOBILE_OS));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent intent = new Intent(Mes_Seres.this, information_serrre.class);
                startActivity(intent);


            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Mes_Seres.this, Ajouter_Serre.class);
                startActivity(intent);
            }
        });
            }
}
