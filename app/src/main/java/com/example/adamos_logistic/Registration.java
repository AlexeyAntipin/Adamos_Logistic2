package com.example.adamos_logistic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;

public class Registration extends AppCompatActivity {

    EditText name, SecondName, SurName, password, email, PassRight;
    Button registration;
    Spinner spinner;
    TextView check;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.WhoAreYou, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        check = (TextView) findViewById(R.id.check);
        password = (EditText) findViewById(R.id.Password);
        PassRight = (EditText) findViewById(R.id.PassRight);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.registration) {
                    //check.setTextColor(RED);
                    check.setText("Пароли не совпадают");
                }
            }
        };
        registration.setOnClickListener(listener);
    }
}
