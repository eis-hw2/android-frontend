package com.pipipan.demo.network;

import com.pipipan.demo.domain.Address;
import com.pipipan.demo.domain.Good;
import com.pipipan.demo.domain.Order;
import com.pipipan.demo.domain.Recipient;
import com.pipipan.demo.domain.Store;
import com.pipipan.demo.domain.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("testMongoFindUser")
    Call<Boolean> test(@Query("key") String test);

    // user
    @GET("User/{id}")
    Call<User> getUserById(@Path("id") long id);
    @POST("User")
    Call<User> logup(@Body User user);
    @POST("User/login")
    Call<User> login(@Body User user);

    // store
    @GET("Store")
    Call<List<Store>> getStore(@Query("mode") String mode, @Query("latitude") Double latitude, @Query("longitude") Double longitude);
    @GET("Store/{id}")
    Call<Store> getStoreById(@Path("id") long id);

    // order
    @GET("Order")
    Call<List<Order>> getOrder(@Query("mode")String mode, @Query("latitude") Double latitude, @Query("longitude")Double longitudem, @Query("userId")String userId);
    @GET("Order/{id}")
    Call<Order> getOrderById(@Path("id") long id);
    @POST("Order")
    Call<Order> createOrder(@Body Order order);
    @PUT("Order/{id}")
    Call<Order> modifyOrderById(@Path("id") long id, @Body Order order);

    // address
    @GET("RecipientAddress/{id}")
    Call<List<Address>> getAddressById(@Path("id") long id);
    @POST("RecipientAddress/user/{id}")
    Call<Recipient> addRecipient(@Path("id") long id, @Body Recipient recipient);
}
