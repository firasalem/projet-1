package com.example.khaireddine.mygreenhouse;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
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
                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                            new DataPoint(0, 1),
                            new DataPoint(1, 5),
                            new DataPoint(2, 3)});
                    graph.removeAllSeries();
                    graph.addSeries(series);
                    return true;
                case R.id.navigation_humidite:

                    LineGraphSeries<DataPoint> series1 = new LineGraphSeries<>(new DataPoint[] {
                            new DataPoint(0, 1),
                            new DataPoint(1, 2),
                            new DataPoint(2, 3)});
                    graph.removeAllSeries();
                    graph.addSeries(series1);
                    return true;

                case R.id.navigation_lumiere:

                    LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                            new DataPoint(2, 2),
                            new DataPoint(3, 1),
                            new DataPoint(4, 3)});
                    graph.removeAllSeries();
                    graph.addSeries(series2);
                    return true;
                case R.id.navigation_co2:

                    LineGraphSeries<DataPoint> series3 = new LineGraphSeries<>(new DataPoint[] {
                            new DataPoint(2, 2),
                            new DataPoint(3, 5),
                            new DataPoint(6, 9)});
                    graph.removeAllSeries();
                    graph.addSeries(series3);
                    return true;
                case R.id.navigation_sol:

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
        setContentView(R.layout.activity_main2);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        graph = (GraphView)findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3)});
        graph.removeAllSeries();
        graph.addSeries(series);

    }

}
