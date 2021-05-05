package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Service.LocalService;

public class SingInActivity extends AppCompatActivity {
    private Button uyegirisi,uyeol,misafir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singn);
        uyegirisi = findViewById(R.id.uyegiris);
        misafir = findViewById(R.id.misafir);
        uyeol = findViewById(R.id.uyeol);

        uyegirisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingInActivity.this,LognActivity.class));
                finish();
            }
        });
        uyeol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingInActivity.this,KayitActivity.class));
                finish();
            }
        });
        misafir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingInActivity.this,MainActivity.class));
                finish();
            }
        });
   }
}
