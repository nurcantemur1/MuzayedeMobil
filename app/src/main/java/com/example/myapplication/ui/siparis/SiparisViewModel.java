package com.example.myapplication.ui.siparis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Dtos.KullaniciPeyDto;
import com.example.myapplication.R;

import java.util.ArrayList;

public class SiparisViewModel extends RecyclerView.Adapter<SiparisViewModel.ViewHolder> {
    Context context;
    ArrayList<KullaniciPeyDto> kullaniciPeyArrayList;
    public SiparisViewModel(Context context,ArrayList<KullaniciPeyDto> kullaniciPeyArrayList) {
        this.context = context;
        this.kullaniciPeyArrayList = kullaniciPeyArrayList;
    }

    @NonNull
    @Override
    public SiparisViewModel.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.siparis_list_item,parent,false);

        return new ViewHolder(item_view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SiparisViewModel.ViewHolder holder, int position) {

        holder.madi.setText(kullaniciPeyArrayList.get(position).getMuzayede().getMuzayedeAdi());
        holder.urunadi.setText(kullaniciPeyArrayList.get(position).getUrun().getUrunAdi());
        holder.uaciklamasi.setText(kullaniciPeyArrayList.get(position).getUrun().getUrunAciklamasi());
        holder.urunfiyat.setText(String.valueOf(kullaniciPeyArrayList.get(position).getUrun().getUrunFiyat()));
        holder.urunfiyat.setText(String.valueOf(kullaniciPeyArrayList.get(position).getUrun().getUrunFiyat()));
        holder.urundurum.setText("Sipariş Alındı");
        holder.pey.setText(String.valueOf(kullaniciPeyArrayList.get(position).getPey()));
    }

    @Override
    public int getItemCount() {
        return kullaniciPeyArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView madi,urunadi,urunfiyat,pey,uaciklamasi,urundurum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            madi = itemView.findViewById(R.id.madi);
            urunadi = itemView.findViewById(R.id.urunadi);
            urunfiyat = itemView.findViewById(R.id.urunfiyat);
            uaciklamasi = itemView.findViewById(R.id.uaciklamasi);
            urundurum = itemView.findViewById(R.id.urundurum);
            pey = itemView.findViewById(R.id.pey);
        }
    }
}
