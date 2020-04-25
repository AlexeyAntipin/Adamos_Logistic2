package com.example.adamos_logistic.ui.Orders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adamos_logistic.Adapters.ForOrders;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.adamos_logistic.FullInformationFragment;
import com.example.adamos_logistic.ApiKey;
import com.example.adamos_logistic.GetResponseBodyOrders;
import com.example.adamos_logistic.OrderAttributes;
import com.example.adamos_logistic.Posts.JsonPlaceHolderApi;
import com.example.adamos_logistic.R;
import com.google.gson.Gson;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrdersFragment extends Fragment implements ForOrders.OnItemListener {

    SharedPreferences settings, mSettings, apiKey;

    private List<GetResponseBodyOrders> ordersList;
    private View root;
    private Context mContext;
    private ForOrders adapter;
    OrdersFragment ordersFragment;
    private String api_key = "";

    private FloatingActionButton buttonAddOrder;

    // TODO: добавить функционал галочке "только активные"
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_orders_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                getHistoryOrders();
                break;
            case R.id.drawer_layout:
            default:
                break;
        }

        return true;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_orders, container, false);
        this.mContext = getContext();
        ordersFragment = this;
        setHasOptionsMenu(true);

        buttonAddOrder = root.findViewById(R.id.button_add_order);

        settings = mContext.getSharedPreferences("orders", Context.MODE_PRIVATE);
        mSettings = mContext.getSharedPreferences("position", Context.MODE_PRIVATE);
        apiKey = mContext.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        if(apiKey.contains("api_key"))
            api_key = apiKey.getString("api_key", null);


        getHistoryOrders();
        saveData(ordersList);

        buttonAddOrder.setOnClickListener(v -> {
            goToAddOrderFragment();
        });

//        historyOrders.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    getHistoryOrders();
//                }
//        });
//        newOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToAddOrderFragment();
//                //order.add(new Order("Активный заказ"));
//                //recyclerView.setAdapter(adapter);
//            }
//        });
        return root;
    }


    private void goToAddOrderFragment() {
        Fragment newFragment = new AddOrderFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void addOrder() {

    }

    private void getHistoryOrders() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JsonPlaceHolderApi.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi jsonPlaceHolderApi;


            ApiKey api = new ApiKey(api_key);

            jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            Call<List<GetResponseBodyOrders>> call = jsonPlaceHolderApi.getOrders(api);



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
                       Log.d("MyLog", e.toString());

                   }
               }
               @Override
               public void onFailure(@NonNull Call<List<GetResponseBodyOrders>> call,
                                     @NonNull Throwable t) {
                   Log.d("MyLog", t.toString());
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
        getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                new FullInformationFragment()).addToBackStack(null).commit();
    }
}