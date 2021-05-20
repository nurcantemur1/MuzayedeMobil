package com.example.myapplication.ui.murunleri;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Dtos.KullaniciPeyDto;
import com.example.myapplication.Dtos.MuzayedeDetayDto;
import com.example.myapplication.Listeners.IListItemSelectedListener;
import com.example.myapplication.R;
import com.example.myapplication.Service.Retrofit;
import com.example.myapplication.Tablolar.Kullanici;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MurunleriAdapter extends RecyclerView.Adapter<MurunleriAdapter.ViewHolder> {

    private Context context;
    private MuzayedeDetayDto murunleriModelList;

    public MurunleriAdapter(Context context,MuzayedeDetayDto murunleriModelList){
        this.context = context;
        this.murunleriModelList = murunleriModelList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.murunleri_list_item,parent,false);
        return new MurunleriAdapter.ViewHolder(item_view);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.urunadi.setText(murunleriModelList.getUrunler().get(position).getUrunAdi());
        holder.urunaciklamasi.setText(murunleriModelList.getUrunler().get(position).getUrunAciklamasi());
        holder.urunadedi.setText(String.valueOf(murunleriModelList.getUrunler().get(position).getUrunAdet()));
        holder.urunfiyat.setText(String.valueOf(murunleriModelList.getUrunler().get(position).getUrunFiyat()));
        try {
            if(new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(murunleriModelList.getMuzayede().getDate()).before(new Date()))
            satis(holder,position);
            else {
            holder.satisbilgisi.setVisibility(View.GONE);
            holder.satisbaslik.setVisibility(View.GONE);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return murunleriModelList.getUrunler().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView urunadi,urunaciklamasi,urunadedi,urunfiyat,sonfiyat,satisbilgisi,satisbaslik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            urunadi = itemView.findViewById(R.id.urunadi);
            urunaciklamasi = itemView.findViewById(R.id.urunaciklamasi);
            urunadedi = itemView.findViewById(R.id.urunadedi);
            urunfiyat = itemView.findViewById(R.id.urunfiyati);
            satisbilgisi = itemView.findViewById(R.id.satisbilgisi);
            satisbaslik = itemView.findViewById(R.id.satisbaslik);
        }
    }
    public void satis(final ViewHolder holder, int i){
        Call<KullaniciPeyDto> call = Retrofit.getKullaniciPeyService().getsonpey(murunleriModelList.getUrunler().get(i).getMurunid());
        call.enqueue(new Callback<KullaniciPeyDto>() {
            @Override
            public void onResponse(Call<KullaniciPeyDto> call, Response<KullaniciPeyDto> response) {
                if(response.isSuccessful()){
                    final KullaniciPeyDto sonpey = response.body();
                    Call<Kullanici> call1 = Retrofit.getKullaniciService().getkullanici( sonpey.getKullaniciID());
                    call1.enqueue(new Callback<Kullanici>() {
                        @Override
                        public void onResponse(Call<Kullanici> call, Response<Kullanici> response) {
                            if(response.isSuccessful()){
                                Kullanici k = response.body();
                               // Mehmet çekici tarafından 9.57 tl ye alındı
                                holder.satisbilgisi.setText(
                                        k.getKullaniciAdi()+" "+
                                        k.getKullaniciSoyadi()+" tarafından "+
                                        sonpey.getPey()+" tl ye alındı");
                            }
                        }

                        @Override
                        public void onFailure(Call<Kullanici> call, Throwable t) {
                            Toast.makeText(context, "Basari1111siz", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    holder.satisbilgisi.setText("Satılamadı");
                }
                Log.e("bfb",response.message());
            }

            @Override
            public void onFailure(Call<KullaniciPeyDto> call, Throwable t) {
                Toast.makeText(context, "Basari1221siz", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
