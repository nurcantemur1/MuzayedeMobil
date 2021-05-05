package com.example.myapplication.ui.urunlerim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.myapplication.Listeners.IListItemSelectedListener;
import com.example.myapplication.LognActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Service.LocalService;
import com.example.myapplication.Service.Retrofit;
import com.example.myapplication.Tablolar.Urun;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UrunlerimActivity extends AppCompatActivity {
    private UrunumViewModel urunlerimViewModel;
    RecyclerView urunum;
    LocalService localService;
    ArrayList<Urun> urunumArrayList;
    Button yeniurun;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_urunum);
        yeniurun = findViewById(R.id.yeniurun);
        yeniurun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), YeniUrun.class);
                startActivity(intent);
            }
        });
        urunum = findViewById(R.id.urunum);
        localService = new LocalService(getApplicationContext());
        id = localService.get("userId");
        if(localService.get("userId") == null){
            Intent intent = new Intent(getApplicationContext(), LognActivity.class);
            startActivity(intent);
        }
        else {
            geturunum(Integer.parseInt(id));
        }
    }
    public void geturunum(int id){
        Call<ArrayList<Urun>> call = Retrofit.getUrunService().geturunum(id);
        call.enqueue(new Callback<ArrayList<Urun>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<ArrayList<Urun>> call, Response<ArrayList<Urun>> response) {

                Log.e("mesaj",response.body().toString());
                urunumArrayList = response.body();
                initpost(urunumArrayList);
            }

            @Override
            public void onFailure(Call<ArrayList<Urun>> call, Throwable t) {
                Log.e("hata",t.getMessage());
                Toast.makeText(getApplicationContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void initpost(final ArrayList<Urun> urunumArrayList){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        urunum.setLayoutManager(linearLayoutManager);
        urunlerimViewModel = new UrunumViewModel(getApplicationContext(), urunumArrayList);
        urunlerimViewModel.setListItemSelectedListener(new IListItemSelectedListener() {
            @Override
            public void onClick(int position, View v) {
                Urun urun = urunumArrayList.get(position);
                if(urun != null){

                    Button btn = (Button) v;
                    if(btn.getId()  == R.id.sil){
                        {
                            Call<Boolean> call = Retrofit.getUrunService().urunsil(urun.getUrunID());
                            call.enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                    if (!response.isSuccessful()) {
                                        Log.e("error", String.valueOf(response.code()));
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Silindi", Toast.LENGTH_SHORT).show();
                                        geturunum(Integer.parseInt(id));
                                    }
                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    else if(btn.getId()  == R.id.detay){

                        Intent intent = new Intent(v.getContext(),UrunDetay.class);
                        intent.putExtra("uurunid",urunumArrayList.get(position).getUrunID());
                        Log.e("gidenurunid", String.valueOf(urunumArrayList.get(position).getUrunID()));
                        v.getContext().startActivity(intent);
                    }
                }
            }
        });
        urunum.setHasFixedSize(true);
        urunum.setAdapter(urunlerimViewModel);
    }
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
