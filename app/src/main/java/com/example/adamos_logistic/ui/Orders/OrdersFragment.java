package com.example.adamos_logistic.ui.Orders;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.adamos_logistic.R;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrdersFragment extends Fragment {

    SharedPreferences Settings;

    // ЛЕХА: список ордеров
    private List<GetResponseBodyOrders> ordersList;
    private View root;
    private Context mContext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_orders, container, false);
        this.mContext = getContext();

        Button activeOrders = (Button) root.findViewById(R.id.active_orders);
        Button historyOrders = (Button) root.findViewById(R.id.history_orders);
        Button newOrder = (Button) root.findViewById(R.id.new_order);

        Settings = mContext.getSharedPreferences("orders", Context.MODE_PRIVATE);

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

            Call<List<GetResponseBodyOrders>> call = jsonPlaceHolderApi.getOrders(api_key);



           call.enqueue(new Callback<List<GetResponseBodyOrders>>() {
               @Override
               public void onResponse(Call<List<GetResponseBodyOrders>> call, Response<List<GetResponseBodyOrders>> response) {
                   List<GetResponseBodyOrders> orderResult = response.body();
                   saveData(orderResult);
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
               public void onFailure(Call<List<GetResponseBodyOrders>> call, Throwable t) {

               }
           });
            } catch (Exception e) {

            Log.d("MyLog", e.toString());

        }
    }

    public void saveData(List<GetResponseBodyOrders> orders) {
        SharedPreferences.Editor editor = Settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(orders);
        editor.putString("orders", json);
        editor.apply();
    }
}