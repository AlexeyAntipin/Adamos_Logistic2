package com.example.adamos_logistic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.adamos_logistic.Order;
import com.example.adamos_logistic.R;

import java.util.List;

public class ForOrders extends RecyclerView.Adapter<ForOrders.ViewHolder> {

    private LayoutInflater inflater;
    private List<Order> order;

    public ForOrders(Context context, List<Order> order) {
        this.order = order;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public ForOrders.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_for_orders, parent, false);
        return new ForOrders.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForOrders.ViewHolder holder, int position) {
        holder.orderView.setText(order.get(position).getOrder());
    }

    @Override
    public int getItemCount() {
        return order.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView orderView;

        ViewHolder(View view){
            super(view);
            orderView = (TextView) view.findViewById(R.id.textViewOrders);
        }
    }
}
