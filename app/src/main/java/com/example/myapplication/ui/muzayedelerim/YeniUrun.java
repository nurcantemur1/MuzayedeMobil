package com.example.myapplication.ui.muzayedelerim;


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
import com.example.myapplication.Tablolar.MuzayedeUrunleri;
import com.example.myapplication.Tablolar.Urun;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YeniUrun extends AppCompatActivity {
    private EditText urunadi, uaciklamasi, ufiyat, uadet;
    private Button ekle;
    LocalService localService;
    int mid;
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
        mid = this.getIntent().getIntExtra("mid",0);
        Log.e("gelenmid", String.valueOf(mid));
        Log.e("id", id);
        Log.e("midolduuu1", String.valueOf(mid));
        ekle = findViewById(R.id.kaydet);
        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urunadi.getText().toString().equals("") || uaciklamasi.getText().toString().equals("") || ufiyat.getText().toString().equals("") || uadet.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Boş alanları doldurunuz", Toast.LENGTH_SHORT).show();
                }
                //önce ürüne sonra m ürünlere ekleme yapılacak
                    else {
                    Urun urun = new Urun();
                    urun.setKullaniciID(Integer.parseInt(id));
                    urun.setUrunAdi(urunadi.getText().toString());
                    urun.setUrunAciklamasi(uaciklamasi.getText().toString());
                    urun.setUrunAdet(Integer.parseInt(uadet.getText().toString()));
                    urun.setUrunFiyat(Integer.parseInt(ufiyat.getText().toString()));
                    Call<Urun> call = Retrofit.getUrunService().urunekle(urun);
                    call.enqueue(new Callback<Urun>() {
                        @Override
                        public void onResponse(Call<Urun> call, Response<Urun> response) {
                            if(!response.isSuccessful()){
                                Log.e("error", String.valueOf(response.code()));
                            }
                            else {
                                int urunid = response.body().getUrunID();
                                MuzayedeUrunleri murun = new MuzayedeUrunleri();
                                murun.setUrunID(urunid);
                                murun.setMuzayedeID(mid);
                                Call<MuzayedeUrunleri> call2 = Retrofit.getMurunleriService().MurunuAdd(murun);
                                call2.enqueue(new Callback<MuzayedeUrunleri>() {
                                    @Override
                                    public void onResponse(@NotNull Call<MuzayedeUrunleri> call, @NotNull Response<MuzayedeUrunleri> response) {
                                        if (!response.isSuccessful()) {
                                            Log.e("error", String.valueOf(response.code()));
                                        } else {
                                            MuzayedeUrunleri muzayed = response.body();
                                            Log.i("urunid", String.valueOf(muzayed.getUrunID()));
                                            Toast.makeText(getApplicationContext(), "Basarili", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), MuzayedeDetay.class);
                                            intent.putExtra("mid",muzayed.getMuzayedeID());
                                            Log.e("midolduuu2", String.valueOf(mid));
                                            startActivity(intent);
                                        }
                                    }
                                    @Override
                                    public void onFailure(Call<MuzayedeUrunleri> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "hata", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        @Override
                        public void onFailure(Call<Urun> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "hata2", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
