package com.example.retrofitexample.Adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.retrofitexample.Detalle;
import com.example.retrofitexample.MainActivity;
import com.example.retrofitexample.Model.RetroPhoto;
import com.example.retrofitexample.R;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewViewHolder> {

    private List<RetroPhoto> dataList;
    private Context context;

    public CustomAdapter(Context context, List<RetroPhoto> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public CustomViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row,parent,false);

        return new CustomViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewViewHolder holder, final int posicion) {

        holder.txtTitle.setText(dataList.get(posicion).getTitle());
        holder.txtUrl.setText(dataList.get(posicion).getUrl());
        holder.txtId.setText(dataList.get(posicion).getId().toString());

        Glide.with(context)
                .load(dataList.get(posicion).getThumbnailUrl())
                .into(holder.coverImage);

        holder.detallecardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("curImagen", dataList.get(posicion).getThumbnailUrl());
                bundle.putString("curTitle", dataList.get(posicion).getTitle().toString());
                bundle.putString("curId", dataList.get(posicion).getId().toString());
                bundle.putString("curUrl", dataList.get(posicion).getUrl());
                Intent iconIntent = new Intent(view.getContext(), Detalle.class);
                iconIntent.putExtras(bundle);
                view.getContext().startActivity(iconIntent);
            }
        });
    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }

    public class CustomViewViewHolder extends RecyclerView.ViewHolder {

        public  final View mView;

        TextView txtTitle;
        CardView detallecardview;
        TextView txtUrl;
        TextView txtId;
        private ImageView coverImage;

        public CustomViewViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;

            txtTitle = mView.findViewById(R.id.title);
            txtUrl = mView.findViewById(R.id.url);
            txtId = mView.findViewById(R.id.id);
            coverImage = mView.findViewById(R.id.coverImage);
            detallecardview = mView.findViewById(R.id.row);
        }
    }
}
