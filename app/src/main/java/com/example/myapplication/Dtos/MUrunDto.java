package com.example.myapplication.Dtos;

import androidx.annotation.NonNull;

import com.example.myapplication.Tablolar.Urun;

public class MUrunDto {
    private int urunID;
    private String urunAdi;
    private int urunAdet;
    private double urunFiyat;
    private String urunAciklamasi;
    private int kullaniciID;
    private int mUrunID;

    public int getMurunid() {
        return mUrunID;
    }

    public void setMurunid(int murunid) {
        this.mUrunID = murunid;
    }

    public int getKullaniciID() {
        return kullaniciID;
    }

    public void setKullaniciID(int kullaniciID) {
        this.kullaniciID = kullaniciID;
    }

    public int getUrunID() {
        return urunID;
    }

    public void setUrunID(int urunID) {
        this.urunID = urunID;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public int getUrunAdet() {
        return urunAdet;
    }

    public void setUrunAdet(int urunAdet) {
        this.urunAdet = urunAdet;
    }

    public double getUrunFiyat() {
        return urunFiyat;
    }

    public void setUrunFiyat(double urunFiyat) {
        this.urunFiyat = urunFiyat;
    }

    public String getUrunAciklamasi() {
        return urunAciklamasi;
    }

    public void setUrunAciklamasi(String urunAciklamasi) {
        this.urunAciklamasi = urunAciklamasi;
    }

    @NonNull
    @Override
    public String toString() {
        return "mUrunID="+getMurunid()+";urunID="+getUrunID();
    }
}
