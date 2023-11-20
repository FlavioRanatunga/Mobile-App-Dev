package com.example.tic_tac_toe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.ViewHolder>{
    Context context;
    ArrayList<Avatar> avatarList;
    private MainActivityData mainActivityDataViewModel;
    private ItemClickListner itemClickListner;


    public AvatarAdapter(Context context, ArrayList<Avatar> avatarList, MainActivityData mainActivityDataViewModel, ItemClickListner itemClickListner){
        this.context = context;
        this.avatarList = avatarList;
        this.mainActivityDataViewModel = mainActivityDataViewModel;
        this.itemClickListner = itemClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context)
                .inflate(R.layout.avatar_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Avatar avatar = avatarList.get(position);
        holder.avatarImageView.setImageResource(avatar.avatarImage);


        holder.avatarImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner.onItemClick(avatarList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return avatarList.size();
    }

    public interface ItemClickListner{
        void onItemClick(Avatar avatars);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ShapeableImageView avatarImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarImageView= itemView.findViewById(R.id.avatarImageView);

        }
    }

}
