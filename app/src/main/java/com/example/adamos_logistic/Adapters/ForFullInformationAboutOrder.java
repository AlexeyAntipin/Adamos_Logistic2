package com.example.adamos_logistic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adamos_logistic.OrderAttributes;
import com.example.adamos_logistic.R;

import java.util.List;

public class ForFullInformationAboutOrder extends RecyclerView.Adapter<ForFullInformationAboutOrder.ViewHolder> {
    private LayoutInflater inflater;
    private List<OrderAttributes> attributes;

    public ForFullInformationAboutOrder(Context context, List<OrderAttributes> attributes) {
        this.attributes = attributes;
        this.inflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.full_information, parent, false);
        return new ForFullInformationAboutOrder.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForFullInformationAboutOrder.ViewHolder holder, int position) {
        holder.name.setText(attributes.get(position).getAttribute_description());
        if (attributes.get(position).getType() == 20)
        holder.attribute.setText(attributes.get(position).getDescription());
        else holder.attribute.setText(attributes.get(position).getValue());
    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView attribute;

        ViewHolder(View view){
            super(view);
            name = view.findViewById(R.id.name);
            attribute = view.findViewById(R.id.attribute);
        }
    }
}
