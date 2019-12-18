package com.example.adamos_logistic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button enter, registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        enter = (Button) findViewById(R.id.enter);
        registration = (Button) findViewById(R.id.registr);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        View.OnClickListener reg = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(Login.this, Registration.class);
                startActivity(i);
            }
        };
        View.OnClickListener login = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                switch (v.getId()) {
                    case R.id.enter:
                        i = new Intent(Login.this, Registration.class);
                        startActivity(i);
                        break;
                    default:
                        return;
                }
            }

        };
        registration.setOnClickListener(reg);
        enter.setOnClickListener(login);
    }
}
