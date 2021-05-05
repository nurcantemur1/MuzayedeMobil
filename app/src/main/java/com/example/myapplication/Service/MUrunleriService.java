package com.example.myapplication.Service;

import com.example.myapplication.Dtos.MuzayedeDetayDto;
import com.example.myapplication.Tablolar.MuzayedeUrunleri;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MUrunleriService {
    @POST("add")
    Call<MuzayedeUrunleri> MurunuAdd(@Body MuzayedeUrunleri muzayedeUrunleri);

    @GET("getmuzayededetay/{id}")
    Call<MuzayedeDetayDto> getMurundetay(@Path("id") int muzayedeID);

    @GET("GetMurunId/{id}/{mid}")
    Call<Integer> GetMurunId(@Path("id") int urunID,@Path("mid") int muzayedeID);

    @DELETE("delete/{id}")
    Call<Boolean> murunsil(@Path("id") int ID);

}
