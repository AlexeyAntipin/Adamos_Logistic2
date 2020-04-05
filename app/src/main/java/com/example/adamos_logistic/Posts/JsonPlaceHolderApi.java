package com.example.adamos_logistic.Posts;

import com.example.adamos_logistic.Message;
import com.example.adamos_logistic.TestQueryPackage.TestQueryString;

import java.util.ArrayList;
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

    String HOST = "http://192.168.43.202/";


    // Тестовый GET запрос
    @GET("adamos2/php/process.php?command=test")
    Call<TestQueryString> getTestQuery();

    // POST-запрос для отправки сообщения в чат
    @POST("adamos2/php/process.php?command=new_message")
    Call<PostChat> createPostChat(@Body PostChat postChat);

    @GET("")
    Call<ArrayList<Message>> getMessages(@Body Message message);



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
    Call<Post> createPostLogin(@Body PostLogin postLogim);


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

    @GET("adamos/hs/MAPI/getOrders")
    Call<GetOrders> forOneOrder();
}