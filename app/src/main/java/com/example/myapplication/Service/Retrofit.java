package com.example.myapplication.Service;

import com.example.myapplication.Tablolar.Kategori;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {
    private static KullaniciPeyService kullaniciPeyService = null;
    private static String BASE_URL1="http://192.168.1.107:81/api/kullanicipey/";
    public static KullaniciPeyService getKullaniciPeyService () {
        if ( kullaniciPeyService == null) {
            retrofit2.Retrofit retrofit = new retrofit2.Retrofit
                    .Builder()
                    .baseUrl(BASE_URL1)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            kullaniciPeyService = retrofit.create(KullaniciPeyService.class);

        }
        return kullaniciPeyService;
    }
    private static KullaniciService kullaniciService = null;
    private static String BASE_URL2="http://192.168.1.107:81/api/kullanici/";
    public static KullaniciService getKullaniciService () {
        if ( kullaniciService == null) {
            retrofit2.Retrofit retrofit = new retrofit2.Retrofit
                    .Builder()
                    .baseUrl(BASE_URL2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            kullaniciService = retrofit.create(KullaniciService.class);

        }
        return kullaniciService;
    }
    private static UrunService urunService = null;
    private static String BASE_URL3="http://192.168.1.107:81/api/urun/";
    public static UrunService getUrunService () {
        if ( urunService == null) {
            retrofit2.Retrofit retrofit = new retrofit2.Retrofit
                    .Builder()
                    .baseUrl(BASE_URL3)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            urunService = retrofit.create(UrunService.class);

        }
        return urunService;
    }
    private static KategoriService kategoriService = null;
    private static String BASE_URL4="http://192.168.1.107:81/api/kategori/";
    public static KategoriService getkategoriService () {
        if ( kategoriService == null) {
            retrofit2.Retrofit retrofit = new retrofit2.Retrofit
                    .Builder()
                    .baseUrl(BASE_URL4)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            kategoriService = retrofit.create(KategoriService.class);

        }
        return kategoriService;
    }
    private static MuzayedeService muzayedeService = null;
    private static String BASE_URL5="http://192.168.1.107:81/api/muzayede/";
    public static MuzayedeService getMuzayedeService () {
        if ( muzayedeService == null) {
            retrofit2.Retrofit retrofit = new retrofit2.Retrofit
                    .Builder()
                    .baseUrl(BASE_URL5)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            muzayedeService = retrofit.create(MuzayedeService.class);

        }
        return muzayedeService;
    }
    private static MUrunleriService murunleriService = null;
    private static String BASE_URL6="http://192.168.1.107:81/api/murunleri/";
    public static MUrunleriService getMurunleriService () {
        if ( murunleriService == null) {
            retrofit2.Retrofit retrofit = new retrofit2.Retrofit
                    .Builder()
                    .baseUrl(BASE_URL6)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            murunleriService = retrofit.create(MUrunleriService.class);

        }
        return murunleriService;
    }
}
