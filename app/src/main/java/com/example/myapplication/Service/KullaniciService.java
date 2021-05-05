package com.example.myapplication.Service;

import com.example.myapplication.Tablolar.Kullanici;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface KullaniciService {

    @GET("giriskontrol/{mail}/{sifre}")
    Call<Kullanici> getlogin(@Path("mail") String kullanicimail, @Path("sifre") String sifre);

    @GET("get/{id}")
    Call<Kullanici> getkullanici(@Path("id") int kullaniciID);

    @POST("update")
    Call<Kullanici> kupdate(@Body Kullanici kullanici);

    @POST("add")
    Call<Kullanici> kadd(@Body Kullanici kullanici);
}
