package com.example.myapplication.ui.peylistesi;

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

public class PeyListViewModel extends RecyclerView.Adapter<PeyListViewModel.ViewHolder> {

     Context context;
     ArrayList<KullaniciPeyDto>  kPeyModelArrayList;

    public PeyListViewModel(Context context, ArrayList<KullaniciPeyDto> kPeyModelArrayList) {
        this.context = context;
        this.kPeyModelArrayList = kPeyModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pey_list_item,parent,false);

        return new PeyListViewModel.ViewHolder(item_view);
    }

    @Override
    public void onBindViewHolder(@NonNull PeyListViewModel.ViewHolder holder, int position) {

        holder.madi.setText(kPeyModelArrayList.get(position).getMuzayede().getMuzayedeAdi());
        holder.urunadi.setText(kPeyModelArrayList.get(position).getUrun().getUrunAdi());
        holder.uaciklamasi.setText(kPeyModelArrayList.get(position).getUrun().getUrunAciklamasi());
        holder.ufiyati.setText(String.valueOf(kPeyModelArrayList.get(position).getUrun().getUrunFiyat()));
        holder.urunpey.setText(String.valueOf(kPeyModelArrayList.get(position).getPey()));
    }

    @Override
    public int getItemCount() {
        return kPeyModelArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView madi,urunadi,uaciklamasi,ufiyati,urunpey;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            madi = itemView.findViewById(R.id.muzayedeadi);
            urunadi = itemView.findViewById(R.id.urunadi);
            uaciklamasi = itemView.findViewById(R.id.uaciklamasi);
            ufiyati = itemView.findViewById(R.id.ufiyati);
            urunpey = itemView.findViewById(R.id.urunpey);

        }
    }


}
