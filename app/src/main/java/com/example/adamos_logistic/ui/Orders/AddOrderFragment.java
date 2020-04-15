package com.example.adamos_logistic.ui.Orders;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.adamos_logistic.Posts.AddResponseBodyOrders;
import com.example.adamos_logistic.Posts.JsonPlaceHolderApi;
import com.example.adamos_logistic.Posts.PostAddOrderData;
import com.example.adamos_logistic.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddOrderFragment extends Fragment {

    private EditText orderNameView;
    private Button returnBackButton, addOrderButton;

    public AddOrderFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_order, container, false);

        orderNameView = root.findViewById(R.id.order_name_editText);
        returnBackButton = root.findViewById(R.id.return_button);
        addOrderButton = root.findViewById(R.id.add_order_button);

        returnBackButton.setOnClickListener(v -> returnToOrdersFragment());

        addOrderButton.setOnClickListener(v -> {
            String orderName = orderNameView.getText().toString();
            addNewOrder(orderName);
        });

        return root;
    }


    private void addNewOrder(String orderName) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JsonPlaceHolderApi.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            //TODO: ВАНЯ: излвлечение api_key из SharedPreferences
            PostAddOrderData addOrderData =
                    new PostAddOrderData("1ff0c335ba8b4b4057928e3796a07222",
                            orderName);

            Call<AddResponseBodyOrders> call2 = jsonPlaceHolderApi.addOrder(addOrderData);

            call2.enqueue(new Callback<AddResponseBodyOrders>() {
                @Override
                public void onResponse(@NonNull Call<AddResponseBodyOrders> call2,
                                       @NonNull Response<AddResponseBodyOrders> response) {

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

    private void returnToOrdersFragment() {
        Fragment newFragment = new OrdersFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.commit();
    }
}
