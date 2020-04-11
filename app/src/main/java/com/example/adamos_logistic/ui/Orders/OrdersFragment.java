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
import com.example.adamos_logistic.Posts.JsonPlaceHolderApi;
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
                order.add(new Order("Активный заказ"));
                recyclerView.setAdapter(adapter);
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

            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            Call<GetResponseBodyOrders> call = jsonPlaceHolderApi.forOneOrder();

            call.enqueue(new Callback<GetResponseBodyOrders>() {
                @Override
                public void onResponse(Call<GetResponseBodyOrders> call, Response<GetResponseBodyOrders> response) {

                    List<GetResponseBodyOrders> getorders = (List<GetResponseBodyOrders>) response.body();

                    for (GetResponseBodyOrders getOrder : getorders) {
                        String content = "";
                        content += "Order ID: " + getOrder.get_order_ID() + "\n";
                        content += "Order info: " + getOrder.getOrderInfo() + "\n";
                        content += "Date:: " + getOrder.get_date() + "\n";
                        content += "Name: " + getOrder.get_name() + "\n\n";

                        Log.d("MyLog", "ЗАПРОС СФОРМИРОВАН");

                    }
                }

                @Override
                public void onFailure(Call<GetResponseBodyOrders> call, Throwable t) {

                }
            });

            } catch (Exception e) {

            e.printStackTrace();
            Log.d("MyLog", "ОШИБКА ФОРМИРОВАНИЯ ЗАПРОСА");

        }
    }
}