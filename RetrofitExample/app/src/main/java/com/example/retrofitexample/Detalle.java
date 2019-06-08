package com.example.retrofitexample;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Detalle extends AppCompatActivity{
    TextView title;
    TextView id;
    TextView url;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitydetalle);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (TextView) findViewById(R.id.title);
        id= (TextView) findViewById(R.id.id);
        url = (TextView) findViewById(R.id.url);
        imagen = (ImageView) findViewById(R.id.imagen);

        title.setText(getIntent().getExtras().getString("curTitle"));
        id.setText(getIntent().getExtras().getString("curId"));
        url.setText(getIntent().getExtras().getString("curUrl"));

        String img = (getIntent().getExtras().getString("curImagen")).toString();

        Glide.with(getApplicationContext())
                .load(img)
                .into(imagen);

//        imagen.setImageResource(getIntent().getExtras().getInt("curImagen"));
    }
}
