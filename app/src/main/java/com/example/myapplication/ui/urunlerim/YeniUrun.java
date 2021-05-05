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
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class YeniUrun extends AppCompatActivity {
    private EditText urunadi, uaciklamasi, ufiyat, uadet;
    private Button ekle;
    LocalService localService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urunekle);
        urunadi = findViewById(R.id.urunadi);
        uaciklamasi = findViewById(R.id.uaciklamasi);
        ufiyat = findViewById(R.id.ufiyat);
        uadet = findViewById(R.id.uadet);
        localService = new LocalService(getApplicationContext());
        final String id = localService.get("userId");
        ekle = findViewById(R.id.kaydet);
        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urunadi.getText().toString().equals("") || uaciklamasi.getText().toString().equals("") || ufiyat.getText().toString().equals("") || uadet.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Boş alanları doldurunuz", Toast.LENGTH_SHORT).show();
                }
                else {
                    final Urun urun = new Urun();
                    urun.setKullaniciID(Integer.parseInt(id));
                    urun.setUrunAdi(urunadi.getText().toString());
                    urun.setUrunAciklamasi(uaciklamasi.getText().toString());
                    urun.setUrunAdet(Integer.parseInt(uadet.getText().toString()));
                    urun.setUrunFiyat(Double.parseDouble(ufiyat.getText().toString()));
                    try {
                        Call<Urun> call = Retrofit.getUrunService().urunekle(urun);
                        call.enqueue(new Callback<Urun>() {
                            @Override
                            public void onResponse(@NotNull Call<Urun> call, @NotNull Response<Urun> response) {
                                if (!response.isSuccessful()) {
                                    Log.e("error", String.valueOf(response.code()));
                                } else {
                                    Log.i("urun", response.body().toString());
                                    Toast.makeText(getApplicationContext(), "Basarili", Toast.LENGTH_SHORT).show();
                                    Log.i("eklenenurunid", String.valueOf(response.body().getUrunID()));
                                }
                            }

                            @Override
                            public void onFailure(Call<Urun> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "hata", Toast.LENGTH_SHORT).show();

                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(v.getContext(), UrunlerimActivity.class);
                startActivity(intent);
            }
        });
    }
}
