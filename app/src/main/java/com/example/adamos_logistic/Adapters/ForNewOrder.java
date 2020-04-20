package com.example.adamos_logistic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adamos_logistic.OrderAttributes;
import com.example.adamos_logistic.Posts.AddResponseBodyOrders;
import com.example.adamos_logistic.R;

import java.util.List;

public class ForNewOrder extends RecyclerView.Adapter<ForNewOrder.ViewHolder> {
    private LayoutInflater inflater;
    private List<AddResponseBodyOrders> attributes;

    public ForNewOrder(Context context, List<AddResponseBodyOrders> attributes) {
        this.attributes = attributes;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.field_for_new_order, parent, false);
        return new ForNewOrder.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForNewOrder.ViewHolder holder, int position) {
        holder.attribute_name.setText(attributes.get(position).getAttribute_description());
    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView attribute_name;
        final EditText attribute_from_user;

        ViewHolder(View view){
            super(view);
            attribute_name = view.findViewById(R.id.attribute_name);
            attribute_from_user = view.findViewById(R.id.attribute_from_user);
        }
    }
}

