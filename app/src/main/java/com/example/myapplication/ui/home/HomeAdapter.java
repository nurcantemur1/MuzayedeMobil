package com.example.myapplication.ui.home;

import android.app.Activity;
import android.icu.text.DateTimePatternGenerator;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Listeners.IListItemSelectedListener;
import com.example.myapplication.Listeners.ThreadListener;
import com.example.myapplication.Model.MyDateFormat;
import com.example.myapplication.R;
import com.example.myapplication.Tablolar.Muzayede;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private IListItemSelectedListener itemSelectedListener;
    private Activity activity;
    private ArrayList<Muzayede> muzayedeArrayList;
    private List<Timer> timers;
    int sayac = 0;

    public HomeAdapter(Activity activity, ArrayList<Muzayede> muzayedeArrayList) {
        this.activity = activity;
        this.muzayedeArrayList = muzayedeArrayList;
        this.timers = new ArrayList<>();
        for(int i=0;i<muzayedeArrayList.size();i++){
            timers.add(new Timer());
        }
        this.muzayedeArrayList.get(0).setIzlenme(1);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView madi, time;
        Button incele;
        int position;

        public ViewHolder(final View view) {
            super(view);
            madi = view.findViewById(R.id.madi);
            time = view.findViewById(R.id.time);
            incele = view.findViewById(R.id.incele);
            incele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemSelectedListener.onClick(position, v);
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.muzayedelist_item, parent, false);
        return new HomeAdapter.ViewHolder(item_view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Muzayede muzayede = muzayedeArrayList.get(position);
        holder.madi.setText(muzayede.getMuzayedeAdi());
        holder.incele.setText("İncele");
        if(MyDateFormat.format(muzayede.getDate()).before(new Date())){
            muzayede.setIzlenme(2);
        }

        ZamanKontrol(holder, position);
        holder.position = position;
    }

    private void ZamanKontrol(final ViewHolder holder, final int position) {
        final Timer t = timers.get(position);
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Muzayede muzayede = muzayedeArrayList.get(position);
                        if (muzayede.getIzlenme() == 0) {
                            if (muzayede.getDate() != null) {
                                String kalan = ZamanHesapla(muzayede.getDate());

                                if (kalan.equals("0 gün 0 saat 0 dakika 0 saniye")) {
                                    holder.time.setText("Müzayede başladı");
                                    holder.incele.setText("Canlı");
                                    muzayede.setIzlenme(1);
                                } else {
                                    holder.time.setText(kalan);
                                }
                            } else {
                                String b = "";
                                Log.e("hata", b);
                            }
                        }
                        if (muzayede.getIzlenme() == 1) {
                            holder.time.setText("Müzayede devam ediyor");
                            holder.incele.setText("Canlı");
                            if(position!=0){
                                BitisKontrol(position);
                            }
                        }
                        if (muzayede.getIzlenme() == 2) {
                            holder.time.setText("Müzayede Bitmiştir");
                            holder.incele.setText("İncele");
                        }
                    }
                });

            }
        };    // t.cancel();
        t.schedule(tt, 0, 1000);

    }

    private void BitisKontrol(final int position){
        final Timer t2 = new Timer();
        TimerTask tt2 = new TimerTask() {
            @Override
            public void run() {
                sayac++;
                Log.e("sayac55", String.valueOf(sayac));
                if(sayac == 1500){
                    muzayedeArrayList.get(position).setIzlenme(2);
                }
            }
        };    // t.cancel();
        t2.schedule(tt2, 0, 1000000);
    }

    public String ZamanHesapla(String mtarih) {
            Date date = MyDateFormat.format(mtarih);
            Date suan = new Date();
            assert date != null;
            long fark = date.getTime() - suan.getTime();
            long sn = TimeUnit.MILLISECONDS.toSeconds(fark) % 60;
            long dk = TimeUnit.MILLISECONDS.toMinutes(fark) % 60;
            long saat = TimeUnit.MILLISECONDS.toHours(fark) % 24;
            long gun = TimeUnit.MILLISECONDS.toDays(fark) % 365;
            long yil = TimeUnit.MILLISECONDS.toDays(fark) / 365l;
            if (yil > 0) gun += yil * 365;
            String kalan = gun + " gün " + saat + " saat " + dk + " dakika " + sn + " saniye";
            return kalan;
    }
    @Override
    public int getItemCount() {
        return muzayedeArrayList.size();
    }

    public void setListItemSelectedListener(IListItemSelectedListener l){
        itemSelectedListener = l;
    }
 }
