package com.pipipan.demo.network;

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

}
