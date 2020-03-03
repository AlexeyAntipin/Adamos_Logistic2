package com.example.adamos_logistic.ui.Chat;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adamos_logistic.Adapters.DataAdapter;
import com.example.adamos_logistic.Posts.JsonPlaceHolderApi;
import com.example.adamos_logistic.Messages;
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

    private EditText chatSendingWindow;

    private String mes;

    private Date date = new Date();

    private List<Messages> message = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        ImageButton send = (ImageButton) root.findViewById(R.id.Send);
        chatSendingWindow = (EditText) root.findViewById(R.id.your_message);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.message_view);

        if (mTimer != null) {
            mTimer.cancel();
        }

        mTimer = new Timer();
        TimerTask mMyTimerTask = new TimerTask() {
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
                            .baseUrl(JsonPlaceHolderApi.HOST)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                    sendChatMessage();

                    Log.d("MyLog", "ЗАПРОС СФОРМИРОВАН");

                } catch (Exception e) {

                    e.printStackTrace();
                    Log.d("MyLog", "ОШИБКА ФОРМИРОВАНИЯ ЗАПРОСА");

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


    // Отправка сообщения чата
    private void sendChatMessage() {

        PostChat postChat = new PostChat(
                "b25c961f1aae707d05509cf68fc3f177",
                "7222f3a105ecc5fa3af58eb222dd4034",
                mes,
                "",
                true);

        Call<PostChat> call = jsonPlaceHolderApi.createPostChat(postChat);

        call.enqueue(new Callback<PostChat>() {
            @Override
            public void onResponse(@NonNull Call<PostChat> call,
                                   @NonNull Response<PostChat> response) {
                Log.d("MyLog", "ЗАПРОС ОТПРАВЛЕН");
            }

            @Override
            public void onFailure(@NonNull Call<PostChat> call,
                                  @NonNull Throwable t) {
                Log.d("MyLog", "ЗАПРОС НЕ ОТПРАВЛЕН");
            }
        });
    }

}