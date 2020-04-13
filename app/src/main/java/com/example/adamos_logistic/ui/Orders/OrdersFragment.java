package com.example.adamos_logistic.ui.Orders;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adamos_logistic.Adapters.ForOrders;
import com.example.adamos_logistic.Posts.ApiKey;
import com.example.adamos_logistic.Posts.GetResponseBodyOrders;
import com.example.adamos_logistic.Posts.JsonPlaceHolderApi;
import com.example.adamos_logistic.Posts.OrderGetResult;
import com.example.adamos_logistic.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrdersFragment extends Fragment {

    // ЛЕХА: список ордеров
    private List<GetResponseBodyOrders> ordersList;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_orders, container, false);

        Button activeOrders = (Button) root.findViewById(R.id.active_orders);
        Button historyOrders = (Button) root.findViewById(R.id.history_orders);
        Button newOrder = (Button) root.findViewById(R.id.new_order);

        historyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getHistoryOrders();
                    //Parcelable recyclerViewState = recyclerView.getLayoutManager().onSaveInstanceState();
                    //recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
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

            ApiKey api_key = new ApiKey("13086f47094f7e5916e161ac33b8fdec");

            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            Call<OrderGetResult> call = jsonPlaceHolderApi.getOrders(api_key);



           call.enqueue(new Callback<OrderGetResult>() {
               @Override
               public void onResponse(Call<OrderGetResult> call, Response<OrderGetResult> response) {
                   OrderGetResult orderResult = response.body();
                   ForOrders adapter = new ForOrders(getActivity().getApplicationContext(), orderResult);
                   try {
                       RecyclerView recyclerView = root.findViewById(R.id.orders);
                       recyclerView.setAdapter(adapter);
                       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               }
               @Override
               public void onFailure(Call<OrderGetResult> call, Throwable t) {

               }
           });
            } catch (Exception e) {

            Log.d("MyLog", e.toString());

        }
    }
}