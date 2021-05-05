package com.example.myapplication.Dtos;

import androidx.annotation.NonNull;

import com.example.myapplication.Tablolar.Muzayede;
import com.example.myapplication.Tablolar.Urun;

public class KullaniciPeyDto {
    public int peyID;
    public Muzayede muzayede;
    public Urun urun;
    public double pey;
    public String peyZaman;
    public int kullaniciID;


    public int getPeyID() {
        return peyID;
    }

    public void setPeyID(int peyID) {
        peyID = peyID;
    }

    public Muzayede getMuzayede() {
        return muzayede;
    }

    public void setMuzayede(Muzayede muzayede) {
        this.muzayede = muzayede;
    }

    public Urun getUrun() {
        return urun;
    }

    public void setUrun(Urun urun) {
        this.urun = urun;
    }

    public double getPey() {
        return pey;
    }

    public void setPey(double pey) {
        pey = pey;
    }

    public String getPeyZaman() {
        return peyZaman;
    }

    public void setPeyZaman(String peyZaman) {
        this.peyZaman = peyZaman;
    }

    public int getKullaniciID() {
        return kullaniciID;
    }

    public void setKullaniciID(int kullaniciID) {
        this.kullaniciID = kullaniciID;
    }

    @NonNull
    @Override
    public String toString() {
        return "peyID=" + getPeyID() +";kullaniciID="+getKullaniciID()+";pey="+getPey();
    }
}
