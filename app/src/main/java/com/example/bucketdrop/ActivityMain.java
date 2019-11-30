package com.example.bucketdrop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ActivityMain extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initbackgoundImage();
    }

    private void initbackgoundImage(){
        ImageView imageView = findViewById(R.id.imageViewbg);
        Glide.with(this)
                .load(R.drawable.background)
                .centerCrop()
                .into(imageView);
    }
}
