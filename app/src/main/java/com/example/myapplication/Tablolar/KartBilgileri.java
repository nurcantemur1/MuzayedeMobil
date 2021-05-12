package com.example.myapplication.Tablolar;

import androidx.annotation.Nullable;

public class KartBilgileri {
    public int id;
    public int kullaniciId;
    public String hesapNo;
    public String kartNo;
    public double bakiye;
    public boolean varsayilan;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(int kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public String getHesapNo() {
        return hesapNo;
    }

    public void setHesapNo(String hesapNo) {
        this.hesapNo = hesapNo;
    }

    public String getKartNo() {
        return kartNo;
    }

    public void setKartNo(String kartNo) {
        this.kartNo = kartNo;
    }

    public double getBakiye() {
        return bakiye;
    }

    public void setBakiye(double bakiye) {
        this.bakiye = bakiye;
    }

    public boolean isVarsayilan() { return varsayilan; }

    public void setVarsayilan(boolean varsayilan) { this.varsayilan = varsayilan;}
}
