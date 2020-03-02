package com.example.adamos_logistic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.adamos_logistic.Messages;
import com.example.adamos_logistic.R;

import java.util.List;

public class ForOrders extends RecyclerView.Adapter<ForOrders.ViewHolder> {

    private LayoutInflater inflater;
    private List<Messages> message;

    public ForOrders(Context context, List<Messages> message) {
        this.message = message;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ForOrders.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ForOrders.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForOrders.ViewHolder holder, int position) {
        Messages messages = message.get(position);
            holder.messageView.setText(message.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return message.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView messageView;

        ViewHolder(View view){
            super(view);
            messageView = (TextView) view.findViewById(R.id.textViewOrders);
        }
    }
}
