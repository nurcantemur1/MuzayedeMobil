package com.example.myapplication.Tablolar;

public class Muzayede  {

    private int muzayedeID;
    private String muzayedeAdi;
    //private String mTarih;
    private int kullaniciID;
    private int izlenme;
    private Sure sure;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Sure getSure() {
        return sure;
    }

    public void setSure(Sure sure) {
        this.sure = sure;
    }

    public int getMuzayedeID() {
        return muzayedeID;
    }

    public void setMuzayedeID(int muzayedeID) {
        this.muzayedeID = muzayedeID;
    }

    public String getMuzayedeAdi() {
        return muzayedeAdi;
    }

    public void setMuzayedeAdi(String muzayedeAdi) {
        this.muzayedeAdi = muzayedeAdi;
    }


    public int getKullaniciID() {
        return kullaniciID;
    }

    public void setKullaniciID(int kullaniciID) {
        this.kullaniciID = kullaniciID;
    }

    public int getIzlenme() {
        return izlenme;
    }

    public void setIzlenme(int izlenme) {
        this.izlenme = izlenme;
    }
}
