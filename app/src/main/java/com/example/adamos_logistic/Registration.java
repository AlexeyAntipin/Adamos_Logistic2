package com.example.adamos_logistic;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

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
        check = (TextView) findViewById(R.id.check);
        password = (EditText) findViewById(R.id.PASSWORD);
        PassRight = (EditText) findViewById(R.id.PassRight);
        registration = (Button) findViewById(R.id.registr) ;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.120/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        createPost();

        final LinearLayout linear = (LinearLayout) findViewById(R.id.Linear);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v.getId() == R.id.registr && !password.getText().toString().equals(PassRight.getText().toString())) {
                    check.setText("Пароли не совпадают");
                }
                else if (v.getId() == R.id.registr && password.getText().toString().equals(PassRight.getText().toString())) {
                    check.setText("");




                        Call<List<Post>> call = jsonPlaceHolderApi.getPosts();

                        call.enqueue(new Callback<List<Post>>() {
                            @Override
                            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                                if (!response.isSuccessful()) {
                                    check.setText("Code: " + response.code());
                                    return;
                                }

                                List<Post> posts = response.body();

                                for (Post post : posts) {
                                    String content = "";
                                    //content += "id: " + post.getId() + "\n";

                                    check.append(content);
                                }
                            }


                            @Override
                            public void onFailure(Call<List<Post>> call, Throwable t) {
                                check.setText(t.getMessage());
                            }
                        });



                }
                    }
                };
        registration.setOnClickListener(listener);
    }

    private void createPost() {
        Post post = new Post("a", "b", "c", "d", "e");

        Call<Post> call = jsonPlaceHolderApi.createPost(post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (!response.isSuccessful()) {
                    check.setText("Code: " + response.code());
                    return;
                }

                Post postResponse = response.body();

                String content = "";
                content += "Code: " + response.code() + "\n";
                content += "NAME: " + postResponse.getNAME() + "\n";
                content += "SURNAME: " + postResponse.getSURNAME() + "\n";
                content += "SECONDNAME: " + postResponse.getSECONDNAME() + "\n";
                content += "PASSWORD: " + postResponse.getPASSWORD() + "\n";
                content += "EMAIL: " + postResponse.getEMAIL() + "\n\n";

                check.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }
}
