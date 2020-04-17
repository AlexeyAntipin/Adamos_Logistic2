package com.example.adamos_logistic.ui.Chat;

import android.os.Bundle;
import android.os.Parcelable;
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
import com.example.adamos_logistic.Message;
import com.example.adamos_logistic.Posts.GetMessages;
import com.example.adamos_logistic.Posts.JsonPlaceHolderApi;
import com.example.adamos_logistic.Posts.Order_id;
import com.example.adamos_logistic.Posts.PostAddMessage;
import com.example.adamos_logistic.Posts.ResponseNewMessage;
import com.example.adamos_logistic.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

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

    private boolean testUserBool = false;

    private ArrayList<Message> message = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_chat, container, false);
        ImageButton send = root.findViewById(R.id.Send);
        chatSendingWindow = root.findViewById(R.id.your_message);
        RecyclerView recyclerView = root.findViewById(R.id.message_view);

        /*if (mTimer != null) {
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

        mTimer.schedule(mMyTimerTask, 1000, 5000);*/
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JsonPlaceHolderApi.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            Order_id order_id = new Order_id("1ff0c335ba8b4b4057928e3796a07222", 840);

            Call<List<GetMessages>> call = jsonPlaceHolderApi.getMessages(order_id);

            call.enqueue(new Callback<List<GetMessages>>() {
                @Override
                public void onResponse(Call<List<GetMessages>> call, Response<List<GetMessages>> response) {
                    List<GetMessages> messages = response.body();

                    System.out.println(messages);
                    GetMessages[] myArray = new GetMessages[messages.size()];
                    messages.toArray(myArray);

                    for(int i=0; i<myArray.length; i++){
                        System.out.println(myArray[i].getUser_id() + " " +myArray[i].getValue());
                    }
                    //Log.d("MyLog", "success");

                }

                @Override
                public void onFailure(Call<List<GetMessages>> call, Throwable t) {

                    Log.d("MyLog", t.toString());

                }
            });

        } catch (Exception e) {

            e.printStackTrace();
            Log.d("MyLog", "ОШИБКА ВХОДА");

        }

        super.onCreate(savedInstanceState);

        // создаем адаптер
        DataAdapter adapter = new DataAdapter(getActivity().getApplicationContext(), message);

        send.setOnClickListener(v -> {
            mes = chatSendingWindow.getText().toString();
            try {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(JsonPlaceHolderApi.HOST)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

                PostAddMessage messageData = new PostAddMessage("1ff0c335ba8b4b4057928e3796a07222", 840, mes, 0);

                Call<ResponseNewMessage> call2 = jsonPlaceHolderApi.addMessage(messageData);

                call2.enqueue(new Callback<ResponseNewMessage>() {
                    @Override
                    public void onResponse(Call<ResponseNewMessage> call2, Response<ResponseNewMessage> response) {

                        ResponseNewMessage message = response.body();
                        Log.d("MyLog", "success");

                    }

                    @Override
                    public void onFailure(Call<ResponseNewMessage> call, Throwable t) {
                        Log.d("MyLog", t.toString());
                    }
                });

            } catch (Exception e) {

                e.printStackTrace();
                Log.d("MyLog", e.toString());

            }
            setMessage();
            Parcelable recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
            recyclerView.setAdapter(adapter);
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
            chatSendingWindow.setText("");
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
}