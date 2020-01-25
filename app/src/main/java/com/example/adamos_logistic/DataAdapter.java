package com.example.adamos_logistic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private LayoutInflater inflater;
    //private List<Messages> message;
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
        if(position % 2 == 0) {
            for(int i = 0; i < message.size(); i++) {
                holder.messageView.setText(message.get(position).getMessage());
            }
        }
        else {
            for(int i = 0; i < message.size(); i++) {
                holder.messageView2.setText(message.get(position).getMessage());
            }
        }
    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView messageView;
        final TextView messageView2;

        ViewHolder(View view){
            super(view);
            messageView = (TextView) view.findViewById(R.id.message);
            messageView2 = (TextView) view.findViewById(R.id.message2);
        }
    }
}
