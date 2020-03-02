package com.example.adamos_logistic.ui.Chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adamos_logistic.DataAdapter;
import com.example.adamos_logistic.Posts.JsonPlaceHolderApi;
import com.example.adamos_logistic.Messages;
import com.example.adamos_logistic.Posts.Post;
import com.example.adamos_logistic.Posts.PostChat;
import com.example.adamos_logistic.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatFragment extends Fragment {

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private Timer mTimer;
    private TimerTask mMyTimerTask;

    private View root;

    String mes;

    Date date = new Date();

    List<Messages> message = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_chat, container, false);
        ImageButton send = (ImageButton) root.findViewById(R.id.Send);
        EditText chatSendingWindow = (EditText) root.findViewById(R.id.your_message);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.message_view);

        if (mTimer != null) {
            mTimer.cancel();
        }

        // re-schedule timer here
        // otherwise, IllegalStateException of
        // "TimerTask is scheduled already"
        // will be thrown
        mTimer = new Timer();
        mMyTimerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        };
        //mMyTimerTask = new TimerTask();

        mTimer.schedule(mMyTimerTask, 1000, 5000);

        super.onCreate(savedInstanceState);

        // создаем адаптер
        DataAdapter adapter = new DataAdapter(getActivity().getApplicationContext(), message);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mes = chatSendingWindow.getText().toString() + "\n";
                try {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://10.192.210.110/newMessage")
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
                setInitialData();
                recyclerView.setAdapter(adapter);
                chatSendingWindow.setText("");
            }
        });

        return root;
    }

    private void setInitialData() {
        message.add(new Messages(mes + "   " + date.toString()));
    }

    private void createPost() {

        TextView check = (TextView) root.findViewById(R.id.check);

        PostChat postChat = new PostChat("d", mes, false);

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

                content += "SUCCESS: " + postResponse.getSUCCESS() + "\n\n";

                check.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                check.setText(t.getMessage());
            }
        });
    }

}