package com.example.myapplication.ui.muzayedelerim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Listeners.IListItemSelectedListener;
import com.example.myapplication.R;
import com.example.myapplication.Service.LocalService;
import com.example.myapplication.Service.Retrofit;
import com.example.myapplication.Tablolar.MuzayedeUrunleri;
import com.example.myapplication.Tablolar.Urun;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MUrunEkleActivity extends AppCompatActivity {
    MUrunEkleViewModel mUrunEkleViewModel;
    ArrayList<Urun> murunleriModelArrayList;
    RecyclerView listview;
    Button yeniurn,mekle;
    int mid;
    Boolean[] checklist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muekle);
        listview = findViewById(R.id.checklist);
        mid=this.getIntent().getIntExtra("mid",0);
        Log.e("geldimid", String.valueOf(mid));
        yeniurn = findViewById(R.id.yeniurn);
        yeniurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), YeniUrun.class);
                intent.putExtra("mid",mid);
                startActivity(intent);
            }
        });
        mekle = findViewById(R.id.mekle);
        mekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i =0; i< checklist.length;i++){
                    if(checklist[i]){
                        MuzayedeUrunleri murun = new MuzayedeUrunleri();
                        murun.setUrunID(murunleriModelArrayList.get(i).getUrunID());
                        murun.setMuzayedeID(mid);
                        Call<MuzayedeUrunleri> call2 = Retrofit.getMurunleriService().MurunuAdd(murun);
                        call2.enqueue(new Callback<MuzayedeUrunleri>() {
                            @Override
                            public void onResponse(@NotNull Call<MuzayedeUrunleri> call, @NotNull Response<MuzayedeUrunleri> response) {
                                if (!response.isSuccessful()) {
                                    Log.e("error", String.valueOf(response.code()));
                                }
                                else {
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
            }
        });
        LocalService localService = new LocalService(this.getApplicationContext());
        String id = localService.get("userId");
        getmuzayedesizurunler(Integer.parseInt(id));
    }
    private void getmuzayedesizurunler(int id) {
         Call<ArrayList<Urun>> call = Retrofit.getUrunService().getmuzayedesizurunler(id);
         call.enqueue(new Callback<ArrayList<Urun>>() {
             @Override
             public void onResponse(Call<ArrayList<Urun>>call, Response<ArrayList<Urun>> response) {

                 Log.e("mesaj",response.body().toString());
                 murunleriModelArrayList = response.body();
                 initpost(murunleriModelArrayList);
             }

             @Override
             public void onFailure(Call<ArrayList<Urun>> call, Throwable t) {
                 Toast.makeText(getApplicationContext(), "hata", Toast.LENGTH_SHORT).show();
             }
         });

    }
    public void initpost(ArrayList<Urun> murunleriModelArrayList){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        listview.setLayoutManager(linearLayoutManager);
        mUrunEkleViewModel = new MUrunEkleViewModel(getApplicationContext(), murunleriModelArrayList);
        checklist = new Boolean[murunleriModelArrayList.size()];
        for(int i=0;i<checklist.length;i++) checklist[i] = false;
        mUrunEkleViewModel.setListItemSelectedListener(new IListItemSelectedListener() {
            @Override
            public void onClick(int position, View v) {
                if(checklist[position]) checklist[position] = false;
                else checklist[position] = true;
            }
        });
        listview.setHasFixedSize(true);
        listview.setAdapter(mUrunEkleViewModel);
    }
}