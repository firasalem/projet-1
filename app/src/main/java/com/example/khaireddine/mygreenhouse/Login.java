package com.example.khaireddine.mygreenhouse;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static android.view.View.VISIBLE;

/**
 * A login screen that offers login via email/password.
 */
public class Login extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    String mail,mdp;
    private static final int REQUEST_READ_CONTACTS = 0;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    TextView erreur;
    SharedPreferences.Editor usser_editor;
    private View mProgressView;
    private View mLoginFormView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginn);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
       populateAutoComplete();
        erreur=(TextView)findViewById(R.id.erreur);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    auhentification();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.se_connecter);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                auhentification();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@")&&email.contains(".com");
    }


    /**
     * Shows the progress UI and hides the login form.
     */


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(Login.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    public void To_Inscription(View view) {
        Intent intent_Inscription= new Intent(getApplicationContext(), Inscrit_info_personnel.class);
        startActivity(intent_Inscription);

    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

   //TODO:methode d'Authentification
   private void auhentification() {
       class CreateAccount extends AsyncTask<String, String, String> {
           String result;
           private Context context;
           private int request = 0;
           //flag 0 means get and 1 means post.(By default it is get.)
           public CreateAccount(Context context, int flag) {
               this.context = context;
               this.request = flag;
           }
           @Override
           protected String doInBackground(String[]arg0) {
               try{
                   String email=(String)arg0[0];
                   String password=(String)arg0[1];

                   String link="http://192.168.43.94/pfe/Verifier_connexion.php?email="+email+"&password="+password;

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
       /*   Toast.makeText(this.context,result,Toast.LENGTH_SHORT).show(); */
               // Reset errors.
               mEmailView.setError(null);
               mPasswordView.setError(null);
               boolean verif=true;
               // Store values at the time of the login attempt.
               String email = mEmailView.getText().toString();
               String password = mPasswordView.getText().toString();

               // Check for a valid password, if the user entered one.


               // Check for a valid email address.
               if (TextUtils.isEmpty(email)) {mEmailView.setError("Champ vide");verif=false;}
               else if (!isEmailValid(email)) {mEmailView.setError("Email invalide");verif=false;}
               if (TextUtils.isEmpty(password)) {mPasswordView.setError("Champ vide");verif=false;}
               else if (verif)
               {
                   if (result.equals("ok"))
                   {SharedPreferences.Editor editor=getSharedPreferences("key_info",MODE_PRIVATE).edit();
                       editor.putString("MY_KEY",mEmailView.getText().toString());
                       editor.commit();
                       usser_editor=getSharedPreferences("KEY_IDENTITY",MODE_PRIVATE).edit();
                       usser_editor.putString("KEY_IDENTITY_INFO",mEmailView.getText().toString());
                       usser_editor.commit();
                       Intent intent_Accueil = new Intent(getApplicationContext(), Accueil.class);
                       intent_Accueil.putExtra("KEY_MAIL",email);
                       startActivity(intent_Accueil);


                   }
                   else if (result.equals("erreur"))
                   {
                       erreur.setVisibility(VISIBLE);
                       erreur.setText("mot de passe ou identifiant incorrect");
                   }

                   else
                   { Toast.makeText(getApplicationContext(),"Probléme de Connexion",Toast.LENGTH_SHORT).show();
                   }

               }}}

       mail=mEmailView.getText().toString();
       mdp=mPasswordView.getText().toString();
       new CreateAccount(this,0).execute(mail,mdp);
   }
        }





