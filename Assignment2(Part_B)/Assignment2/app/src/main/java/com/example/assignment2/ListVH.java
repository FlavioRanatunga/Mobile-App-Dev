package com.example.assignment2;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListVH extends RecyclerView.ViewHolder{
    public ImageView imageV;

    public ListVH(@NonNull View itemView, ViewGroup parent) {
        super(itemView);
        int height = parent.getMeasuredHeight() /3;
        ViewGroup.LayoutParams lp = itemView.getLayoutParams();
        lp.height = height;
        imageV = itemView.findViewById(R.id.imageView);

    }
}