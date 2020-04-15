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
import com.example.adamos_logistic.Posts.AddResponseBodyOrders;
import com.example.adamos_logistic.Posts.GetResponseBodyOrders;
import com.example.adamos_logistic.Posts.JsonPlaceHolderApi;
import com.example.adamos_logistic.Posts.PostAddOrderData;
import com.example.adamos_logistic.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrdersFragment extends Fragment {

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    private String api_key = "37bca7fce88fc16f0b666c64cc82cc55";
    private String name = "Sosu_xuy";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_orders, container, false);

        Button activeOrders = (Button) root.findViewById(R.id.active_orders);
        Button historyOrders = (Button) root.findViewById(R.id.history_orders);
        Button newOrder = (Button) root.findViewById(R.id.new_order);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.orders);

        List<Order> order = new ArrayList<>();
        ForOrders adapter = new ForOrders(getActivity().getApplicationContext(), order);
        activeOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHistoryOrders();
                //order.add(new Order("Активный заказ"));
                //recyclerView.setAdapter(adapter);
            }
        });
        newOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrder();
                //order.add(new Order("Активный заказ"));
                //recyclerView.setAdapter(adapter);
            }
        });
        return root;
    }

    private void addOrder() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JsonPlaceHolderApi.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            PostAddOrderData addOrderData = new PostAddOrderData("02d884ce32aa9b7927766864ec437ac6".toString(), "order1".toString());

            Call<AddResponseBodyOrders> call2 = jsonPlaceHolderApi.addOrder(addOrderData);

            call2.enqueue(new Callback<AddResponseBodyOrders>() {
                @Override
                public void onResponse(Call<AddResponseBodyOrders> call2, Response<AddResponseBodyOrders> response) {

                    AddResponseBodyOrders order = response.body();
                    System.out.println(order.getOrder_id());
                    Log.d("MyLog", "success");

                }

                @Override
                public void onFailure(Call<AddResponseBodyOrders> call2, Throwable t) {
                    Log.d("MyLog", t.toString());
                }
            });

        } catch (Exception e) {

            e.printStackTrace();
            Log.d("MyLog", "ОШИБКА ФОРМИРОВАНИЯ ЗАПРОСА");

        }
    }

    private void getHistoryOrders() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JsonPlaceHolderApi.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            Call<List<GetResponseBodyOrders>> call = jsonPlaceHolderApi.getOrders(api_key);

            call.enqueue(new Callback<List<GetResponseBodyOrders>>() {
                @Override
                public void onResponse(Call<List<GetResponseBodyOrders>> call, Response<List<GetResponseBodyOrders>> response) {

                    List<GetResponseBodyOrders> getorders = response.body();
                    Log.d("MyLog", "Запрос сформирован");

                }

                @Override
                public void onFailure(Call<List<GetResponseBodyOrders>> call, Throwable t) {
                    Log.d("MyLog", "Запрос не сформирован");
                }
            });

            } catch (Exception e) {

            e.printStackTrace();
            Log.d("MyLog", "ОШИБКА ФОРМИРОВАНИЯ ЗАПРОСА");

        }
    }
}