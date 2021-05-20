package com.example.myapplication.ui.gallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Listeners.IListItemSelectedListener;
import com.example.myapplication.Model.ImageListAdapter;
import com.example.myapplication.Model.UrunResimModel;
import com.example.myapplication.R;
import com.example.myapplication.Service.Retrofit;
import com.example.myapplication.Tablolar.Resim;
import com.example.myapplication.Tablolar.Urun;
import com.example.myapplication.Tablolar.UrunResim;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        geturunresim(holder,position);
    }

    private void geturunresim(final ViewHolder holder, final int position) {
        Call<ArrayList<Resim>> call = Retrofit.getUrunResimService().GetUrunResim(urunArrayList.get(position).getUrunID());
        call.enqueue(new Callback<ArrayList<Resim>>() {
            @Override
            public void onResponse(Call<ArrayList<Resim>> call, Response<ArrayList<Resim>> response) {
                if(response.isSuccessful()){
                    if(response.body().size() != 0){
                        ArrayList<Resim> list = response.body();
                        //Log.e("bitmap hata",list.get(0).getBase64());
                        //holder.urunresim.setImageBitmap(BitmapFactory.decodeFile(response.body().get(position).getBase64()));

                        resimleriGoster(holder,list);
                        Toast.makeText(context, "Basarılı", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Log.e("bitmap hata",response.message());
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Resim>> call, Throwable t) {
                Toast.makeText(context, "Basarisiz", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void resimleriGoster(final ViewHolder holder, final ArrayList<Resim> resimler){
        ImageListAdapter adapter = new ImageListAdapter(context,resimler);
        adapter.setListItemSelectedListener(new IListItemSelectedListener() {
            @Override
            public void onClick(int position, View v) {
                resimler.remove(position);
                resimleriGoster(holder,resimler);
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,RecyclerView.HORIZONTAL,false);
        holder.imagelist.setLayoutManager(layoutManager);
        holder.imagelist.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return urunArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView urunadi,urunaciklamasi,urunfiyat;
        ImageView urunresim;
        RecyclerView imagelist;
        Button sepet;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            urunadi= itemView.findViewById(R.id.urunadi);
            urunaciklamasi=itemView.findViewById(R.id.urunaciklamasi);
            urunfiyat = itemView.findViewById(R.id.urunfiyat);
            imagelist = itemView.findViewById(R.id.image_listView);
        }
    }
    public void setListItemSelectedListener(IListItemSelectedListener l){
        itemSelectedListener = l;
    }
}