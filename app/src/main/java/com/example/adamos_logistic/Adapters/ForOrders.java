package com.example.adamos_logistic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adamos_logistic.Posts.GetResponseBodyOrders;
import com.example.adamos_logistic.R;

import java.util.List;

public class ForOrders extends RecyclerView.Adapter<ForOrders.ViewHolder> {

    private LayoutInflater inflater;
    private List<GetResponseBodyOrders> orders;

    public ForOrders(Context context, List<GetResponseBodyOrders> order) {
        this.orders = order;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.order_text_example, parent, false);
        return new ForOrders.ViewHolder(view);
    }

    @Override
        public void onBindViewHolder(ForOrders.ViewHolder holder, int position) {
            holder.order_name.setText(orders.get(position).getName());
            holder.time_created.setText(orders.get(position).getTimeCreated());
            holder.order_status.setText(orders.get(position).getOrderStatus());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView order_name;
        final TextView time_created;
        final TextView order_status;

        ViewHolder(View view){
            super(view);
            order_name = view.findViewById(R.id.order_name);
            time_created = view.findViewById(R.id.time_created);
            order_status = view.findViewById(R.id.order_status);
        }
    }
}
