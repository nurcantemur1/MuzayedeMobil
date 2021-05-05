package com.example.myapplication.Service;

import com.example.myapplication.Tablolar.Muzayede;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MuzayedeService {

    @GET("getall")
    Call<ArrayList<Muzayede>> mgetall();

    @GET("kmgetall/{kullaniciId}")
    Call<ArrayList<Muzayede>> kmgetall(@Path("kullaniciId") int kullaniciID);

    @POST("addmuzayede")
    Call<Muzayede> muzayedeAdd(@Body Muzayede muzayede);

    @POST("update")
    Call<Muzayede> muzayedeUpdate(@Body Muzayede muzayede);

    @DELETE("delete/{id}")
    Call<Boolean> msil(@Path("id") int muzayedeID);
}
