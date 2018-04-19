package com.example.khaireddine.mygreenhouse;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Accueil extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String[] colors_array = {"Serre 1", "Serre 2", "Serre 3", "Serre 4","Serre 5"};
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        View headerView = navigationView.getHeaderView(0);
        username = (TextView) headerView.findViewById(R.id.user);
        username.setText("Firas&Khairi");

        List<EventDay> events = new ArrayList<>();
        final Calendar calendar = Calendar.getInstance();
        calendar.set(2018,3,22);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2018,3,11);
        events.add(new EventDay(calendar, R.drawable.star));
        events.add(new EventDay(calendar1, R.drawable.star));
        CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.showCurrentMonthPage();
        calendarView.setEvents(events);
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                if (clickedDayCalendar.equals(calendar))
                {Dialog alert=onCreateDialog();
                    alert.show();}
            }
        });


    }

    public Dialog onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Accueil.this);
        builder.setTitle("Evennement de ce jour");
        builder.setIcon(R.drawable.calendrier);
        builder.setItems(colors_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                });
        return builder.create();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_rapports)
        {
            Intent intent_rapports = new Intent(this, Rapports.class);
            startActivity(intent_rapports);
        }
        else if (id == R.id.nav_mes_serre)
        {
            Intent intent_plantes = new Intent(this, Mes_Seres.class);
            startActivity(intent_plantes);
        }
        else if (id == R.id.nav_types)
        {
            Intent intent_rapports = new Intent(this, Mes_plantes.class);
            startActivity(intent_rapports);

        }
        else if (id == R.id.nav_ajout_serre)
        {
            Intent intent_rapports = new Intent(this, Ajouter_Serre.class);
            startActivity(intent_rapports);
        }
        else if (id == R.id.nav_ajout_plante)
        {
            Intent intent_rapports = new Intent(this, Ajouter_plantes.class);
            startActivity(intent_rapports);
        }
        else if (id == R.id.nav_lancer_culture)
        {
            Intent intent_lancer = new Intent(this, Lancer_Cultures.class);
            startActivity(intent_lancer);
        }
        else if (id == R.id.nav_gerer)
        {
            Intent intent_gerer = new Intent(this, Manupiler.class);
            startActivity(intent_gerer);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
