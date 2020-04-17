package com.example.adamos_logistic;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.adamos_logistic.Adapters.ForFullInformationAbourOrder;
import com.example.adamos_logistic.ui.Orders.OrdersFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FullInformationFragment extends Fragment {

    private View root;
    private Context mContext;
    private List<GetResponseBodyOrders> orders;
    private List<OrderAttributes> attributes;
    private int position;
    GetResponseBodyOrders order;

    SharedPreferences mSettings;

    private ForFullInformationAbourOrder adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_full_information, container, false);
        this.mContext = getContext();
        loadData();
        mSettings = mContext.getSharedPreferences("position", Context.MODE_PRIVATE);
        if(mSettings.contains("position"))
            position = mSettings.getInt("position", 0);

        TextView order_name = root.findViewById(R.id.order_name);
        TextView order_status = root.findViewById(R.id.order_status);
        TextView order_time_created = root.findViewById(R.id.order_time_created);

        Button backButton = root.findViewById(R.id.backButton);

        order = orders.get(position);
        attributes = order.getATTRIBUTES();

        order_name.setText(order.getName());
        order_status.setText(order.getOrderStatus());
        order_time_created.setText(order.getTimeCreated());

        adapter = new ForFullInformationAbourOrder(getActivity().getApplicationContext(), attributes);
        RecyclerView recyclerView = root.findViewById(R.id.attributes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        new OrdersFragment()).commit();
            }
        });

        return root;
    }


    public void loadData() {
        SharedPreferences Settings = mContext.getSharedPreferences("orders", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = Settings.getString("orders", null);
        Type type = new TypeToken<ArrayList<GetResponseBodyOrders>>() {}.getType();
        orders = gson.fromJson(json, type);

        if (orders == null) {
            orders = new ArrayList<>();
        }
    }
}
