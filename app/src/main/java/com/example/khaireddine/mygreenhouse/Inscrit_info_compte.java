package com.example.khaireddine.mygreenhouse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Inscrit_info_compte extends AppCompatActivity {
    private EditText FieldEMail;
    private EditText FieldMdp;
    private EditText FieldMdp2;
    private TextView label_mail, label_mdp, label_mdp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscrit_info_compte);
        FieldEMail = (EditText) findViewById(R.id.mail_inscrit);
        FieldMdp = (EditText) findViewById(R.id.password_inscrit);
        FieldMdp2 = (EditText) findViewById(R.id.password2_inscrit);
        label_mail = (TextView) findViewById(R.id.label_mail_inscrit);
        label_mdp = (TextView) findViewById(R.id.label_password_inscrit);
        label_mdp2 = (TextView) findViewById(R.id.label_password_inscrit);
        setfocus(FieldEMail,label_mail);
        setfocus(FieldMdp,label_mdp);
        setfocus(FieldMdp2,label_mdp2);
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
    public void To_Serre_info(View view) {
        Intent intent_inscription = new Intent(this, Inscrit_info_serre.class);
        startActivity(intent_inscription);
    }
}
