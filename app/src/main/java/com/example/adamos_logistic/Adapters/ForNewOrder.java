package com.example.adamos_logistic.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adamos_logistic.ForSaveInNewOrder;
import com.example.adamos_logistic.Posts.AddResponseBodyOrders;
import com.example.adamos_logistic.Posts.Values;
import com.example.adamos_logistic.R;
import com.example.adamos_logistic.ui.Orders.AddOrderFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ForNewOrder extends RecyclerView.Adapter<ForNewOrder.ViewHolder> {
    private LayoutInflater inflater;
    private List<AddResponseBodyOrders> attributes;
    private List<AddResponseBodyOrders> attributes1;
    private Context context;
    private List<ForSaveInNewOrder> save;
    SharedPreferences saveAttributes;

    public ForNewOrder(Context context, List<AddResponseBodyOrders> attributes) {
        this.attributes = attributes;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
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
        if (attributes.get(position).getAttribute_type() == 20) {
            holder.attribute_from_user.setVisibility(View.GONE);
            holder.choose_date.setVisibility(View.GONE);
            holder.spinner_for_user.setVisibility(View.VISIBLE);
            List<Values> values = attributes.get(position).getVALUES();
            List<String> descriptions = new ArrayList<>();
            for (Values value: values) {
                descriptions.add(value.getDescription());
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, descriptions);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spinner_for_user.setAdapter(adapter);
        }
        else if (attributes.get(position).getAttribute_type() == 10) {
            holder.attribute_from_user.setVisibility(View.GONE);
            holder.spinner_for_user.setVisibility(View.GONE);
            holder.choose_date.setVisibility(View.VISIBLE);
        }
        else {
            holder.spinner_for_user.setVisibility(View.GONE);
            holder.choose_date.setVisibility(View.GONE);
            holder.attribute_from_user.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return attributes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView attribute_name;
        final EditText attribute_from_user;
        final Spinner spinner_for_user;
        final CalendarView choose_date;

        ViewHolder(View view){
            super(view);
            attribute_name = view.findViewById(R.id.attribute_name);
            attribute_from_user = view.findViewById(R.id.attribute_from_user);
            spinner_for_user = view.findViewById(R.id.spinner_for_user);
            choose_date = view.findViewById(R.id.choose_date);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        SharedPreferences Attributes = context.getSharedPreferences("Attributes", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = Attributes.getString("Attributes", null);
        Type type = new TypeToken<ArrayList<AddResponseBodyOrders>>() {}.getType();
        attributes1 = gson.fromJson(json, type);

        if (attributes1 == null) {
            attributes1 = new ArrayList<>();
        }
        Log.d("MyLog", String.valueOf(attributes1.size()));
        int pos = holder.getAdapterPosition();
        if (attributes1.get(pos).getAttribute_type() == 20) {
            Spinner spinner_for_user = holder.spinner_for_user;
            spinner_for_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String value = attributes1.get(pos).getVALUES().get(position).getValue();
                    ForSaveInNewOrder forSpinnerInNewOrder = new ForSaveInNewOrder(pos, value);
                    save.add(forSpinnerInNewOrder);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else if (attributes1.get(pos).getAttribute_type() == 10) {
            CalendarView calendarView = holder.choose_date;
            calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                String value = new StringBuilder().append(mYear)
                        .append("-").append(mMonth)
                        .append("-").append(mDay).toString();
                ForSaveInNewOrder forSpinnerInNewOrder = new ForSaveInNewOrder(pos, value);
                save.add(forSpinnerInNewOrder);
            });
        }
        else if (attributes1.get(pos).getAttribute_type() == 0) {
            EditText attribute_from_user = holder.attribute_from_user;
            String value = attribute_from_user.getText().toString();
            ForSaveInNewOrder forSpinnerInNewOrder = new ForSaveInNewOrder(pos, value);
            save.add(forSpinnerInNewOrder);
        }
        saveAttributes = context.getSharedPreferences("saveAttributes", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = saveAttributes.edit();
        Gson gson1 = new Gson();
        String json1 = gson1.toJson(save);
        editor.putString("saveAttributes", json1);
        editor.apply();
    }
}