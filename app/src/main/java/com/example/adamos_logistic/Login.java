package com.example.adamos_logistic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button enter, registration;
    TextView check;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        enter = (Button) findViewById(R.id.enter);
        registration = (Button) findViewById(R.id.registr);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        check = (TextView) findViewById(R.id.check);

        View.OnClickListener reg = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                i = new Intent(Login.this, Registration.class);
                startActivity(i);
            }
        };
        registration.setOnClickListener(reg);
        View.OnClickListener login = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check.setText("");
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://192.168.1.121/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                    createPost();
                } catch (Exception e) {
                    //int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
                    //Toast.makeText(getApplicationContext(), permissionStatus.toString(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                    //Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                }
                Intent i;
                i = new Intent(Login.this, ChatActivity.class);
                startActivity(i);

            }
        };
        enter.setOnClickListener(login);

    }

    private void createPost() {
        PostLogin post = new PostLogin(email.getText().toString(), password.getText().toString());

        Call<Post> call = jsonPlaceHolderApi.createPostLogin(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    check.setText("Code: " + response.code() + " " + response.raw());
                    return;
                }

                Post postResponse = response.body();
                String content = "";

                content += "SUCCESS: " + postResponse.getSUCCESS() + "\n";
                content += "USER_ID: " + postResponse.getUSER_ID() + "\n";
                content += "ERROR: " + postResponse.getERROR() + "\n\n";

                check.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                check.setText(t.getMessage());
            }
        });
    }
}
