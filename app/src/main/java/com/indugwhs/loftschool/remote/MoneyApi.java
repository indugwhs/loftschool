package com.indugwhs.loftschool.remote;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MoneyApi {
    @GET("./items")
    Single<List<MoneyRemoteItem>> getMoneyItems(@Query("type") String type, @Query("auth-token") String authToken);

   @POST("./items/add")
    @FormUrlEncoded
   Completable postMoney(@Field("price") int price, @Field("name") String name,
                         @Field("type") String type, @Field("auth-token") String authToken);

    @POST("items/remove")
    Single<AuthResponse> removeItem (@Query("id") String id, @Query("auth-token") String authToken);
}
