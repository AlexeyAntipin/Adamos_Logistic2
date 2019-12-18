package com.example.adamos_logistic;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.hardware.camera2.params.RggbChannelVector.RED;

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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.WhoAreYou, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        check = (TextView) findViewById(R.id.check);
        password = (EditText) findViewById(R.id.Password);
        PassRight = (EditText) findViewById(R.id.PassRight);
        registration = (Button) findViewById(R.id.registr) ;
        final LinearLayout linear = (LinearLayout) findViewById(R.id.Linear);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.registr && !password.getText().toString().equals(PassRight.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Andrey soset", Toast.LENGTH_LONG).show();
                    //check.setTextColor(RED);
                    //check.setText("Пароли не совпадают");
                }
                else if (v.getId() == R.id.registr) {
                    try {
                        URL urlRegistration = new URL("http://192.168.137.1/adamos/hs/MAPI/test");
                        HttpURLConnection urlConRegistration = (HttpURLConnection) urlRegistration.openConnection();
                        urlConRegistration.setRequestMethod("POST");
                        urlConRegistration.setRequestProperty("Content-Type", "application/json; utf-8");
                        urlConRegistration.setRequestProperty("Accept","application/json");
                        urlConRegistration.setDoOutput(true);
                        urlConRegistration.setDoInput(true);
                        urlConRegistration.connect();

                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("e-mail", email.getText());
                        jsonParam.put("password", password.getText().hashCode());
                        jsonParam.put("surname", SurName.getText());
                        jsonParam.put("second name", SecondName.getText());

                        DataOutputStream os = new DataOutputStream(urlConRegistration.getOutputStream());
                        os.writeBytes(jsonParam.toString());

                        os.flush();
                        os.close();

                        Log.i("STATUS", String.valueOf(urlConRegistration.getResponseCode()));
                        Log.i("MSG" , urlConRegistration.getResponseMessage());

                        urlConRegistration.disconnect();
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                }
                //Toast.makeText(getApplicationContext(), pidor, Toast.LENGTH_LONG).show();
            }
        };
        registration.setOnClickListener(listener);
    }
}
