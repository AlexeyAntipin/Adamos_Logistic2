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
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.adamos_logistic.Posts.AddResponseBodyOrders;
import com.example.adamos_logistic.Posts.JsonPlaceHolderApi;
import com.example.adamos_logistic.Posts.PostAddOrderData;
import com.example.adamos_logistic.R;

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

    private TextView statusTextView, resultTextView;
    private EditText orderNameView;
    private Button returnBackButton, addOrderButton;
    private ProgressBar progressBar;

    public AddOrderFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_order, container, false);

        mContext = getContext();

        orderNameView = root.findViewById(R.id.order_name_editText);

        statusTextView = root.findViewById(R.id.progress_textView);
        statusTextView.setVisibility(View.INVISIBLE);
        resultTextView = root.findViewById(R.id.result_textView);
        resultTextView.setVisibility(View.INVISIBLE);

        returnBackButton = root.findViewById(R.id.return_button);
        addOrderButton = root.findViewById(R.id.add_order_button);

        progressBar = root.findViewById(R.id.status_progressBar);
        progressBar.setVisibility(View.INVISIBLE);


        returnBackButton.setOnClickListener(v -> returnToOrdersFragment());

        addOrderButton.setOnClickListener(v -> {
            String orderName = orderNameView.getText().toString();

            if (!orderName.isEmpty()) {
                hideResultTextView();
                showAddingInProgressMessage();
                tryAddNewOrder(orderName, root);
            } else {
                showNoOrderNameMessage();
            }

        });

        return root;
    }


    private void tryAddNewOrder(String orderName, View root) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JsonPlaceHolderApi.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            apiKey = mContext.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
            if(apiKey.contains("api_key"))
                api_key = apiKey.getString("api_key", null);
            PostAddOrderData addOrderData =
                    new PostAddOrderData(api_key, 1);

            Call<List<AddResponseBodyOrders>> call2 = jsonPlaceHolderApi.addOrder(addOrderData);

            call2.enqueue(new Callback<List<AddResponseBodyOrders>>() {
                @Override
                public void onResponse(@NonNull Call<List<AddResponseBodyOrders>> call2,
                                       @NonNull Response<List<AddResponseBodyOrders>> response) {

                    List<AddResponseBodyOrders> order = response.body();

                    AddResponseBodyOrders[] myArray = new AddResponseBodyOrders[order.size()];
                    order.toArray(myArray);

                    for(int i=0; i<myArray.length; i++){
                        System.out.println(myArray[i].getAttribute_name() + " " +myArray[i].getAttribute_description() + " " +myArray[i].getAttribute_type());
                    }
                    Log.d("MyLog", "success");

                    hideAddingInProgressMessage();
                    showSuccessfulAddingMessage();
                    orderNameView.setText("");
                }

                @Override
                public void onFailure(@NonNull Call<List<AddResponseBodyOrders>> call2,
                                      @NonNull Throwable t) {

                    Log.d("MyLog", t.toString());
                    Log.d("MyLog", "ОШИБКА: выход в onFailure");

                    hideAddingInProgressMessage();
                    showFailedAddingMessage();
                }
            });

        } catch (Exception e) {

            e.printStackTrace();
            Log.d("MyLog",  e.toString());
            Log.d("MyLog", "ОШИБКА: вывалилось в catch");
            hideAddingInProgressMessage();
            showFailedAddingMessage();
        }
    }


    private void showNoOrderNameMessage() {
        resultTextView.setVisibility(View.VISIBLE);
        resultTextView.setText(R.string.status_no_order_name);
    }

    private void hideResultTextView() {
        resultTextView.setVisibility(View.INVISIBLE);
    }

    private void showAddingInProgressMessage() {
        progressBar.setVisibility(View.VISIBLE);
        statusTextView.setVisibility(View.VISIBLE);
    }

    private void hideAddingInProgressMessage() {
        progressBar.setVisibility(View.INVISIBLE);
        statusTextView.setVisibility(View.INVISIBLE);
    }

    private void showSuccessfulAddingMessage() {
        resultTextView.setText(R.string.status_adding_success);
        resultTextView.setVisibility(View.VISIBLE);
    }

    private void showFailedAddingMessage() {
        resultTextView.setText(R.string.status_adding_failed);
        resultTextView.setVisibility(View.VISIBLE);
    }

    private void returnToOrdersFragment() {
        Fragment newFragment = new OrdersFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, newFragment);
        transaction.commit();
    }
}
