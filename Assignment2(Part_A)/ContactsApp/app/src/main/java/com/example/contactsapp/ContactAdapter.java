package com.example.contactsapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder>{

    Context context;
    List<Contact> contactList;
    private MainActivityData mainActivityDataViewModel;
    private ItemClickListner itemClickListner;

    public ContactAdapter(Context context, List<Contact> contactList, MainActivityData mainActivityDataViewModel, ItemClickListner itemClickListner){
        this.context = context;
        this.contactList = contactList;
        this.mainActivityDataViewModel = mainActivityDataViewModel;
        this.itemClickListner = itemClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context)
                .inflate(R.layout.contact_item, parent, false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Contact contact = contactList.get(position);

        //Display the contact name and phone number
        holder.txtContactName.setText(contact.getName());
        holder.txtContactPhone.setText(contact.getPhone());

        //Navigate the user to the edit contact fragment
        holder.txtContactName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner.onItemClick(contactList.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public interface ItemClickListner{
         void onItemClick(Contact contacts);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        //TextViews to display the contact name and phone number
        TextView txtContactName;
        TextView txtContactPhone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtContactName = itemView.findViewById(R.id.txtContactName);
            txtContactPhone = itemView.findViewById(R.id.txtContactPhone);
        }
    }

}
