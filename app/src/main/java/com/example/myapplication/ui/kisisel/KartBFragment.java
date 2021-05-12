package com.example.myapplication.ui.kisisel;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Listeners.IListItemSelectedListener;
import com.example.myapplication.R;
import com.example.myapplication.Service.LocalService;
import com.example.myapplication.Service.Retrofit;
import com.example.myapplication.Tablolar.KartBilgileri;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KartBFragment extends Fragment {
    KartBAdapter kartBAdapter;
    RecyclerView recyclerView;
    EditText bakiye,kartno,isim;
    Button kartekle,kaydet;
    LocalService localService;
    int id;
    List<KartBilgileri> kartBilgilerim;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_kartbilgileri, container, false);
        localService = new LocalService(getContext());
        id = Integer.parseInt(localService.get("userId"));
        recyclerView = root.findViewById(R.id.recyclerView);
        kartekle = root.findViewById(R.id.kartekle);
        kartekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(v);
            }
        });
        getall();
        return root;
    }

    public void getall() {
        Call<List<KartBilgileri>> call = Retrofit.getKartBService().getallbykullanici(id);
        call.enqueue(new Callback<List<KartBilgileri>>() {
            @Override
            public void onResponse(Call<List<KartBilgileri>> call, Response<List<KartBilgileri>> response) {
                if(!response.isSuccessful()){
                    Log.e("hata",String.valueOf(response.code()));
                }
                kartBilgilerim = response.body();
                initpost();
                Toast.makeText(getContext(), "Basarili", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<KartBilgileri>> call, Throwable t) {
                Toast.makeText(getContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @SuppressLint("WrongViewCast")
    public void showCustomDialog(View view){
        final Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.kartbilgisi_ekle);
        dialog.setCancelable(true);
        bakiye = dialog.findViewById(R.id.bakiye);
        isim = dialog.findViewById(R.id.isim);
        kartno = dialog.findViewById(R.id.kartno);
        Button olustur =dialog.findViewById(R.id.kaydet);

        olustur.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Assert")
            @Override
            public void onClick(View view) {

            final KartBilgileri kartBilgileri = new KartBilgileri();
            kartBilgileri.setKartNo(kartno.getText().toString());
            kartBilgileri.setHesapNo(isim.getText().toString());
            kartBilgileri.setBakiye(Double.parseDouble(bakiye.getText().toString()));
            kartBilgileri.setKullaniciId(id);

            Call<KartBilgileri> call = Retrofit.getKartBService().kartEkle(kartBilgileri);
            call.enqueue(new Callback<KartBilgileri>() {
                @Override
                public void onResponse(Call<KartBilgileri> call, Response<KartBilgileri> response) {
                    if(!response.isSuccessful()){
                        Log.e("hata",String.valueOf(response.code()));
                    }
                    Toast.makeText(getContext(), "Basarili", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<KartBilgileri> call, Throwable t) {
                    Toast.makeText(getContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
                }
            });
            }
        });
        dialog.show();
    }
    public void initpost() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        kartBAdapter = new KartBAdapter(getContext(), kartBilgilerim);
        kartBAdapter.setListItemSelectedListener(new IListItemSelectedListener() {
            @Override
            public void onClick(int position, View v) {
                Call<KartBilgileri> call = Retrofit.getKartBService().varsayilanYap(kartBilgilerim.get(position).getId());
                call.enqueue(new Callback<KartBilgileri>() {
                    @Override
                    public void onResponse(Call<KartBilgileri> call, Response<KartBilgileri> response) {
                        Toast.makeText(getContext(), "Basarili", Toast.LENGTH_SHORT).show();
                        getall();
                    }

                    @Override
                    public void onFailure(Call<KartBilgileri> call, Throwable t) {
                        Toast.makeText(getContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(kartBAdapter);
    }
}
