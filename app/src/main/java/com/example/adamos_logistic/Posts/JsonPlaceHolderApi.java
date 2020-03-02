package com.example.adamos_logistic.Posts;

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

    @POST("adamos/hs/MAPI/login")
    Call<Post> createPostChat(@Body PostChat postChat);

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
}