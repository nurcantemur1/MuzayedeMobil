package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Service.LocalService;
import com.example.myapplication.Service.Retrofit;
import com.example.myapplication.Tablolar.Kullanici;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LognActivity extends AppCompatActivity {
    LocalService localService;
    TextView uyeol;
    EditText email,sifre;
    CheckBox benihatirla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        localService = new LocalService(LognActivity.this);
        email =findViewById(R.id.email);
        sifre = findViewById(R.id.sifre);
        benihatirla = findViewById(R.id.benihatirla);
        benihatirla.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(email.getText() != null && sifre.getText() != null){
                        localService.add("email",email.getText().toString());
                        localService.add("sifre",sifre.getText().toString());
                    }
                }
                else {
                    localService.delete("email");
                    localService.delete("sifre");
                }
            }
        });
        if(localService.get("email")==null){
            benihatirla.setChecked(false);
        }
        else{
            benihatirla.setChecked(true);
            email.setText(localService.get("email"));
            sifre.setText(localService.get("sifre"));
        }

        uyeol = findViewById(R.id.uyeol);
        uyeol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LognActivity.this, KayitActivity.class);
                startActivity(intent);
            }
        });
        final Button giris = findViewById(R.id.giris);
        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 /*Intent intent = new Intent(LognActivity.this, MainActivity.class);
                 startActivity(intent);*/
                 if(benihatirla.isChecked()){
                     if(localService.get("email")==null){
                         localService.add("email",email.getText().toString());
                         localService.add("sifre",sifre.getText().toString());
                     }
                 }
               Login();

            }
        });
    }
    public void Login(){
        Call<Kullanici> call = Retrofit.getKullaniciService().getlogin(email.getText().toString(),sifre.getText().toString());
        call.enqueue(new Callback<Kullanici>() {
            @Override
            public void onResponse(Call<Kullanici> call, Response<Kullanici> response) {
                Log.i("BURADA","1");

                Log.i("BURADA","2");
                if(!response.isSuccessful()){
                    Log.i("BURADA","3");
                    Log.e("error", String.valueOf(response.code()));
                }
                else{
                    Kullanici kullanici = response.body();
                    Log.i("MESAJ",response.toString());

                   // assert kullanici != null;
                   // int userID = kullanici.getKullaniciID();
                   // Log.e("id",String.valueOf(kullanici.getKullaniciAdi()));
                    Log.e("id",String.valueOf(kullanici.getKullaniciID()));
                    localService.add("userId",String.valueOf(kullanici.getKullaniciID()));
                    Intent intent = new Intent(LognActivity.this, MainActivity.class);
                 //   intent.putExtra("id",userID);
                   // Log.e("id", String.valueOf(userID));
                    //  Log.e("id", String.valueOf(kullanici.getKullaniciID()));
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Basarili", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Kullanici> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
