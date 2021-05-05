package com.example.myapplication.ui.muzayedelerim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Listeners.IListItemSelectedListener;
import com.example.myapplication.R;
import com.example.myapplication.Tablolar.Urun;

import java.util.ArrayList;

public class MUrunEkleViewModel extends RecyclerView.Adapter<MUrunEkleViewModel.ViewHolder> {
    Context context;
    ArrayList<Urun> murunleriModelArrayList;
    IListItemSelectedListener itemSelectedListener;

    public MUrunEkleViewModel(Context context, ArrayList<Urun> murunleriModelArrayList) {
        this.context= context;
        this.murunleriModelArrayList = murunleriModelArrayList;
    }

    @NonNull
    @Override
    public MUrunEkleViewModel.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_list_item,parent,false);
        return new ViewHolder(item_view);
    }

    @Override
    public void onBindViewHolder(@NonNull MUrunEkleViewModel.ViewHolder holder, int position) {
        holder.urnadi.setText(murunleriModelArrayList.get(position).getUrunAdi());
        holder.adet.setText(String.valueOf(murunleriModelArrayList.get(position).getUrunAdet()));
        holder.fiyat.setText(String.valueOf(murunleriModelArrayList.get(position).getUrunFiyat()));
        holder.position =position;
    }

    @Override
    public int getItemCount() {
        return murunleriModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView urnadi,fiyat,adet;
        CheckBox checkBox;
        int position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            urnadi = itemView.findViewById(R.id.urnadi);
            fiyat = itemView.findViewById(R.id.fiyat);
            adet = itemView.findViewById(R.id.adet);
            checkBox = itemView.findViewById(R.id.checkBox);
            checkBox.setOnClickListener(new View.OnClickListener() {
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
