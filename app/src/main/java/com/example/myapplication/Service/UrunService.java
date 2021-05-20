package com.example.myapplication.Service;

import com.example.myapplication.Model.UrunResimModel;
import com.example.myapplication.Tablolar.Urun;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UrunService {
    @GET("getall")
    Call<ArrayList<Urun>> geturunler();

    @POST("add")
    Call<Urun> urunekle(@Body Urun urun);
    @Multipart
    @POST("addurun")
    Call<String> urunresimekle(@Part UrunResimModel urunResimModel);

    @GET("getlist/{kid}")
    Call<ArrayList<Urun>> geturunum(@Path("kid") int kullaniciID);

    @DELETE("delete/{id}")
    Call<Boolean> urunsil(@Path("id") int urunID);

    @GET("get/{id}")
    Call<Urun> geturun(@Path("id") int urunID);

    @GET("getallbykullanicimuzayedesiz/{kid}")
    Call<ArrayList<Urun>> getmuzayedesizurunler(@Path("kid") int kullaniciID);

    @POST("update")
    Call<Urun> uupdate(@Body Urun urun);

}
