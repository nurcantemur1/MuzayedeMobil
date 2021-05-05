package com.example.myapplication.ui.muzayedelerim;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Dtos.MUrunDto;
import com.example.myapplication.Dtos.MuzayedeDetayDto;
import com.example.myapplication.Listeners.IListItemSelectedListener;
import com.example.myapplication.R;
import com.example.myapplication.Service.LocalService;
import com.example.myapplication.Service.Retrofit;
import com.example.myapplication.Tablolar.Muzayede;
import com.example.myapplication.Tablolar.MuzayedeUrunleri;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MuzayedeDetay extends AppCompatActivity {
    TextView mesaj;
    Button kaydet,yeniurun;
    RecyclerView murunlerlist;
    MuzayedeDetayModel mDetayViewModel;
    int mid;
    private MuzayedeDetayDto murunleriModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muzayededetay);
        murunlerlist = findViewById(R.id.murunlerlist);
        mesaj = findViewById(R.id.mesaj);
        mid=this.getIntent().getIntExtra("mid",0);
        Log.e("geldimid", String.valueOf(mid));
        getMUrunleri(mid);
        kaydet = findViewById(R.id.kaydet);
        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MuzayedelerimActivity.class);
                startActivity(intent);
            }
        });
        yeniurun = findViewById(R.id.yeniurun);
        yeniurun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MUrunEkleActivity.class);
                intent.putExtra("mid",mid);
                startActivity(intent);
            }
        });
    }

    private void getMUrunleri(int mid) {
        Call<MuzayedeDetayDto> call = Retrofit.getMurunleriService().getMurundetay(mid);
        call.enqueue(new Callback<MuzayedeDetayDto>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<MuzayedeDetayDto> call, Response<MuzayedeDetayDto> response) {
                if (!response.isSuccessful()) {
                    Log.e("error", String.valueOf(response.code()));
                }
                else {
                    Log.e("mesaj", response.body().toString());
                    murunleriModelList = response.body();

                    if(murunleriModelList.getUrunler().size() == 0){
                        mesaj.setText("Müzayedene ürün ekle");
                    }
                    else{
                        initpost(murunleriModelList);
                    }
                }
            }

            @Override
            public void onFailure(Call<MuzayedeDetayDto> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initpost(final MuzayedeDetayDto murunleriModelList){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        murunlerlist.setLayoutManager(linearLayoutManager);
        mDetayViewModel = new MuzayedeDetayModel(getApplicationContext(), murunleriModelList);
        mDetayViewModel.setListItemSelectedListener(new IListItemSelectedListener() {
           @Override
           public void onClick(int position, View v) {
               MuzayedeDetayDto murunleri = murunleriModelList;
               if(murunleri != null){
                   Log.e("silinecek", String.valueOf(murunleri.getUrunler().get(position).getMurunid()));
                   Button btn = (Button) v;
                   if(btn.getId() == R.id.sil){
                       Call<Boolean> call = Retrofit.getMurunleriService().murunsil(murunleri.getUrunler().get(position).getMurunid());
                       call.enqueue(new Callback<Boolean>() {
                           @Override
                           public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                               if (!response.isSuccessful()) {
                                   Log.e("error", String.valueOf(response.code()));
                               } else {
                                   Toast.makeText(getApplicationContext(), "Silindi", Toast.LENGTH_SHORT).show();
                                   Intent intent = new Intent(getApplicationContext(), MuzayedeDetay.class);
                                   intent.putExtra("mid",mid);
                                   startActivity(intent);
                               }
                           }

                           @Override
                           public void onFailure(Call<Boolean> call, Throwable t) {
                               Toast.makeText(getApplicationContext(), "Başarısız", Toast.LENGTH_SHORT).show();
                           }
                       });
                   }
               }
           }
       });
        murunlerlist.setHasFixedSize(true);
        murunlerlist.setAdapter(mDetayViewModel);
    }
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MuzayedelerimActivity.class);
        startActivity(intent);
    }
}
