package com.example.myapplication.Service;

import com.example.myapplication.Tablolar.KartBilgileri;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface KartBService {

    @GET("getallbykullanici/{kullaniciId}")
    Call<List<KartBilgileri>> getallbykullanici(@Path("kullaniciId") int kullaniciId);

    @POST("add")
    Call<KartBilgileri> kartEkle(@Body KartBilgileri kartBilgileri);

    @DELETE("delete/{id}")
    Call<Boolean> kartSil(@Path("id") int id);

    @POST("varsayilanyap/{id}")
    Call<KartBilgileri> varsayilanYap(@Path("id") int id);

    @GET("getVarsayilanKart/{KullaniciId}")
    Call<KartBilgileri> getVarsayilanKart(@Path("KullaniciId") int kullaniciId);
}
