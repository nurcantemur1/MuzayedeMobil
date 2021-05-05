package com.example.myapplication.ui.gallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Listeners.IListItemSelectedListener;
import com.example.myapplication.R;
import com.example.myapplication.Tablolar.Urun;

import java.util.ArrayList;

public class GalleryViewModel extends RecyclerView.Adapter<GalleryViewModel.ViewHolder> {
    private Context context;
    private IListItemSelectedListener itemSelectedListener;
    private ArrayList<Urun> urunArrayList;

    public GalleryViewModel(Context context,ArrayList<Urun> urunArrayList){
        this.context = context;
        this.urunArrayList = urunArrayList;
    }
    @NonNull
    @Override
    public GalleryViewModel.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.urunlist_item,parent,false);
        return new ViewHolder(item_view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull GalleryViewModel.ViewHolder holder, int position) {

        holder.urunadi.setText(urunArrayList.get(position).getUrunAdi());
        holder.urunaciklamasi.setText(urunArrayList.get(position).getUrunAciklamasi());
        holder.urunfiyat.setText(Double.toString(urunArrayList.get(position).getUrunFiyat()));
    }

    @Override
    public int getItemCount() {
        return urunArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView urunadi,urunaciklamasi,urunfiyat;
        Button sepet;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            urunadi= itemView.findViewById(R.id.urunadi);
            urunaciklamasi=itemView.findViewById(R.id.urunaciklamasi);
            urunfiyat = itemView.findViewById(R.id.urunfiyat);
        }
        public void setListItemSelectedListener(IListItemSelectedListener l){
             itemSelectedListener = l;
        }
    }
}