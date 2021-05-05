package com.example.myapplication.Service;

import com.example.myapplication.Dtos.KullaniciPeyDto;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface KullaniciPeyService {
    @GET("getallbykullanici/{kullaniciId}")
    Call<ArrayList<KullaniciPeyDto>> getkullanicipey(@Path("kullaniciId") int kullaniciID);

    @GET("getsiparisListbykullanici/{kullaniciId}")
    Call<ArrayList<KullaniciPeyDto>> getsiparislist(@Path("kullaniciId") int kullaniciID);

    @POST("sonpeyupdate/{kid}/{murunid}")
    Call<KullaniciPeyDto> sonpeyupdate(@Path("kid") int KullaniciID, @Path("murunid") int murunid);

    @GET("getsonpey/{murunid}")
    Call<KullaniciPeyDto> getsonpey(@Path("murunid") int murunID);
}
