package com.example.adamos_logistic.ui.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.adamos_logistic.ApiKey;
import com.example.adamos_logistic.Posts.JsonPlaceHolderApi;
import com.example.adamos_logistic.Posts.UserInfo;
import com.example.adamos_logistic.R;
import com.squareup.picasso.Picasso;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserFragment extends Fragment {

    private String api_key = "";
    SharedPreferences apiKey;
    private Context mContext;
    private UserInfo userInfo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_user, container, false);

        ImageView photo = root.findViewById(R.id.photo);
        TextView role = root.findViewById(R.id.role);
        TextView name = root.findViewById(R.id.name);
        TextView id = root.findViewById(R.id.id);

        mContext = getContext();
        apiKey = mContext.getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        if(apiKey.contains("api_key"))
            api_key = apiKey.getString("api_key", null);

        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JsonPlaceHolderApi.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
            ApiKey apiKey = new ApiKey(api_key);
            Call<UserInfo> call = jsonPlaceHolderApi.getUserInfo(apiKey);
            call.enqueue(new Callback<UserInfo>() {
                @Override
                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                    try {
                        userInfo = response.body();
                        role.setText("Роль: " + userInfo.getRole());
                        name.setText("Имя: " + userInfo.getName());
                        id.setText("ID: " + String.valueOf(userInfo.getId()));
                        Picasso.get().load(userInfo.getAvatar()).into(photo);
                    }catch (Exception e) {
                        Toast.makeText(getActivity().getApplicationContext(), "Ошибка", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<UserInfo> call, Throwable t) {
                    Log.d("MyLog", t.toString());
                    Log.d("MyLog", "Выход в onFailure");
                }
            });

        }catch(Exception e) {
            Log.d("MyLog", e.toString());
            Log.d("MyLog", "Выход в catch");
        }

        return root;
    }
}