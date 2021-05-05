package com.example.myapplication.ui.gallery;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.Tablolar.Kategori;
import java.util.ArrayList;


public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ViewHolder>{
    private ArrayList<Kategori> kategoriArrayList;
    private Context context;

    public  KategoriAdapter(Context context,ArrayList<Kategori> kategoriArrayList){
        this.context= context;
        this.kategoriArrayList= kategoriArrayList;
    }
    @NonNull
    @Override
    public KategoriAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kategori_list_item,parent,false);
        return new KategoriAdapter.ViewHolder(item_view);
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriAdapter.ViewHolder holder, int position) {
        holder.ktgradi.setText(kategoriArrayList.get(position).getKategoriAdi());
    }

    @Override
    public int getItemCount() {
        return kategoriArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ktgradi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ktgradi= itemView.findViewById(R.id.ktgradi);

        }
    }
}

