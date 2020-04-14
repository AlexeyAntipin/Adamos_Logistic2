package com.example.adamos_logistic;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adamos_logistic.Posts.GetResponseBodyOrders;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FullInformationFragment extends Fragment {

    private View root;
    private Context mContext;
    private List<GetResponseBodyOrders> orders;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_orders, container, false);
        this.mContext = getContext();
        loadData();

        return root;
    }


    public void loadData() {
        SharedPreferences Settings = mContext.getSharedPreferences("orders", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = Settings.getString("order", null);
        Type type = new TypeToken<ArrayList<GetResponseBodyOrders>>() {}.getType();
        orders = gson.fromJson(json, type);

        if (orders == null) {
            orders = new ArrayList<>();
        }
    }
}
