package com.example.assignment2;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ImgListAdapter extends RecyclerView.Adapter<ListVH> {

    ArrayList<Bitmap> list;
    ImageListViewModel imageViewModel;
    int prevPos = -1;

    public ImgListAdapter(ArrayList<Bitmap> list, ImageListViewModel imageViewModel){
        this.list = list;
        this.imageViewModel = imageViewModel;
    }
    @NonNull
    @Override
    public ListVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.image_layout,parent,false);
        ListVH listVHolder = new ListVH(view,parent);
        return listVHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListVH holder, int position) {
        list = imageViewModel.getImageList();
        Bitmap image = list.get(position);
        holder.imageV.setImageBitmap(image);
        if(prevPos == position)
        {
            holder.imageV.setBackgroundColor(Color.YELLOW);
        }
        else
        {
            holder.imageV.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                imageViewModel.setSelectedImage(image);
                int adapterPos = holder.getAdapterPosition();

                notifyItemChanged(prevPos);
                notifyItemChanged(adapterPos);
                prevPos = adapterPos;
                Log.d("OnBindViewHolder", "Image Clicked");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
