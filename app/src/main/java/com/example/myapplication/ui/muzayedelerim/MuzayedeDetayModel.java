package com.example.myapplication.ui.muzayedelerim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Dtos.MuzayedeDetayDto;
import com.example.myapplication.Listeners.IListItemSelectedListener;
import com.example.myapplication.R;

public class MuzayedeDetayModel extends RecyclerView.Adapter<MuzayedeDetayModel.ViewHolder> {
    private Context context;
    private MuzayedeDetayDto murunleriModelList;
    private IListItemSelectedListener itemSelectedListener;

    public MuzayedeDetayModel(Context context,MuzayedeDetayDto murunleriModelList){
        this.context = context;
        this.murunleriModelList = murunleriModelList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.m_detay_list_item,parent,false);
        return new MuzayedeDetayModel.ViewHolder(item_view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.urunadi.setText(murunleriModelList.getUrunler().get(position).getUrunAdi());
        holder.urunaciklamasi.setText(murunleriModelList.getUrunler().get(position).getUrunAciklamasi());
        holder.urunadedi.setText(String.valueOf(murunleriModelList.getUrunler().get(position).getUrunAdet()));
        holder.urunfiyat.setText(String.valueOf(murunleriModelList.getUrunler().get(position).getUrunFiyat()));
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return murunleriModelList.getUrunler().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView urunadi,urunaciklamasi,urunadedi,urunfiyat;
        Button sil;
        int position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            urunadi = itemView.findViewById(R.id.urunadi);
            urunaciklamasi = itemView.findViewById(R.id.uaciklamasi);
            urunadedi = itemView.findViewById(R.id.uadedi);
            urunfiyat = itemView.findViewById(R.id.ufiyati);
            sil = itemView.findViewById(R.id.sil);
            sil.setOnClickListener(new View.OnClickListener() {
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