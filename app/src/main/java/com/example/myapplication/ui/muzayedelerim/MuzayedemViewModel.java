package com.example.myapplication.ui.muzayedelerim;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Listeners.IListItemSelectedListener;
import com.example.myapplication.R;
import com.example.myapplication.Tablolar.Muzayede;

import java.util.ArrayList;

public class MuzayedemViewModel extends RecyclerView.Adapter<MuzayedemViewModel.ViewModel> {
    private IListItemSelectedListener itemSelectedListener;
    Context context;
    ArrayList<Muzayede> muzayedemm;

    public MuzayedemViewModel(Context context,ArrayList<Muzayede> muzayedemm) {
        this.context= context;
        this.muzayedemm = muzayedemm;

    }

    @NonNull
    @Override
    public MuzayedemViewModel.ViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.muzayedem_list_item,parent,false);

        return new ViewModel(item_view);
    }

    @Override
    public void onBindViewHolder(@NonNull MuzayedemViewModel.ViewModel holder, int position) {

        holder.madi.setText(muzayedemm.get(position).getMuzayedeAdi());
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return muzayedemm.size();
    }

    public class ViewModel extends RecyclerView.ViewHolder {
        TextView madi;
        Button sil,detay,yayinla;
        int position;
        public ViewModel(@NonNull View itemView) {
            super(itemView);
            madi = itemView.findViewById(R.id.madi);
            yayinla = itemView.findViewById(R.id.yayinla);
            yayinla.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemSelectedListener.onClick(position,v);
                }
            });
            sil = itemView.findViewById(R.id.sil);
            sil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemSelectedListener.onClick(position,v);

                }
            });
            detay = itemView.findViewById(R.id.detay);
            detay.setOnClickListener(new View.OnClickListener() {
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
