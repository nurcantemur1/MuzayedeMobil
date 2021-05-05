package com.example.myapplication.ui.urunlerim;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Listeners.IListItemSelectedListener;
import com.example.myapplication.R;
import com.example.myapplication.Service.LocalService;
import com.example.myapplication.Service.Retrofit;
import com.example.myapplication.Tablolar.Urun;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UrunumViewModel extends RecyclerView.Adapter<UrunumViewModel.ViewModel> {

    private IListItemSelectedListener itemSelectedListener;
    Context context;
    ArrayList<Urun> urunumArrayList;

    public UrunumViewModel(Context context,ArrayList<Urun> urunumArrayList) {
        this.context=context;
        this.urunumArrayList = urunumArrayList;
    }

    @NonNull
    @Override
    public UrunumViewModel.ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.urunum_list_item,parent,false);
        return new ViewModel(item_view);
    }

    @Override
    public void onBindViewHolder(@NonNull UrunumViewModel.ViewModel holder, int position) {
        holder.urunadi.setText(urunumArrayList.get(position).getUrunAdi());
        holder.uaciklamasi.setText(urunumArrayList.get(position).getUrunAciklamasi());
        holder.ufiyat.setText(String.valueOf(urunumArrayList.get(position).getUrunFiyat()));
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return urunumArrayList.size();
    }


    public class ViewModel extends RecyclerView.ViewHolder {
        TextView urunadi,uaciklamasi,ufiyat;
        Button usil,udetay;
        int position;
        public ViewModel(@NonNull View itemView) {
            super(itemView);
            urunadi = itemView.findViewById(R.id.urunadi);
            uaciklamasi = itemView.findViewById(R.id.uaciklamasi);
            ufiyat = itemView.findViewById(R.id.ufiyat);
            usil = itemView.findViewById(R.id.sil);
            usil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    itemSelectedListener.onClick(position,v);
                }
            });
            udetay = itemView.findViewById(R.id.detay);
            udetay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemSelectedListener.onClick(position,v);
                }
            });
        }
    }
    public void setListItemSelectedListener(IListItemSelectedListener l){
        itemSelectedListener = l;
    }
}
