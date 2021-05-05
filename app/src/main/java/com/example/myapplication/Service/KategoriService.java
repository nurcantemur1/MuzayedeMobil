package com.example.myapplication.Service;

import com.example.myapplication.Tablolar.Kategori;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface KategoriService {
    @GET("getall")
    Call<ArrayList<Kategori>> getkategori();

}
