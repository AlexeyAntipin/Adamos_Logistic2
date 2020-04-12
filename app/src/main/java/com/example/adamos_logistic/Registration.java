package com.example.adamos_logistic;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adamos_logistic.Posts.AddUser;
import com.example.adamos_logistic.Posts.JsonPlaceHolderApi;
import com.example.adamos_logistic.Posts.Post;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Registration extends AppCompatActivity {

    EditText name, SecondName, SurName, password, email, PassRight;
    Button registration;
    Spinner spinner;
    TextView check;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.WhoAreYou, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        email = (EditText) findViewById(R.id.EMAIL);
        name = (EditText) findViewById(R.id.NAME);
        check = (TextView) findViewById(R.id.check);
        password = (EditText) findViewById(R.id.PASSWORD);
        PassRight = (EditText) findViewById(R.id.PassRight);
        registration = (Button) findViewById(R.id.registr) ;



        final LinearLayout linear = (LinearLayout) findViewById(R.id.Linear);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.registr && !password.getText().toString().equals(PassRight.getText().toString())) {
                    //Toast.makeText(getApplicationContext(), "Пароли не совпадают", Toast.LENGTH_LONG).show();
                    check.setText("Пароли не совпадают");
                }
                else if (v.getId() == R.id.registr && password.getText().toString().equals(PassRight.getText().toString())) {
                    check.setText("");
                    try {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(JsonPlaceHolderApi.HOST)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                        Call<AddUser> call2 = jsonPlaceHolderApi.addUser(
                                email.getText().toString(),
                                password.getText().toString(),
                                name.getText().toString());

                        call2.enqueue(new Callback<AddUser>() {
                            @Override
                            public void onResponse(Call<AddUser> call2, Response<AddUser> response) {

                                Log.d("MyLog", response.toString());

                            }

                            @Override
                            public void onFailure(Call<AddUser> call2, Throwable t) {

                                Log.d("MyLog", t.toString());

                            }
                        });

                    } catch (Exception e) {

                        e.printStackTrace();
                        Log.d("MyLog", "ОШИБКА РЕГИСТРАЦИИ");

                    }
                    Intent i = new Intent(Registration.this, MainMenuActivity.class);
                    startActivity(i);
                }
            }
        };
        registration.setOnClickListener(listener);
    }
    private void createPost() {
        Post post = new Post(SurName.getText().toString(),
                name.getText().toString(),
                SecondName.getText().toString(),
                email.getText().toString(),
                password.getText().toString(), true);

        Call<Post> call = jsonPlaceHolderApi.createPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    check.setText("Code: " + response.code() + " " + response.raw());
                    return;
                }

                Post postResponse = response.body();
                String content = "";
                /*content += "NAME: " + postResponse.getNAME() + "\n";
                content += "SURNAME: " + postResponse.getSURNAME() + "\n";
                content += "SECONDNAME: " + postResponse.getSECONDNAME() + "\n";
                content += "PASSWORD: " + postResponse.getPASSWORD() + "\n";
                content += "EMAIL: " + postResponse.getEMAIL() + "\n\n";
                 */

                content += "SUCCESS: " + postResponse.getSUCCESS() + "\n";
                content += "USER_ID: " + postResponse.getUSER_ID() + "\n";
                content += "ORDER_ID: " + postResponse.getORDER_ID() + "\n";
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
