package com.example.myapplication.ui.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Listeners.IListItemSelectedListener;
import com.example.myapplication.Model.UrunResimModel;
import com.example.myapplication.R;
import com.example.myapplication.Service.Retrofit;
import com.example.myapplication.Tablolar.Kategori;
import com.example.myapplication.Tablolar.Muzayede;
import com.example.myapplication.Tablolar.Urun;
import com.example.myapplication.Tablolar.UrunResim;
import com.example.myapplication.ui.home.HomeAdapter;
import com.example.myapplication.ui.murunleri.MurunleriActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private KategoriAdapter kategoriAdapter;
    RecyclerView urunler,kategoriler;
    private ArrayList<Urun> urunArrayList;
    private ArrayList<Kategori> kategoriArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        urunler=root.findViewById(R.id.urunler);
        kategoriler = root.findViewById(R.id.kategoriler);
        geturunler();
        getkategoriler();
        return root;
    }
    public void uruninitpost(final ArrayList<Urun> urunArrayList){
        LinearLayoutManager linearLayoutManagers = new LinearLayoutManager(getContext());
        urunler.setLayoutManager(linearLayoutManagers);
        galleryViewModel = new GalleryViewModel(getContext(), urunArrayList);
        urunler.setHasFixedSize(true);
        urunler.setAdapter(galleryViewModel);
    }

    public void kategoriinitpost(final ArrayList<Kategori> kategoriArrayList){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        kategoriler.setLayoutManager(linearLayoutManager);
        kategoriAdapter = new KategoriAdapter(getContext(), kategoriArrayList);
        kategoriler.setHasFixedSize(true);
        kategoriler.setAdapter(kategoriAdapter);
    }

    private void geturunler() {
        Call <ArrayList<Urun>> call = Retrofit.getUrunService().geturunler();
        call.enqueue(new Callback <ArrayList<Urun>>() {
            @Override
            public void onResponse(Call <ArrayList<Urun>> call, Response <ArrayList<Urun>> response) {
                Log.e("mesaj", response.body().toString());
                urunArrayList= response.body();
                uruninitpost(urunArrayList);

            }

            @Override
            public void onFailure(Call <ArrayList<Urun>> call, Throwable t) {
                Log.e("hata: ", t.getMessage());
                Toast.makeText(getContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getkategoriler() {
        Call <ArrayList<Kategori>> call = Retrofit.getkategoriService().getkategori();
        call.enqueue(new Callback <ArrayList<Kategori>>() {
            @Override
            public void onResponse(Call <ArrayList<Kategori>> call, Response <ArrayList<Kategori>> response) {
                Log.e("kategori", response.body().toString());
                kategoriArrayList = response.body();
                kategoriinitpost(kategoriArrayList);
            }

            @Override
            public void onFailure(Call <ArrayList<Kategori>> call, Throwable t) {
                Log.e("hata: ", t.getMessage());
                Toast.makeText(getContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

