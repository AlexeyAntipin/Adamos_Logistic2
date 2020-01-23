package com.example.adamos_logistic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Messages> message;

    DataAdapter(Context context, List<Messages> message) {
        this.message = message;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder holder, int position) {
        Messages messages = message.get(position);
        holder.messageView.setText(messages.getMessage());
    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView messageView;

        ViewHolder(View view){
            super(view);
            messageView = (TextView) view.findViewById(R.id.message);
        }
    }
}
