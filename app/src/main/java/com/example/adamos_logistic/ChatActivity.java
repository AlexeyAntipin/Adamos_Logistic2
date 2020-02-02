package com.example.adamos_logistic;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatActivity extends AppCompatActivity {

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    String mes;

    Date date = new Date();

    List<Messages> message = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);

        ImageButton send = (ImageButton) findViewById(R.id.Send);
        EditText yourMessage = (EditText) findViewById(R.id.your_message);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.message_view);

        // создаем адаптер
        DataAdapter adapter = new DataAdapter(this, message);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                mes = yourMessage.getText().toString() + "\n";
                setInitialData();
                recyclerView.setAdapter(adapter);
                yourMessage.setText("");
            }
        });

    }

    private void setInitialData() {
        message.add(new Messages(mes + "   " + date.toString()));
    }

    private void createPost() {
        PostChat postChat = new PostChat("d", "e");

        Call<Post> call = jsonPlaceHolderApi.createPostChat(postChat);

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
                content += "ERROR: " + postResponse.getERROR() + "\n\n";

                //check.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                //check.setText(t.getMessage());
            }
        });
    }
}