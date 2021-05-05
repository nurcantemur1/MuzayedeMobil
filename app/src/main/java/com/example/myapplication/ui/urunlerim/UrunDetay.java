package com.example.myapplication.ui.urunlerim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.Service.LocalService;
import com.example.myapplication.Service.Retrofit;
import com.example.myapplication.Tablolar.Urun;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UrunDetay extends AppCompatActivity {
    private EditText urunadi,uaciklamasi,ufiyat,uadet;
    private Button kaydet;
    Urun urunlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urundetay);
        urunadi = findViewById(R.id.uadi);
        uaciklamasi = findViewById(R.id.uaciklamasi);
        ufiyat = findViewById(R.id.ufiyat);
        uadet = findViewById(R.id.uadet);


        int urunid=this.getIntent().getIntExtra("uurunid",0);
        Log.e("gelenurunid", String.valueOf(urunid));
        geturun(urunid);
        kaydet = findViewById(R.id.kaydet);
        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urunlist.setUrunFiyat(Double.parseDouble(String.valueOf(ufiyat.getText())));
                urunlist.setUrunAdet(Integer.parseInt(uadet.getText().toString()));
                urunlist.setUrunAdi(urunadi.getText().toString());
                urunlist.setUrunAciklamasi(uaciklamasi.getText().toString());
                Call<Urun> call = Retrofit.getUrunService().uupdate(urunlist);
                call.enqueue(new Callback<Urun>() {
                    @Override
                    public void onResponse(Call<Urun> call, Response<Urun> response) {

                        Toast.makeText(getApplicationContext(), "Basarili", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),UrunlerimActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Urun> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    public void geturun(int urunid){
        Call<Urun> call = Retrofit.getUrunService().geturun(urunid);
        call.enqueue(new Callback<Urun>() {
            @Override
            public void onResponse(Call<Urun> call, Response<Urun> response) {
                urunlist = response.body();
                urunadi.setText(urunlist.getUrunAdi());
                uaciklamasi.setText(urunlist.getUrunAciklamasi());
                ufiyat.setText(String.valueOf(urunlist.getUrunFiyat()));
                uadet.setText(String.valueOf(urunlist.getUrunAdet()));
            }

            @Override
            public void onFailure(Call<Urun> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
