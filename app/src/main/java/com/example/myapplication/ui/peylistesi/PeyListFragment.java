package com.example.myapplication.ui.peylistesi;

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


public class PeyListFragment extends Fragment {

    PeyListViewModel peyListViewModel;
    ArrayList<KullaniciPeyDto> kPeyModelArrayList;
    RecyclerView peylistesi;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_peylistesi, container, false);

        peylistesi = root.findViewById(R.id.peylistesi);
        LocalService localService = new LocalService(this.getContext());
        String id = localService.get("userId");
        if(localService.get("userId") == null){
            Intent intent = new Intent(getContext(), LognActivity.class);
            startActivity(intent);
        }
        else {
            Log.e("userid",id);
            getpeylist(Integer.parseInt(id));
        }
        return root;
    }
    public void initpost( ArrayList<KullaniciPeyDto> kPeyModelArrayList){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        peylistesi.setLayoutManager(linearLayoutManager);
        peyListViewModel = new PeyListViewModel(getContext(), kPeyModelArrayList);
        peylistesi.setHasFixedSize(true);
        peylistesi.setAdapter(peyListViewModel);
    }


    private void getpeylist(int id) {
        Call<ArrayList<KullaniciPeyDto>> call = Retrofit.getKullaniciPeyService().getkullanicipey(id);
        call.enqueue(new Callback<ArrayList<KullaniciPeyDto>>() {
            @Override
            public void onResponse(Call<ArrayList<KullaniciPeyDto>> call, Response<ArrayList<KullaniciPeyDto>> response) {
                Log.e("mesaj", response.body().toString());
                kPeyModelArrayList = response.body();
                initpost(kPeyModelArrayList);

            }

            @Override
            public void onFailure(Call<ArrayList<KullaniciPeyDto>> call, Throwable t) {
                Log.e("hata: ", t.getMessage());
                Toast.makeText(getContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
