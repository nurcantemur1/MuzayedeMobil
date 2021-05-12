package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Service.Retrofit;
import com.example.myapplication.Tablolar.Kullanici;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KayitActivity extends AppCompatActivity {
    private Button kaydet;
    EditText isim,soyisim,email,sifre,sifretekrar;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayitol);
        isim = findViewById(R.id.isim);
        soyisim = findViewById(R.id.soyisim);
        email = findViewById(R.id.email);
        sifre = findViewById(R.id.sifre);
        sifretekrar = findViewById(R.id.sifretekrar);
        kaydet = findViewById(R.id.kaydet);
        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid()){
                    final Kullanici kullanici = new Kullanici();
                    kullanici.setKullaniciAdi(isim.getText().toString());
                    kullanici.setKullaniciSoyadi(soyisim.getText().toString());
                    kullanici.setKullanicimail(email.getText().toString());
                    kullanici.setSifre(sifre.getText().toString());
                    kullanici.setKullaniciAdres("vkdfmvkl0");
                    kullanici.setKullaniciTelefon("51515515");
                    Call<Kullanici> call = Retrofit.getKullaniciService().kadd(kullanici);
                    call.enqueue(new Callback<Kullanici>() {
                                     @Override
                                     public void onResponse(Call<Kullanici> call, Response<Kullanici> response) {
                                         if(response.body() != null){
                                             Log.e("kullanici", kullanici.getKullaniciAdi());
                                             Intent intent = new Intent(getApplicationContext(),LognActivity.class);
                                             startActivity(intent);
                                         }
                                         else{
                                             Log.e("kullanici", String.valueOf(response.code()));
                                         }
                                     }

                                     @Override
                                     public void onFailure(Call<Kullanici> call, Throwable t) {
                                         Log.e("kullanici", "hata");
                                     }
                                 });
                }
            }
        });

    }
    public boolean isValid(){
        return true;
    }
}
