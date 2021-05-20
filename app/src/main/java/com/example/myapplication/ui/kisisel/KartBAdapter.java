package com.example.myapplication.ui.kisisel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.Listeners.IListItemSelectedListener;
import com.example.myapplication.R;
import com.example.myapplication.Tablolar.KartBilgileri;
import java.util.List;

public class KartBAdapter extends RecyclerView.Adapter<KartBAdapter.ViewHolder>{
    private IListItemSelectedListener itemSelectedListener;
    List<KartBilgileri> kartBilgilerim;
    Context context;

    public KartBAdapter(Context context, List<KartBilgileri> kartBilgilerim){
        this.context = context;
        this.kartBilgilerim = kartBilgilerim;
    }
    @NonNull
    @Override
    public KartBAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kart_list_item,parent,false);
        return new ViewHolder(item_view);
    }

    @Override
    public void onBindViewHolder(@NonNull KartBAdapter.ViewHolder holder, int position) {
        holder.bakiye.setText(String.valueOf(kartBilgilerim.get(position).getBakiye()));
        Log.e("gelen", String.valueOf(kartBilgilerim.get(position).getBakiye()));
        holder.kulaniciadi.setText(kartBilgilerim.get(position).getHesapNo());
        holder.checkBox.setChecked(kartBilgilerim.get(position).varsayilan);
        holder.kartno.setText(kartBilgilerim.get(position).getKartNo());
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return kartBilgilerim.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView kulaniciadi,bakiye,kartno;
        Button kartsil;
        CheckBox checkBox;
        int position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kulaniciadi = itemView.findViewById(R.id.kullaniciadi);
            kartno = itemView.findViewById(R.id.kartno);
            bakiye = itemView.findViewById(R.id.bakiye);
            kartsil = itemView.findViewById(R.id.kartsil);
            checkBox = itemView.findViewById(R.id.checkBox2);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBox.isChecked()){
                        itemSelectedListener.onClick(position,v);
                    }
                    checkBox.setChecked(true);
                }
            });
            kartsil.setOnClickListener(new View.OnClickListener() {
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
