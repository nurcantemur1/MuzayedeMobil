package com.example.myapplication.ui.muzayedelerim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.example.myapplication.Listeners.IListItemSelectedListener;
import com.example.myapplication.LognActivity;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Model.MyDateFormat;
import com.example.myapplication.R;
import com.example.myapplication.Service.LocalService;
import com.example.myapplication.Service.Retrofit;
import com.example.myapplication.Tablolar.Muzayede;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MuzayedelerimActivity extends AppCompatActivity {
    private MuzayedemViewModel muzayedemViewModel;
    Button mekle;
    RecyclerView muzayedem;
    ArrayList<Muzayede> muzayedemm;
    EditText madi;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_muzayedem);
        mekle = findViewById(R.id.muzayedeekle);
        mekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCustomDialog(v);
            }
        });
        muzayedem = findViewById(R.id.muzayedem);
        LocalService localService = new LocalService(this.getApplicationContext());
        id = localService.get("userId");
        if(localService.get("userId") == null){
            Intent intent = new Intent(getApplicationContext(), LognActivity.class);
            startActivity(intent);
        }
        else {
            getkmuzayedeleri(Integer.parseInt(id));
        }
    }
    public void uruninitpost(final ArrayList<Muzayede> muzayedemm){
        LinearLayoutManager linearLayoutManagers = new LinearLayoutManager(getApplicationContext());
        muzayedem.setLayoutManager(linearLayoutManagers);
        muzayedemViewModel = new MuzayedemViewModel(getApplicationContext(), muzayedemm);
        muzayedemViewModel.setListItemSelectedListener(new IListItemSelectedListener() {
            @Override
            public void onClick(int position, View v) {

                if(muzayedemm.get(position) != null){
                    final Muzayede muzayede = muzayedemm.get(position);
                    Button btn = (Button) v;
                    if(btn.getId() == R.id.yayinla){
                        if(muzayedemm.size()>0){

                            Calendar calendar = Calendar.getInstance();
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            int month = calendar.get(Calendar.MONTH);
                            int year = calendar.get(Calendar.YEAR);
                            final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                            final int minute = calendar.get(Calendar.MINUTE);
                            DatePickerDialog datePickerDialog = new DatePickerDialog(MuzayedelerimActivity.this,R.style.MyDialogTheme, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                                    Toast.makeText(getApplicationContext(), dayOfMonth + "/" + (month+1) + "/" + year, Toast.LENGTH_SHORT).show();
                                    final String tarih = ((dayOfMonth)<10?"0"+dayOfMonth:dayOfMonth) + "."
                                            + ((month+1)<10?"0"+(month+1):(month+1)) + "."
                                            +year;
                                    TimePickerDialog timePickerDialog = new TimePickerDialog(MuzayedelerimActivity.this,R.style.MyDialogTheme, new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            Toast.makeText(getApplicationContext(), hourOfDay + " : "+ minute , Toast.LENGTH_SHORT).show();
                                            String time = (hourOfDay<10?"0"+hourOfDay:hourOfDay)+":"
                                                    +(minute<10?"0"+minute:minute)+":00";
                                            // String tamzaman = tarih +"T"+ time;
                                            String tamzaman = tarih +" "+ time; //06.05.2021 16:00:00
                                            Log.e("TARIH",tamzaman);

                                            Log.e("id",String.valueOf(muzayede.getMuzayedeID()));
                                            muzayede.setDate(tamzaman);
                                            muzayede.setIzlenme(0);
                                            mupdate(muzayede,tamzaman);

                                        }
                                    }, hour,minute,true);
                                    timePickerDialog.show();
                                }
                            }, day,month,year);
                            datePickerDialog.show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Önce müzayedene ürün eklemelisin!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(btn.getId() == R.id.sil){
                        Call<Boolean> call = Retrofit.getMuzayedeService().msil(muzayede.getMuzayedeID());
                        call.enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if (!response.isSuccessful()) {
                                    Log.e("error", String.valueOf(response.code()));
                                } else {
                                    Toast.makeText(getApplicationContext(), "Silindi", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MuzayedelerimActivity.class);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                else if(btn.getId() == R.id.detay){
                    Intent intent = new Intent(getApplicationContext(), MuzayedeDetay.class);
                    intent.putExtra("mid",muzayede.getMuzayedeID());
                    startActivity(intent);
                    Log.e("mid",String.valueOf(muzayede.getMuzayedeID()));
                    }

                }
            }
        });
        muzayedem.setHasFixedSize(true);
        muzayedem.setAdapter(muzayedemViewModel);
    }
    private void getkmuzayedeleri(int id) {
        Call<ArrayList<Muzayede>> call = Retrofit.getMuzayedeService().kmgetall(id);
        call.enqueue(new Callback<ArrayList<Muzayede>>() {
            @Override
            public void onResponse(Call <ArrayList<Muzayede>> call, Response<ArrayList<Muzayede>> response) {
                Log.e("mesaj", response.body().toString());
                muzayedemm = response.body();
                uruninitpost(muzayedemm);
            }
            @Override
            public void onFailure(Call <ArrayList<Muzayede>> call, Throwable t) {
                Log.e("hata: ", t.getMessage());
                Toast.makeText(getApplicationContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @SuppressLint("WrongViewCast")
    public void showCustomDialog(View view){
        final Dialog dialog = new Dialog(view.getContext());
        dialog.setContentView(R.layout.diyalog_muzayede);
        dialog.setCancelable(true);
        madi = dialog.findViewById(R.id.madi);
        Button olustur =dialog.findViewById(R.id.olustur);

        olustur.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Assert")
            @Override
            public void onClick(View view) {
                Log.e("madi", String.valueOf(madi.getText()));
                LocalService localService = new LocalService(view.getContext());
                String id = localService.get("userId");

                Muzayede muzayede = new Muzayede();
                muzayede.setMuzayedeAdi(madi.getText().toString());
                muzayede.setKullaniciID(Integer.parseInt(id));
                muzayede.setIzlenme(0);
                Date date = new Date();
                muzayede.setDate(MyDateFormat.toString(date));
                Call<Muzayede> call = Retrofit.getMuzayedeService().muzayedeAdd(muzayede);
                call.enqueue(new Callback<Muzayede>() {
                    @Override
                    public void onResponse(Call<Muzayede> call, Response<Muzayede> response) {
                        if(!response.isSuccessful()){
                            Log.e("error", String.valueOf(response.code()));
                            Log.e("error2", String.valueOf(response.message()));
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Basarili", Toast.LENGTH_SHORT).show();
                            Muzayede muzayed = response.body();
                            Intent intent = new Intent(getApplicationContext(), MuzayedeDetay.class);
                            intent.putExtra("mid",muzayed.getMuzayedeID());
                            startActivity(intent);
                        }
                    }
                    @Override
                    public void onFailure(Call<Muzayede> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Basarisiz", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        dialog.show();
    }
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    public void mupdate(Muzayede m,String zaman){
        m.setDate(zaman);
        m.setMuzayedeAdi(m.getMuzayedeAdi());
        m.setIzlenme(0);
        m.setMuzayedeID(m.getMuzayedeID());
        Log.e("idddd",String.valueOf(m.getMuzayedeID()));
        Call<Muzayede> call = Retrofit.getMuzayedeService().muzayedeUpdate(m);
        call.enqueue(new Callback<Muzayede>() {
            @Override
            public void onResponse(Call<Muzayede> call, Response<Muzayede> response) {
                if(!response.isSuccessful()){
                    Log.e("errror", String.valueOf(response.code()));
                }
                else {
                    Muzayede a = response.body();
                    if(a == null){
                        Log.e("lol", String.valueOf(response.code()));
                    }
                    else {
                        Log.e("errmid", String.valueOf(a.getMuzayedeID()));
                        Log.e("err", response.body().getDate());
                        Log.e("err", response.body().getMuzayedeAdi());
                        Toast.makeText(getApplicationContext(), "başarılı", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<Muzayede> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "başarısız", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
