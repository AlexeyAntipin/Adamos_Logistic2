package com.example.adamos_logistic.ui.Orders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adamos_logistic.Adapters.ForFullInformationAboutOrder;
import com.example.adamos_logistic.GetResponseBodyOrders;
import com.example.adamos_logistic.OrderAttributes;
import com.example.adamos_logistic.R;
import com.example.adamos_logistic.ui.Chat.ChatFragment;
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

    private ForFullInformationAboutOrder adapter;

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
        Button chatButton = root.findViewById(R.id.chatButton);

        order = orders.get(position);
        attributes = order.getATTRIBUTES();

        order_name.setText(order.getName());
        order_status.setText(order.getOrderStatus());
        order_time_created.setText(order.getTimeCreated());

        adapter = new ForFullInformationAboutOrder(getActivity().getApplicationContext(), attributes);
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

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                        new ChatFragment(order.getOrder_id())).commit();
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
