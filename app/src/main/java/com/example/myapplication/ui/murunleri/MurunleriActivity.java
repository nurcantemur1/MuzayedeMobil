package com.example.myapplication.ui.murunleri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Dtos.MuzayedeDetayDto;
import com.example.myapplication.Listeners.IListItemSelectedListener;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Service.Retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MurunleriActivity extends AppCompatActivity {
    int id;
    RecyclerView murunlerilist;
    MurunleriAdapter murunleriAdapter;
    private MuzayedeDetayDto murunleriModelList;
    TextView muzayedeadi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_murunleri);
        murunlerilist = findViewById(R.id.murunlerilist);
        id=this.getIntent().getIntExtra("id",0);
        Log.e("id", String.valueOf(id));
        getMUrunleri(id);
        muzayedeadi= findViewById(R.id.mzayedeadi);
    }

    private void getMUrunleri(int id) {
        Call<MuzayedeDetayDto> call = Retrofit.getMurunleriService().getMurundetay(id);
        call.enqueue(new Callback<MuzayedeDetayDto>() {
            @Override
            public void onResponse(Call<MuzayedeDetayDto> call, Response<MuzayedeDetayDto> response) {
                Log.e("mesaj", response.body().toString());
                murunleriModelList = response.body();
                muzayedeadi.setText(murunleriModelList.getMuzayede().getMuzayedeAdi());
                initpost(murunleriModelList);
            }

            @Override
            public void onFailure(Call<MuzayedeDetayDto> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initpost(MuzayedeDetayDto murunleriModelList){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        murunlerilist.setLayoutManager(linearLayoutManager);
        murunleriAdapter = new MurunleriAdapter(getApplicationContext(), murunleriModelList);
        murunlerilist.setHasFixedSize(true);
        murunlerilist.setAdapter(murunleriAdapter);
    }
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
