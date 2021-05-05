package com.example.myapplication.ui.siparis;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Dtos.KullaniciPeyDto;
import com.example.myapplication.LognActivity;
import com.example.myapplication.R;
import com.example.myapplication.Service.LocalService;
import com.example.myapplication.Service.Retrofit;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SiparisFragment extends Fragment {

    private SiparisViewModel siparisViewModel;
    RecyclerView siparis;
    ArrayList<KullaniciPeyDto> kullaniciPeyArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_siparis, container, false);
        siparis = root.findViewById(R.id.siparis);
        LocalService localService = new LocalService(getContext());
        String id = localService.get("userId");
        if(localService.get("userId") == null){
            Intent intent = new Intent(getContext(), LognActivity.class);
            startActivity(intent);
        }
        else {
            getsiparislist(Integer.parseInt(id));
        }

        return root;
    }
    public void getsiparislist(int id){
        Call<ArrayList<KullaniciPeyDto>> call = Retrofit.getKullaniciPeyService().getsiparislist(id);
        call.enqueue(new Callback<ArrayList<KullaniciPeyDto>>() {
            @Override
            public void onResponse(Call<ArrayList<KullaniciPeyDto>> call, Response<ArrayList<KullaniciPeyDto>> response) {
                Log.e("mesaj",response.body().toString());
                kullaniciPeyArrayList = response.body();
                Log.e("fiyat",String.valueOf(kullaniciPeyArrayList.get(0).getUrun().getUrunFiyat()));
                initpost(kullaniciPeyArrayList);
            }

            @Override
            public void onFailure(Call<ArrayList<KullaniciPeyDto>> call, Throwable t) {
                Log.e("hata",t.getMessage());
                Toast.makeText(getContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void initpost( ArrayList<KullaniciPeyDto> kullaniciPeyArrayList){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        siparis.setLayoutManager(linearLayoutManager);
        siparisViewModel = new SiparisViewModel(getContext(), kullaniciPeyArrayList);
        siparis.setHasFixedSize(true);
        siparis.setAdapter(siparisViewModel);
    }
}
