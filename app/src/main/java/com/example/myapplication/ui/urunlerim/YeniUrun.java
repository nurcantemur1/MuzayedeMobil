package com.example.myapplication.ui.urunlerim;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Listeners.IListItemSelectedListener;
import com.example.myapplication.Model.ImageConverter;
import com.example.myapplication.Model.ImageListAdapter;
import com.example.myapplication.Model.UrunResimModel;
import com.example.myapplication.R;
import com.example.myapplication.Service.LocalService;
import com.example.myapplication.Service.Retrofit;
import com.example.myapplication.Tablolar.Resim;
import com.example.myapplication.Tablolar.Urun;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.nio.file.Files.*;

public class YeniUrun extends AppCompatActivity {

    private static final int RESULT_LOAD_IMG = 1;
    private EditText urunadi, uaciklamasi, ufiyat, uadet;
    RecyclerView imagelist;
    private Button ekle,resim;
    LocalService localService;
    int resimsayisi = 0;
    private ArrayList<Resim> resimler= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_urunekle);
        urunadi = findViewById(R.id.urunadi);
        uaciklamasi = findViewById(R.id.uaciklamasi);
        ufiyat = findViewById(R.id.ufiyat);
        uadet = findViewById(R.id.uadet);
        imagelist = findViewById(R.id.image_listView);
        resim = findViewById(R.id.resim);
        resim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
            }
        });

        localService = new LocalService(getApplicationContext());
        final String id = localService.get("userId");
        ekle = findViewById(R.id.kaydet);
        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (urunadi.getText().toString().equals("") || uaciklamasi.getText().toString().equals("") || ufiyat.getText().toString().equals("") || uadet.getText().toString().equals("") || resimsayisi>0) {
                    Toast.makeText(getApplicationContext(), "Boş alanları doldurunuz", Toast.LENGTH_SHORT).show();
                }
                else {
                    final Urun urun = new Urun();
                    urun.setKullaniciID(Integer.parseInt(id));
                    urun.setUrunAdi(urunadi.getText().toString());
                    urun.setUrunAciklamasi(uaciklamasi.getText().toString());
                    urun.setUrunAdet(Integer.parseInt(uadet.getText().toString()));
                    urun.setUrunFiyat(Double.parseDouble(ufiyat.getText().toString()));
                    try {
                        Call<Urun> call = Retrofit.getUrunService().urunekle(urun);
                        call.enqueue(new Callback<Urun>() {
                            @Override
                            public void onResponse(Call<Urun> call, Response<Urun> response) {
                                if (!response.isSuccessful()) {
                                    Log.e("error", String.valueOf(response.message()));
                                } else {
                                    Log.i("urun", response.body().toString());
                                    Toast.makeText(getApplicationContext(), "Basarili", Toast.LENGTH_SHORT).show();
                                    Log.i("eklenenurunid", String.valueOf(response.body().getUrunID()));
                                    upload(response.body().getUrunID());
                                }
                            }

                            @Override
                            public void onFailure(Call<Urun> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "hata", Toast.LENGTH_SHORT).show();
                            }
                        });
                      /*  UrunResimModel model = new UrunResimModel();
                        model.setUrun(urun);
                        model.setResimList(resimler);
                        Call<String> call = Retrofit.getUrunService().urunresimekle(model);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(@NotNull Call<String> call, @NotNull Response<String> response) {
                                if (!response.isSuccessful()) {
                                    Log.e("error", String.valueOf(response.message()));
                                } else {
                                    Log.i("urun", response.body().toString());
                                    Toast.makeText(getApplicationContext(), "Basarili", Toast.LENGTH_SHORT).show();
                                    Log.i("eklenenurunid", String.valueOf(response.body()));
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "hata", Toast.LENGTH_SHORT).show();

                            }
                        });*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(v.getContext(), UrunlerimActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK
                && null != data) {
            if(data.getData()!=null){

                Uri uri=data.getData();
                String encodedImage = ImageConverter.encodeImage(getApplicationContext(),uri);
                Resim picture = new Resim();
                picture.setBase64("data:image/jpeg;base64,"+encodedImage);
                Log.e("fv",encodedImage);
                resimler.add(picture);

            } else {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        String encodedImage = ImageConverter.encodeImage(getApplicationContext(),uri);
                        Resim picture = new Resim();
                        picture.setBase64("data:image/jpeg;base64,"+encodedImage);
                        resimler.add(picture);
                    }
                }
            }
            resimleriGoster();
        } else {
            Toast.makeText(this, "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }
    }


    void resimleriGoster(){
        ImageListAdapter adapter = new ImageListAdapter(getApplicationContext(),resimler);
        adapter.setListItemSelectedListener(new IListItemSelectedListener() {
            @Override
            public void onClick(int position, View v) {
                resimler.remove(position);
                resimleriGoster();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false);
        imagelist.setLayoutManager(layoutManager);
        imagelist.setAdapter(adapter);
    }


  void upload(int urunid){
        for(int i=0;i<resimler.size();i++){
            Call<Resim> call = Retrofit.getUrunResimService().addresim(urunid,resimler.get(i).getBase64());
            call.enqueue(new Callback<Resim>() {
                @Override
                public void onResponse(Call<Resim> call, Response<Resim> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(getApplicationContext(), "başarılı", Toast.LENGTH_SHORT).show();
                    }
                    else Log.e("hata222", String.valueOf(response.code()));
                }

                @Override
                public void onFailure(Call<Resim> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "başarısız", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
