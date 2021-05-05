package com.example.myapplication.ui.kisisel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myapplication.LognActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Service.LocalService;
import com.example.myapplication.Service.Retrofit;
import com.example.myapplication.Tablolar.Kullanici;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KisiselFragment extends Fragment {

    EditText isim,soyisim,email,sifre,telefon,adres;
    Button kaydet;
    Kullanici kullanici;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_kisisel, container, false);
        isim = root.findViewById(R.id.isim);
        soyisim =root.findViewById(R.id.soyisim);
        email = root.findViewById(R.id.email);
        sifre = root.findViewById(R.id.sifre);
        telefon = root.findViewById(R.id.telefon);
        kaydet = root.findViewById(R.id.kaydet);
        adres =root.findViewById(R.id.sdres);

        LocalService localService = new LocalService(getContext());
        String id = localService.get("userId");
        if(localService.get("userId") == null){
            Intent intent = new Intent(getContext(), LognActivity.class);
            startActivity(intent);
        }
        else {
            Bilgilerim(Integer.parseInt(id));
        }
      kaydet = root.findViewById(R.id.kaydet);
        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //LocalService localService = new LocalService(getContext());
               // String id = localService.update("userId",String.valueOf(kullaniciArrayList.getKullaniciID()));
                Update();
            }

        });

        return root;
    }

    public void Bilgilerim(int id){

        Call<Kullanici> call =Retrofit.getKullaniciService().getkullanici(id);
        call.enqueue(new Callback<Kullanici>() {
            @Override
            public void onResponse(Call<Kullanici> call, Response<Kullanici>response) {

                if(!response.isSuccessful()){
                    Log.e("hata",String.valueOf(response.code()));
                }
                else {
                    kullanici =response.body();
                    isim.setText(String.valueOf(kullanici.getKullaniciAdi()));
                    soyisim.setText(String.valueOf(kullanici.getKullaniciSoyadi()));
                    email.setText(String.valueOf(kullanici.getKullanicimail()));
                    adres.setText(String.valueOf(kullanici.getKullaniciAdres()));
                    telefon.setText(String.valueOf(kullanici.getKullaniciTelefon()));
                    sifre.setText(String.valueOf(kullanici.getSifre()));
                    Log.e("",String.valueOf(kullanici.getKullaniciAdi()));
                    Log.e("",String.valueOf(kullanici.getKullaniciID()));

                }
            }

            @Override
            public void onFailure(Call<Kullanici> call, Throwable t) {
                Toast.makeText(getContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void  Update(){
        kullanici.setKullaniciAdi(isim.getText().toString());
        kullanici.setKullaniciTelefon(telefon.getText().toString());
        kullanici.setKullaniciAdres(adres.getText().toString());
        kullanici.setKullaniciSoyadi(soyisim.getText().toString());
        kullanici.setKullanicimail(email.getText().toString());
        kullanici.setSifre(sifre.getText().toString());
        Call<Kullanici>call = Retrofit.getKullaniciService().kupdate(kullanici);
        call.enqueue(new Callback<Kullanici>() {
            @Override
            public void onResponse(Call<Kullanici> call, Response<Kullanici> response) {
                if(response.body()!= null){
                    Toast.makeText(getContext(), "Basarili", Toast.LENGTH_SHORT).show();
                    LocalService localService = new LocalService(getContext());
                    String id = localService.get("userId");
                    Bilgilerim(Integer.parseInt(id));
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Kullanici> call, Throwable t) {
                Toast.makeText(getContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
