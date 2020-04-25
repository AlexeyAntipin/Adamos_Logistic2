package com.example.adamos_logistic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adamos_logistic.GetResponseBodyOrders;
import com.example.adamos_logistic.R;

import java.util.List;

public class ForOrders extends RecyclerView.Adapter<ForOrders.ViewHolder> {

    private LayoutInflater inflater;
    private List<GetResponseBodyOrders> orders;
    private  OnItemListener mOnItemListener;

    public ForOrders(Context context, List<GetResponseBodyOrders> order, OnItemListener onItemListener) {
        this.orders = order;
        this.inflater = LayoutInflater.from(context);
        this.mOnItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.order_text_example, parent, false);
        return new ForOrders.ViewHolder(view, mOnItemListener);
    }


    @Override
    public void onBindViewHolder(ForOrders.ViewHolder holder, int position) {
        String shortName = makeShorterString(orders.get(position).getName());
        holder.order_name.setText(shortName);
        holder.time_created.setText(orders.get(position).getTimeCreated());
        holder.order_status.setText(orders.get(position).getOrderStatus());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        final TextView order_name;
        final TextView time_created;
        final TextView order_status;

        OnItemListener onItemListener;

        ViewHolder(View view, OnItemListener onItemListener) {
            super(view);
            order_name = view.findViewById(R.id.textView_order_name);
            time_created = view.findViewById(R.id.textView_order_time_created);
            order_status = view.findViewById(R.id.textView_order_status);
            this.onItemListener = onItemListener;

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    // Делает строчку короче для корректного отображения
    private String makeShorterString(String input) {
        String output;
        if (input.length() > 14) {
            output = input.substring(0, 13) + "…";
        } else {
            output = input;
        }

        return output;
    }

    public interface OnItemListener {
            void onItemClick(int position);
    }
}
