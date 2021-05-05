package com.example.myapplication.Tablolar;

public class Kategori {
    private int kategoriID;
    private boolean kategoriDurum;
    private String kategoriAdi;

    public boolean isKategoriDurum() {
        return kategoriDurum;
    }

    public void setKategoriDurum(boolean kategoriDurum) {
        this.kategoriDurum = kategoriDurum;
    }

    public int getKategoriID() {
        return kategoriID;
    }

    public void setKategoriID(int kategoriID) {
        this.kategoriID = kategoriID;
    }

    public String getKategoriAdi() {
        return kategoriAdi;
    }

    public void setKategoriAdi(String kategoriAdi) {
        this.kategoriAdi = kategoriAdi;
    }


}
