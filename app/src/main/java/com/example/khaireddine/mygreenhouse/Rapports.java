package com.example.khaireddine.mygreenhouse;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Rapports extends AppCompatActivity {

    private TextView mTextMessage;
    GraphView graph;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_temperature:
                mTextMessage.setText("Température");
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                            new DataPoint(0, 1),
                            new DataPoint(1, 5),
                            new DataPoint(2, 3)});
                    graph.removeAllSeries();
                    graph.addSeries(series);
                    return true;
                case R.id.navigation_humidite:
                  mTextMessage.setText("Humidité");
                    LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(new DataPoint[] {
                            new DataPoint(0, 1),
                            new DataPoint(1, 2),
                            new DataPoint(2, 3)});
                    graph.removeAllSeries();
                    graph.addSeries(series1);
                    return true;

                case R.id.navigation_lumiere:
                mTextMessage.setText("Lumiére ambiante");
                    LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                            new DataPoint(2, 2),
                            new DataPoint(3, 1),
                            new DataPoint(4, 3)});
                    graph.removeAllSeries();
                    graph.addSeries(series2);
                    return true;
                case R.id.navigation_co2:
                  mTextMessage.setText("Co2 dans l'air");
                    LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(new DataPoint[] {
                            new DataPoint(2, 2),
                            new DataPoint(3, 5),
                            new DataPoint(6, 9)});
                    graph.removeAllSeries();
                    graph.addSeries(series3);
                    return true;
                case R.id.navigation_sol:
                  mTextMessage.setText("Humidité du sol");
                    LineGraphSeries<DataPoint> series4 = new LineGraphSeries<>(new DataPoint[] {
                            new DataPoint(2, 2),
                            new DataPoint(3, 1),
                            new DataPoint(11, 3)});
                    graph.removeAllSeries();
                    graph.addSeries(series4);
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
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        graph = (GraphView)findViewById(R.id.graph);
        mTextMessage.setText("Température");
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3)});
        graph.removeAllSeries();
        graph.addSeries(series);

    }

}
