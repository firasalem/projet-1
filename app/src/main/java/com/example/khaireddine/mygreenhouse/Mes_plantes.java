package com.example.khaireddine.mygreenhouse;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;

public class Mes_plantes extends AppCompatActivity {
    private final LinkedList<String> mWordList = new LinkedList<>();
    private final LinkedList<String> famille = new LinkedList<>();
    private final LinkedList<String> cycle = new LinkedList<>();
    private int mCount = 0;
    private RecyclerView mRecyclerView;
    private adapter_plante mAdapter;
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        for (int i = 0; i < 20; i++) {

            mWordList.addLast("nom plante " + mCount++);
            famille.addLast("famille " + mCount++);
            cycle.addLast("cycle " + mCount++);
            Log.d("WordList", mWordList.getLast());
        }
        // Get a handle to the RecyclerView.
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
// Create an adapter and supply the data to be displayed.
        mAdapter = new adapter_plante(this, mWordList,famille,cycle);
// Connect the adapter with the RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
// Give the RecyclerView a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
