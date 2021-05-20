package com.example.myapplication.ui.murunleri;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Dtos.KullaniciPeyDto;
import com.example.myapplication.Dtos.MUrunDto;
import com.example.myapplication.Dtos.MuzayedeDetayDto;
import com.example.myapplication.Listeners.ThreadListener;
import com.example.myapplication.R;
import com.example.myapplication.Service.LocalService;
import com.example.myapplication.Service.Retrofit;
import com.example.myapplication.Tablolar.KartBilgileri;
import com.example.myapplication.Tablolar.Urun;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CanliActivity  extends AppCompatActivity {
    int mid;
    TextView muzayedeadi,urunadi,urunaciklamasi,urunadedi,urunfiyat,pey;
    MuzayedeDetayDto murunleriModel;
    ProgressBar progressBar;
    Button peyver;
    int counter = 0;
    int i=10;
    int sonpeyUserId=0;
    Thread thread;
    ThreadListener threadListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canli_fragment);
        muzayedeadi= findViewById(R.id.muzayedeadi);
        urunadi = findViewById(R.id.urunadi);
        urunaciklamasi = findViewById(R.id.urunaciklamasi);
        urunadedi = findViewById(R.id.urunadedi);
        urunfiyat = findViewById(R.id.urunfiyati);
        peyver = findViewById(R.id.peyver);
        pey = findViewById(R.id.pey);
        progressBar = findViewById(R.id.progressBar);
        mid=this.getIntent().getIntExtra("id",0);
        Log.e("MuzayedeId", String.valueOf(mid));
        getMUrunleri(mid);


        setThreadListener(new ThreadListener() {
            @Override
            public void cancel() {
                if(thread != null){
                    thread = null;
                    thread.stop();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void getMUrunleri(int id) {
        Call<MuzayedeDetayDto> call = Retrofit.getMurunleriService().getMurundetay(id);
        call.enqueue(new Callback<MuzayedeDetayDto>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<MuzayedeDetayDto> call, Response<MuzayedeDetayDto> response) {
                murunleriModel = response.body();
                muzayedeadi.setText(murunleriModel.getMuzayede().getMuzayedeAdi());
                updateurun();
            }
            @Override
            public void onFailure(Call<MuzayedeDetayDto> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @SuppressLint("SetTextI18n")
    public void updateurun(){
        Log.e("index",String.valueOf(i));
        final MUrunDto urunModel = murunleriModel.murunler.get(i);

        urunadi.setText(urunModel.getUrunAdi());
        urunaciklamasi.setText(urunModel.getUrunAciklamasi());
        urunadedi.setText(String.valueOf(urunModel.getUrunAdet()));
        urunfiyat.setText(String.valueOf(urunModel.getUrunFiyat()));
        Call<KullaniciPeyDto> call = Retrofit.getKullaniciPeyService().getsonpey(urunModel.getMurunid());
        call.enqueue(new Callback<KullaniciPeyDto>() {
            @Override
            public void onResponse(Call<KullaniciPeyDto> call, Response<KullaniciPeyDto> response) {
                if(response.isSuccessful()){
                    KullaniciPeyDto dto = response.body();
                    if( dto != null){
                        Log.e("Son Pey",dto.toString());
                        pey.setText(String.valueOf(dto.getPey()));
                        sonpeyUserId = dto.getKullaniciID();
                        Log.e("SonPeyUserId",String.valueOf(sonpeyUserId));
                    }
                }
                else{
                    sonpeyUserId = 0;
                    pey.setText("Verilen pey yok");
                    //Toast.makeText(getApplicationContext(), "Basari1111siz", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<KullaniciPeyDto> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Basari44siz", Toast.LENGTH_SHORT).show();
            }
        });

        peyver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int kid = Integer.parseInt(new LocalService(getApplicationContext()).get("userId"));
                if(kid <= 0){
                    Toast.makeText(getApplicationContext(), "Üye girişi yapınız", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.e("sonid",sonpeyUserId+"-"+kid);
                    if(sonpeyUserId == kid){
                        Toast.makeText(getApplicationContext(), "Üst üste pey veremezsiniz", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Log.e("Seçilen MurunId", String.valueOf(urunModel.getMurunid()));
                        Call<KullaniciPeyDto> call2 = Retrofit.getKullaniciPeyService().sonpeyupdate(kid,urunModel.getMurunid());
                        call2.enqueue(new Callback<KullaniciPeyDto>() {
                            @Override
                            public void onResponse(Call<KullaniciPeyDto> call, Response<KullaniciPeyDto> response) {
                                if(response.isSuccessful()){
                                    KullaniciPeyDto dto1 = response.body();
                                    if(dto1 != null){
                                        pey.setText(String.valueOf(dto1.getPey()));
                                        sonpeyUserId = dto1.getKullaniciID();
                                        counter= counter*50/100;
                                        Toast.makeText(getApplicationContext(), "Başarılı", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Yetersiz Bakiye", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<KullaniciPeyDto> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Basarisi2222z", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    }
                //  Toast.makeText(getApplicationContext(), sonpeyUserId+"-"+kid+"-"+urunModel.getMurunid(), Toast.LENGTH_SHORT).show();
            }
        });
        ProgressKontrol();
    }
    private void ProgressKontrol(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(100);
                        counter++;
                        progressBar.setProgress(counter);
                        if(counter==100){
                            counter = 0;
                            if( i== murunleriModel.murunler.size()-1){

                                Log.e("ikinciid", String.valueOf(i));
                                Intent intent = new Intent(getApplicationContext(),MurunleriActivity.class);
                                intent.putExtra("id",mid);
                                startActivity(intent);
                                threadListener.cancel();
                                break;
                            }
                            i++;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateurun();
                                }
                            });
                            break;
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    void setThreadListener(ThreadListener l){
        this.threadListener = l;
    }
}
