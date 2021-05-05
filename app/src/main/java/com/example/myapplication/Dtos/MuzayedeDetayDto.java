package com.example.myapplication.Dtos;

import com.example.myapplication.Tablolar.Muzayede;
import com.example.myapplication.Tablolar.Urun;

import java.util.List;

public class MuzayedeDetayDto {
    public Muzayede muzayede;
    public List<MUrunDto> murunler;

    public Muzayede getMuzayede() {
        return muzayede;
    }

    public void setMuzayede(Muzayede muzayede) {
        this.muzayede = muzayede;
    }

    public List<MUrunDto> getUrunler() {
        return murunler;
    }

    public void setUrunler(List<MUrunDto> urunler) {
        this.murunler = urunler;
    }

}
