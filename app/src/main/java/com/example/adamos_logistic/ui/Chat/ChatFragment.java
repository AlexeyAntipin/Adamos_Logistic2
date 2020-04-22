package com.example.adamos_logistic.ui.Chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adamos_logistic.Adapters.MessageAdapter;
import com.example.adamos_logistic.ApiKey;
import com.example.adamos_logistic.Message;
import com.example.adamos_logistic.Posts.GetMessages;
import com.example.adamos_logistic.Posts.JsonPlaceHolderApi;
import com.example.adamos_logistic.Posts.Order_id;
import com.example.adamos_logistic.Posts.PostAddMessage;
import com.example.adamos_logistic.Posts.ResponseNewMessage;
import com.example.adamos_logistic.Posts.UserInfo;
import com.example.adamos_logistic.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChatFragment extends Fragment {

    UserInfo userInfo;

    String api_key;

    List<GetMessages> messages;

    SharedPreferences apiKey;
    private Context mContext;

    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private MessageAdapter adapter;

    private Timer mTimer;

    private EditText chatSendingWindow;

    private String mes;

    private boolean testUserBool = false;

    private ArrayList<Message> message = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mContext = getContext();
        apiKey = mContext.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        if(apiKey.contains("api_key"))
            api_key = apiKey.getString("api_key", null);

        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        ImageButton send = root.findViewById(R.id.Send);
        chatSendingWindow = root.findViewById(R.id.your_message);
        RecyclerView recyclerView = root.findViewById(R.id.message_view);

        getUserInfo(api_key);

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

        mTimer.schedule(mMyTimerTask, 1000);

        ApiKey apiKey = new ApiKey(api_key);
        getMessages(apiKey);

        super.onCreate(savedInstanceState);
        adapter = new MessageAdapter(getActivity().getApplicationContext(), messages, userInfo.getId());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        send.setOnClickListener(v -> {
            mes = chatSendingWindow.getText().toString();
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(JsonPlaceHolderApi.HOST)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                PostAddMessage messageData = new PostAddMessage("1ff0c335ba8b4b4057928e3796a07222", 840, mes, 0);

                Call<ResponseNewMessage> callAdd = jsonPlaceHolderApi.addMessage(messageData);

                callAdd.enqueue(new Callback<ResponseNewMessage>() {
                    @Override
                    public void onResponse(Call<ResponseNewMessage> callAdd, Response<ResponseNewMessage> response) {

                        ResponseNewMessage message = response.body();
                        Log.d("MyLog", "success");

                    }

                    @Override
                    public void onFailure(Call<ResponseNewMessage> callAdd, Throwable t) {
                        Log.d("MyLog", t.toString());
                    }
                });

            } catch (Exception e) {

                e.printStackTrace();
                Log.d("MyLog", e.toString());

            }
            setMessage();
        });

        return root;
    }

    // Пока что не нужно, дата выводится через адаптер
    private void setMessage() {
        String check = mes.replaceAll("\\s", "");
        if (!check.isEmpty()) {
            testUserBool = !testUserBool;
            message.add(new Message(mes, "", !testUserBool, ""));
        }
    }

    private void getMessages(ApiKey api_key) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JsonPlaceHolderApi.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            Order_id order_id = new Order_id(api_key.getApi_key(), 840);

            Call<List<GetMessages>> callMessages = jsonPlaceHolderApi.getMessages(order_id);

            callMessages.enqueue(new Callback<List<GetMessages>>() {
                @Override
                public void onResponse(Call<List<GetMessages>> callMessages, Response<List<GetMessages>> response) {
                    messages = response.body();

                    System.out.println(messages);
                    for (int i = 0; i < messages.size(); i++) {
                        Log.d("MyLog", messages.get(i).toString());
                    }

                    //Log.d("MyLog", "success");

                }

                @Override
                public void onFailure(Call<List<GetMessages>> callMessages, Throwable t) {

                    Log.d("MyLog", t.toString());

                }
            });

        } catch (Exception e) {

            e.printStackTrace();
            Log.d("MyLog", "ОШИБКА ВХОДА");

        }
    }

    private void getUserInfo(String api_key) {

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JsonPlaceHolderApi.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
            ApiKey apiKey = new ApiKey(api_key);
            Call<UserInfo> call = jsonPlaceHolderApi.getUserInfo(apiKey);
            call.enqueue(new Callback<UserInfo>() {
                @Override
                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                    userInfo = response.body();
                }

                @Override
                public void onFailure(Call<UserInfo> call, Throwable t) {
                    Log.d("MyLog", t.toString());
                    Log.d("MyLog", "Выход в onFailure");
                }
            });

        } catch(Exception e) {
            Log.d("MyLog", e.toString());
            Log.d("MyLog", "Выход в catch");
        }
    }
}