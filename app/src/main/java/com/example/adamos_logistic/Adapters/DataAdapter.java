package com.example.adamos_logistic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adamos_logistic.Message;
import com.example.adamos_logistic.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Message> messages;

    private static int USER_MESSAGE = 1;
    private static int SERVER_MESSAGE = 2;


    public DataAdapter(Context context, ArrayList<Message> messages) {
        this.messages = messages;
        this.inflater = LayoutInflater.from(context);
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
        if (messages.get(position).isFrom_user())
            return USER_MESSAGE;
        else
            return SERVER_MESSAGE;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == USER_MESSAGE) {
            ((UserViewHolder)holder).setUserMessage(messages.get(position).getMessage());
        } else {
            ((ServerViewHolder)holder).setServerMessage(messages.get(position).getMessage());
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

        private void setUserMessage(String message) {
            userMessageBox.setText(message);
            userTimeBox.setText(getMessageTime());
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

        private void setServerMessage(String message) {
            serverMessageBox.setText(message);
            serverTimeBox.setText(getMessageTime());
        }
    }

    // Получение текущего времени (Исправить на серверное время)
    private String getMessageTime() {
        Calendar calender = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.GERMAN);
        return simpleDateFormat.format(calender.getTime());
    }
}
