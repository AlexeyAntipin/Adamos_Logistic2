package com.example.adamos_logistic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adamos_logistic.Posts.GetMessages;
import com.example.adamos_logistic.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private List<GetMessages> messages;
    private int user_id;

    private static int USER_MESSAGE = 1;
    private static int SERVER_MESSAGE = 2;


    public MessageAdapter(Context context, List<GetMessages> messages, int user_id) {
        this.messages = messages;
        this.inflater = LayoutInflater.from(context);
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if (viewType == USER_MESSAGE) {
            view = inflater.inflate(R.layout.user_message, parent, false);
            return new UserViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.server_message, parent, false);
            return new ServerViewHolder(view);
        }
    }

    // Узнаем тип сообщения
    @Override
    public int getItemViewType(int position) {
        if (user_id == messages.get(position).getUser_id())
            return USER_MESSAGE;
        else
            return SERVER_MESSAGE;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == USER_MESSAGE) {
            ((UserViewHolder)holder).setUserMessage(messages.get(position).getValue(), getMessageTime(position));
        } else {
            ((ServerViewHolder)holder).setServerMessage(messages.get(position).getValue(), getMessageTime(position));
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    // Разметка отправленного сообщения
    class UserViewHolder extends RecyclerView.ViewHolder {

        private TextView userMessageBox;
        private TextView userTimeBox;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);

            userMessageBox = itemView.findViewById(R.id.user_message_box);
            userTimeBox = itemView.findViewById(R.id.user_time_box);
        }

        private void setUserMessage(String message, String time) {
            userMessageBox.setText(message);
            userTimeBox.setText(time);
        }
    }

    // Разметка пришедшего сообщения
    class ServerViewHolder extends RecyclerView.ViewHolder {

        private TextView serverMessageBox;
        private TextView serverTimeBox;

        ServerViewHolder(@NonNull View itemView) {
            super(itemView);

            serverMessageBox = itemView.findViewById(R.id.server_message_box);
            serverTimeBox = itemView.findViewById(R.id.server_time_box);
        }

        private void setServerMessage(String message, String time) {
            serverMessageBox.setText(message);
            serverTimeBox.setText(time);
        }
    }

    // Получение текущего времени (Исправить на серверное время)
    private String getMessageTime(int position) {
        return messages.get(position).getTime();
    }
}
