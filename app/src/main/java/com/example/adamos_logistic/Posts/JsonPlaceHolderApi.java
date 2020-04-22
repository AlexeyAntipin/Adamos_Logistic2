package com.example.adamos_logistic.Posts;


import com.example.adamos_logistic.ApiKey;
import com.example.adamos_logistic.GetResponseBodyOrders;
import com.example.adamos_logistic.TestQueryPackage.TestQueryString;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    String HOST = "http://25.99.235.231/";

    // Тестовый GET запрос
    @GET("adamos2/php/process.php?command=test")
    Call<TestQueryString> getTestQuery();

    // POST-запрос для отправки сообщения в чат
    @POST("adamos2/php/process.php?command=new_message")
    Call<PostChat> createPostChat(@Body PostChat postChat);

    // Неразобранные запросы

    @GET("adamos/hs/MAPI/newUser")
    Call<List<Post>> getPosts(
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order
    );

    @GET("adamos/hs/MAPI/newUser")
    Call<List<Post>> getPosts();

    @POST("adamos/hs/MAPI/newUser")
    Call<Post> createPost(@Body Post post);

    @POST("adamos/hs/MAPI/login")
    Call<Post> createPostLogin(@Body PostLogin postLogin);


    @FormUrlEncoded
    @POST("adamos/hs/MAPI/newUser")
    Call<Post> createPost(
            @Field("NAME") String NAME,
            @Field("SECONDNAME") String SECONDNAME,
            @Field("SURNAME") String SURNAME,
            @Field("PASSWORD") String PASSWORD,
            @Field("EMAIL") String EMAIL
    );

    @FormUrlEncoded
    @POST("adamos/hs/MAPI/login")
    Call<Post> createPostLogin(
            @Field("PASSWORD") String PASSWORD,
            @Field("EMAIL") String EMAIL
    );

    @FormUrlEncoded
    @POST("adamos/hs/MAPI/login")
    Call<Post> createPostChat(
            @Field("ID") String ID,
            @Field("MESSAGE") String MESSAGE
    );

    @FormUrlEncoded
    @POST("adamos/hs/MAPI/newUser")
    Call<Post> createPost(@FieldMap Map<String, String> fields);


    @POST("process.php?route=order/get")
    Call<List<GetResponseBodyOrders>> getOrders(
            @Body ApiKey api_key
    );

    @POST("process.php?route=message/get")
    Call<List<GetMessages>> getMessages(
            @Body Order_id order_id
    );


    @POST("process.php?route=order/add")
    Call<List<AddResponseBodyOrders>> addOrder(
            @Body PostAddOrderData addOrderData
    );

    @POST("process.php?route=user/add")
    Call<AddUser> addUser(
            @Body PostRegisterData registerData
    );

    @POST("process.php?route=user/login")
    Call<ResponseLogin> checkUser(
            @Body PostLoginData loginData
    );

    @POST("process.php?route=message/add")
    Call<ResponseNewMessage> addMessage(
            @Body PostAddMessage messageData
    );

    @POST("process.php?route=user/info")
    Call<UserInfo> getUserInfo (
            @Body ApiKey api_key
    );
}