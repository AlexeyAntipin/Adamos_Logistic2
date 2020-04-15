package com.example.adamos_logistic;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adamos_logistic.Posts.JsonPlaceHolderApi;
import com.example.adamos_logistic.Posts.Post;
import com.example.adamos_logistic.Posts.PostLogin;
import com.example.adamos_logistic.Posts.PostLoginData;
import com.example.adamos_logistic.Posts.ResponseLogin;
import com.example.adamos_logistic.Posts.StoringParams;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button enter, registration;
    Button debugBtn;
    TextView check;
    StoringParams params;
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

        debugBtn = findViewById(R.id.debugButton);

        params = new StoringParams();

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
                //check.setText("");
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(JsonPlaceHolderApi.HOST)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                    PostLoginData login2 = new PostLoginData(email.getText().toString(), password.getText().toString());

                    Call<ResponseLogin> call2 = jsonPlaceHolderApi.checkUser(login2);

                    call2.enqueue(new Callback<ResponseLogin>() {
                        @Override
                        public void onResponse(Call<ResponseLogin> call2, Response<ResponseLogin> response) {
                            ResponseLogin user = response.body();

                            /*params.setApi_key(user.getApi_key());
                            params.setRole(user.getRole());*/

                            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("api_key", user.getApi_key());
                            editor.putInt("role", user.getRole());
                            editor.apply();

                            Log.d("MyLog", "success");
                            System.out.println(pref.getAll());

                            if(user.ERROR_ID == null) {
                                Intent i = new Intent(Login.this, MainMenuActivity.class);
                                startActivity(i);
                            } else {
                                check.setText("Неверно введен логин или пароль");
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseLogin> call2, Throwable t) {

                            check.setText("Ошибка сервера");
                            Log.d("MyLog", t.toString());

                        }
                    });

                } catch (Exception e) {

                    check.setText("Ошибка сервера");
                    e.printStackTrace();
                    Log.d("MyLog", "ОШИБКА ВХОДА");

                }

            }
        };
        enter.setOnClickListener(login);

        debugBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, MainMenuActivity.class);
                startActivity(i);
            }
        });

    }

    private void createPost() {
        PostLogin postLogin = new PostLogin(email.getText().toString(),
                password.getText().toString());

        Call<Post> call = jsonPlaceHolderApi.createPostLogin(postLogin);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if (!response.isSuccessful()) {
                    //check.setText("Code: " + response.code() + " " + response.raw());
                    return;
                }

                Post postResponse = response.body();
                String content = "";

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
