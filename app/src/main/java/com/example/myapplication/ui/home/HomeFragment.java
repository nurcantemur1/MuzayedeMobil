package com.example.myapplication.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Listeners.IListItemSelectedListener;
import com.example.myapplication.Model.MyDateFormat;
import com.example.myapplication.R;
import com.example.myapplication.Service.LocalService;
import com.example.myapplication.Service.Retrofit;
import com.example.myapplication.Tablolar.Muzayede;
import com.example.myapplication.ui.murunleri.CanliActivity;
import com.example.myapplication.ui.murunleri.MurunleriActivity;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    HomeAdapter homeAdapter;
    RecyclerView muzayedeler;
    private ArrayList<Muzayede> muzayedeArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        muzayedeler = root.findViewById(R.id.muzayedeler);
        getdata();
        return root;
    }


    public void initpost(final ArrayList<Muzayede> muzayedeArrayList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        muzayedeler.setLayoutManager(linearLayoutManager);
        homeAdapter = new HomeAdapter(this.getActivity(),this.muzayedeArrayList);
        homeAdapter.setListItemSelectedListener(new IListItemSelectedListener() {
            @Override
            public void onClick(int position, View v) {
                Muzayede muzayede = muzayedeArrayList.get(position);
                //muzayede.getTime()
                Intent intent;
                Button button = (Button) v;
                if(button.getText().equals("Canlı")){
                    intent = new Intent(v.getContext(), CanliActivity.class);
                }
                else {
                    intent = new Intent(v.getContext(), MurunleriActivity.class);
                }
                intent.putExtra("id", muzayede.getMuzayedeID());
                startActivity(intent);
            }
        });
        muzayedeler.setHasFixedSize(true);
        muzayedeler.setAdapter(homeAdapter);
    }

    private void getdata() {
        Call<ArrayList<Muzayede>> call = Retrofit.getMuzayedeService().mgetall();
        call.enqueue(new Callback<ArrayList<Muzayede>>() {
            @Override
            public void onResponse(Call<ArrayList<Muzayede>> call, Response<ArrayList<Muzayede>> response) {
                if(response.isSuccessful()){
                    Log.e("mesaj", response.body().toString());
                    muzayedeArrayList = response.body();
                    initpost(muzayedeArrayList);
                }
                else
                    Log.e("hata: ", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<ArrayList<Muzayede>> call, Throwable t) {
                Log.e("hata: ", t.getMessage());
                Toast.makeText(getContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
            }
        });
    }

}