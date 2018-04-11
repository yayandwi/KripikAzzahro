package com.yayan.kripikazzahro.api;

/**
 * Created by Robby Dianputra on 10/31/2017.
 */

import com.yayan.kripikazzahro.Model.city.ItemCity;
import com.yayan.kripikazzahro.Model.cost.ItemCost;
import com.yayan.kripikazzahro.Model.province.ItemProvince;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    // Province
    @GET("province")
    @Headers("key:a1db3093f82a112d84f38383f7119efb")
    Call<ItemProvince> getProvince ();

    // City
    @GET("city")
    @Headers("key:a1db3093f82a112d84f38383f7119efb")
    Call<ItemCity> getCity (@Query("province") String province);

    // Cost
    @FormUrlEncoded
    @POST("cost")
    Call<ItemCost> getCost(@Field("key") String Token,
                           @Field("origin") String origin,
                           @Field("destination") String destination,
                           @Field("weight") Integer weight,
                           @Field("courier") String courier);

}
