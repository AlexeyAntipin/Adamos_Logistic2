package com.example.adamos_logistic.ui.Orders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adamos_logistic.Adapters.ForNewOrder;
import com.example.adamos_logistic.ApiKey;
import com.example.adamos_logistic.Posts.AddResponseBodyOrders;
import com.example.adamos_logistic.Posts.AllAttributesFromUser;
import com.example.adamos_logistic.Posts.AttributesFromUser;
import com.example.adamos_logistic.Posts.JsonPlaceHolderApi;
import com.example.adamos_logistic.Posts.OrderAddInfo;
import com.example.adamos_logistic.Posts.Order_id;
import com.example.adamos_logistic.Posts.Step;
import com.example.adamos_logistic.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddOrderFragment extends Fragment {

    private String api_key = "";
    SharedPreferences apiKey;
    private Context mContext;
    private Order_id orderId;
    private List<OrderAddInfo> info;
    private int order_id;
    private OrderAddInfo a;
    private List<AddResponseBodyOrders> addResponseBodyOrders;
    private List<AttributesFromUser> ATTRIBUTES = new ArrayList<>();
    private int i = 0;
    View root;
    TextView step, category_name;
    Button next, back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_new_order, container, false);

        mContext = getContext();

        step = root.findViewById(R.id.step);
        category_name = root.findViewById(R.id.category_name);
        next = root.findViewById(R.id.next);
        back = root.findViewById(R.id.back);


        apiKey = mContext.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        if(apiKey.contains("api_key"))
            api_key = apiKey.getString("api_key", null);

        addNewOrder();
        addOrderInfo();

        return root;
    }

    private void addNewOrder() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JsonPlaceHolderApi.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
            ApiKey api = new ApiKey(api_key);
            Call<Order_id> call = jsonPlaceHolderApi.addOrder(api);
            call.enqueue(new Callback<Order_id>() {
                @Override
                public void onResponse(Call<Order_id> call, Response<Order_id> response) {
                    orderId = response.body();
                    order_id = orderId.getOrder_id();
                }

                @Override
                public void onFailure(Call<Order_id> call, Throwable t) {
                    Log.d("MyLog", t.toString());
                    Log.d("MyLog", "ОШИБКА: выход в onFailure");
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
            Log.d("MyLog",  e.toString());
            Log.d("MyLog", "ОШИБКА: вывалилось в catch");
        }
    }

    private void addOrderInfo() {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JsonPlaceHolderApi.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
            ApiKey api = new ApiKey(api_key);
            Call<List<OrderAddInfo>> call = jsonPlaceHolderApi.addOrderInfo(api);
            call.enqueue(new Callback<List<OrderAddInfo>>() {
                @Override
                public void onResponse(Call<List<OrderAddInfo>> call, Response<List<OrderAddInfo>> response) {
                    info = response.body();
                    category_name.setText(info.get(i).getName());
                    try {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(JsonPlaceHolderApi.HOST)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                        Step step = new Step(api_key, info.get(i).getNumber());
                        Call<List<AddResponseBodyOrders>> call2 = jsonPlaceHolderApi.addStep(step);
                        call2.enqueue(new Callback<List<AddResponseBodyOrders>>() {
                            @Override
                            public void onResponse(Call<List<AddResponseBodyOrders>> call,
                                                   Response<List<AddResponseBodyOrders>> response) {
                                addResponseBodyOrders = response.body();
                                ForNewOrder adapter = new ForNewOrder(getActivity().getApplicationContext(), addResponseBodyOrders);
                                RecyclerView recyclerView = root.findViewById(R.id.attributes);
                                recyclerView.setAdapter(adapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                                next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        for (int j = 0; j < addResponseBodyOrders.size() - 1; j++) {
                                            View view = recyclerView.getChildAt(j);
                                            EditText attribute_from_user = view.findViewById(R.id.attribute_from_user);
                                            String value = attribute_from_user.getText().toString();
                                            String attribute_name = addResponseBodyOrders.get(j).getAttribute_name();
                                            AttributesFromUser attribute = new AttributesFromUser(order_id, attribute_name, value);
                                            ATTRIBUTES.add(attribute);
                                        }
                                        try {
                                            Retrofit retrofit = new Retrofit.Builder()
                                                    .baseUrl(JsonPlaceHolderApi.HOST)
                                                    .addConverterFactory(GsonConverterFactory.create())
                                                    .build();

                                            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                                            AllAttributesFromUser allAttributesFromUser = new AllAttributesFromUser(api_key, ATTRIBUTES);
                                            Call<Integer> call2 = jsonPlaceHolderApi.attributeAdd(allAttributesFromUser);
                                            call2.enqueue(new Callback<Integer>() {
                                                @Override
                                                public void onResponse(Call<Integer> call, Response<Integer> response) {

                                                }

                                                @Override
                                                public void onFailure(Call<Integer> call, Throwable t) {

                                                }
                                            });
                                        }catch (Exception e) {
                                            e.printStackTrace();
                                            Log.d("MyLog",  e.toString());
                                            Log.d("MyLog", "ОШИБКА: вывалилось в catch");
                                        }
                                        ATTRIBUTES.clear();
                                        if (i < info.size()-1) {
                                            i++;
                                            category_name.setText(info.get(i).getName());
                                            try {
                                                Retrofit retrofit = new Retrofit.Builder()
                                                        .baseUrl(JsonPlaceHolderApi.HOST)
                                                        .addConverterFactory(GsonConverterFactory.create())
                                                        .build();

                                                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                                                Step step = new Step(api_key, info.get(i).getNumber());
                                                Call<List<AddResponseBodyOrders>> call = jsonPlaceHolderApi.addStep(step);
                                                call.enqueue(new Callback<List<AddResponseBodyOrders>>() {
                                                    @Override
                                                    public void onResponse(Call<List<AddResponseBodyOrders>> call,
                                                                           Response<List<AddResponseBodyOrders>> response) {
                                                        addResponseBodyOrders.clear();
                                                        addResponseBodyOrders = response.body();
                                                        ForNewOrder adapter2 = new ForNewOrder(getActivity().getApplicationContext(), addResponseBodyOrders);
                                                        recyclerView.setAdapter(adapter2);
                                                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                                                    }

                                                    @Override
                                                    public void onFailure(Call<List<AddResponseBodyOrders>> call, Throwable t) {
                                                        Log.d("MyLog", t.toString());
                                                        Log.d("MyLog", "ОШИБКА: выход в onFailure");
                                                    }
                                                });
                                            }catch (Exception e) {
                                                e.printStackTrace();
                                                Log.d("MyLog",  e.toString());
                                                Log.d("MyLog", "ОШИБКА: вывалилось в catch");
                                            }
                                        }
                                        else {
                                            Toast.makeText(getActivity().getApplicationContext(), "Заказ успешно сформирован", Toast.LENGTH_LONG).show();
                                            returnToOrdersFragment();
                                        }
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<List<AddResponseBodyOrders>> call, Throwable t) {
                                Log.d("MyLog", t.toString());
                                Log.d("MyLog", "ОШИБКА: выход в onFailure");
                            }
                        });
                    }catch (Exception e) {
                        e.printStackTrace();
                        Log.d("MyLog",  e.toString());
                        Log.d("MyLog", "ОШИБКА: вывалилось в catch");
                    }
                }

                @Override
                public void onFailure(Call<List<OrderAddInfo>> call, Throwable t) {
                    Log.d("MyLog", t.toString());
                    Log.d("MyLog", "ОШИБКА: выход в onFailure");
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
            Log.d("MyLog",  e.toString());
            Log.d("MyLog", "ОШИБКА: вывалилось в catch");
        }
    }

    private void addOrderStep(int number) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JsonPlaceHolderApi.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
            Step step = new Step(api_key, number);
            Call<List<AddResponseBodyOrders>> call = jsonPlaceHolderApi.addStep(step);
            call.enqueue(new Callback<List<AddResponseBodyOrders>>() {
                @Override
                public void onResponse(Call<List<AddResponseBodyOrders>> call,
                                       Response<List<AddResponseBodyOrders>> response) {
                    addResponseBodyOrders = response.body();
                }

                @Override
                public void onFailure(Call<List<AddResponseBodyOrders>> call, Throwable t) {
                    Log.d("MyLog", t.toString());
                    Log.d("MyLog", "ОШИБКА: выход в onFailure");
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
            Log.d("MyLog",  e.toString());
            Log.d("MyLog", "ОШИБКА: вывалилось в catch");
        }
    }

    private void returnToOrdersFragment() {
        Fragment newFragment = new OrdersFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.commit();
    }
}
