package com.example.khaireddine.mygreenhouse;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Accueil extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String[] colors_array = {"Serre 1", "Serre 2", "Serre 3", "Serre 4","Serre 5"};
    TextView username;
    String msg_name,msg_name1;
static int parent = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        //TODO:Setting up the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //TODO:Setting up the DrawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //TODO:Setting up the NavigationView
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
        View headerView = navigationView.getHeaderView(0);
        //TODO:Setting up user lastname and firstname in the  NavigationView
        username = (TextView) headerView.findViewById(R.id.user);
        //TODO: get preferences value of login activity

        //TODO:get preferences value of inscription activity;
   Intent get_msg=getIntent();
      msg_name=get_msg.getStringExtra("KEY_1_name")+get_msg.getStringExtra("KEY_last_name");
        msg_name1=get_msg.getStringExtra("KEY_MAIL")+"null";
     if (!(msg_name.equals("nullnull")))
     {    SharedPreferences get_inscrit=getSharedPreferences("KEY_inscription_share",MODE_PRIVATE);
         msg_name=get_inscrit.getString("KEY_inscription","");
         username.setText(msg_name);
         SharedPreferences.Editor editor=getSharedPreferences("KEY_user",MODE_PRIVATE).edit();
         editor.putString("KEY_user_inf",msg_name);
         editor.commit();
     }
     else if(!(msg_name1.equals("nullnull"))){
         SharedPreferences get_connexion=getSharedPreferences("key_info",MODE_PRIVATE);
         msg_name1=get_connexion.getString("MY_KEY","");
         Add_user_info(msg_name1);
        // username.setText(msg_name1);
         SharedPreferences.Editor editor=getSharedPreferences("KEY_user",MODE_PRIVATE).edit();
         editor.putString("KEY_user_inf",msg_name1);
         editor.commit();
     }
     else{ SharedPreferences get_connexion=getSharedPreferences("KEY_user",MODE_PRIVATE);
         username.setText(get_connexion.getString("KEY_user_inf",""));
     }
        //TODO:Setting up the Calendar view and add events
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
    //TODO:Setting up an alert dialog if user click on an event in the calendar
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_accueil,menu);
        return true;
    }
    @Override
    public void onBackPressed() {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            System.exit(1);
                        }
                    }).setNegativeButton("No", null).show();
        }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout)
        {Intent lgout=new Intent(this,Login.class);
            startActivity(lgout);
            return true;
        }
        if ((id==R.id.action_notification))
        {
            Intent intent_notification=new Intent(this,Notification.class);
            startActivity(intent_notification);
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
        {   Intent intent_Serres = new Intent(this, Mes_Seres.class);
            SharedPreferences get_inf=getSharedPreferences("KEY_IDENTITY",MODE_PRIVATE);
            intent_Serres.putExtra("KEY_SERRE",get_inf.getString("KEY_IDENTITY_INFO",""));
            startActivity(intent_Serres);
        }
        else if (id == R.id.nav_types)
        {
            Intent intent_rapports = new Intent(this, Mes_plantes.class);
            startActivity(intent_rapports);

        }
        else if (id == R.id.nav_ajout_serre)
        {  Intent intent_ajouter_serre = new Intent(this, Ajouter_Serre.class);
            SharedPreferences get_inf=getSharedPreferences("KEY_IDENTITY",MODE_PRIVATE);
            intent_ajouter_serre.putExtra("KEY_SERRE",get_inf.getString("KEY_IDENTITY_INFO",""));
            startActivity(intent_ajouter_serre);
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
    //TODO:Getting data from database
    private void Add_user_info(String chaine) {
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
                String email=(String)arg0[0];
                URL url = new URL("http://192.168.43.94/pfe/selectusername.php?email="+email);

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
                loadIntoTextView(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    new Get_info(this,0).execute(chaine);
}
    private void loadIntoTextView(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            String info= obj.getString("firstname") +" "+obj.getString("lastname");
            username.setText(info);
            SharedPreferences.Editor editor=getSharedPreferences("KEY_user",MODE_PRIVATE).edit();
            editor.putString("KEY_user_inf",info);
            editor.commit();
        }
    }


}



