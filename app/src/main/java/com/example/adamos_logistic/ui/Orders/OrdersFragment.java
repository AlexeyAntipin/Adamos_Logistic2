package com.example.adamos_logistic.ui.Orders;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adamos_logistic.Adapters.ForOrders;
import com.example.adamos_logistic.Order;
import com.example.adamos_logistic.Posts.GetResponseBodyOrders;
import com.example.adamos_logistic.Posts.GetResponseBodyOrdersList;
import com.example.adamos_logistic.Posts.JsonPlaceHolderApi;
import com.example.adamos_logistic.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrdersFragment extends Fragment {

    // ЛЕХА: список ордеров
    private List<GetResponseBodyOrders> ordersList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_orders, container, false);

        Button activeOrders = (Button) root.findViewById(R.id.active_orders);
        Button historyOrders = (Button) root.findViewById(R.id.history_orders);
        Button newOrder = (Button) root.findViewById(R.id.new_order);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.orders);

        List<Order> order = new ArrayList<>();
        ForOrders adapter = new ForOrders(getActivity().getApplicationContext(), order);
        historyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHistoryOrders();
                //order.add(new Order("Активный заказ"));
                //recyclerView.setAdapter(adapter);
            }
        });
        return root;
    }

    private void getHistoryOrders() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JsonPlaceHolderApi.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi jsonPlaceHolderApi;

            String api_key = "37bca7fce88fc16f0b666c64cc82cc54";

            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            Call<GetResponseBodyOrdersList> call = jsonPlaceHolderApi.getOrders(api_key);



            call.enqueue(new Callback<GetResponseBodyOrdersList>() {
                @Override
                public void onResponse(@NonNull Call<GetResponseBodyOrdersList> call, @NonNull Response<GetResponseBodyOrdersList> response) {

                    Log.d("MyLog", "Запрос сформирован");

                    //ordersList = response.body();

                    // ЛЕХА: получаем объект, который содержит список ордеров
                    //assert recievedOrdersList != null;

                    Log.d("MyLog", response.body().toString());

                }

                @Override
                public void onFailure(@NonNull Call<GetResponseBodyOrdersList> call, @NonNull Throwable t) {
                    //t.printStackTrace();
                    Log.d("MyLog", t.toString());
                }
            });

            } catch (Exception e) {

            Log.d("MyLog", e.toString());

        }
    }
}