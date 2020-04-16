package com.example.adamos_logistic.ui.Orders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adamos_logistic.Adapters.ForOrders;
import com.example.adamos_logistic.FullInformationFragment;
import com.example.adamos_logistic.Posts.ApiKey;
import com.example.adamos_logistic.Posts.GetResponseBodyOrders;
import com.example.adamos_logistic.Posts.JsonPlaceHolderApi;
import com.example.adamos_logistic.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrdersFragment extends Fragment implements ForOrders.OnItemListener {

    SharedPreferences settings, mSettings;

    private List<GetResponseBodyOrders> ordersList;
    private View root;
    private Context mContext;
    private ForOrders adapter;
    OrdersFragment ordersFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_orders, container, false);
        this.mContext = getContext();
        ordersFragment = this;

        Button activeOrders = root.findViewById(R.id.active_orders);
        Button historyOrders = root.findViewById(R.id.history_orders);
        Button newOrder = root.findViewById(R.id.new_order);

        settings = mContext.getSharedPreferences("orders", Context.MODE_PRIVATE);
        mSettings = mContext.getSharedPreferences("position", Context.MODE_PRIVATE);

        historyOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    getHistoryOrders();

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

            ApiKey api_key = new ApiKey("1ff0c335ba8b4b4057928e3796a07222");

            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            Call<List<GetResponseBodyOrders>> call = jsonPlaceHolderApi.getOrders(api_key);



           call.enqueue(new Callback<List<GetResponseBodyOrders>>() {
               @Override
               public void onResponse(Call<List<GetResponseBodyOrders>> call, Response<List<GetResponseBodyOrders>> response) {
                   List<GetResponseBodyOrders> orderResult = response.body();
                   saveData(orderResult);
                   ordersList = orderResult;
                   adapter = new ForOrders(getActivity().getApplicationContext(), orderResult, ordersFragment);
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
        SharedPreferences.Editor editor = settings.edit();
        Gson gson = new Gson();
        String json = gson.toJson(orders);
        editor.putString("orders", json);
        editor.apply();
    }

    @Override
    public void onItemClick(int position){
        SharedPreferences.Editor ed = mSettings.edit();
        ed.putInt("position", position);
        ed.apply();
        ordersList.get(position);
        //Log.d("MyLog", "changed");
        //Intent intent = new Intent(getActivity(), FullInformationFragment.class);
        //getActivity().getApplicationContext().startActivity(intent);
        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                new FullInformationFragment()).addToBackStack(null).commit();
    }
}