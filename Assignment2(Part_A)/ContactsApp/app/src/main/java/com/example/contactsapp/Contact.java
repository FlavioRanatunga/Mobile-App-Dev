package com.example.contactsapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Table name initialized as contacts
@Entity(tableName = "contacts")


public class Contact {

    //Primary key for the table
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "contact_name")
    private String name;

    @ColumnInfo(name = "contact_phone")
    private String phone;

    @ColumnInfo(name = "contact_email")
    private String email;

    @ColumnInfo(name = "contact_address")
    private String address;

    @ColumnInfo(name = "contact_image")
    private String imageURI;

    public Contact (String name, String phone, String email, String address, String imageURI){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.imageURI = imageURI;
    };

    public Contact(){

    };

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getImageURI() { return imageURI; }

    public void setImageURI(String imageURI) { this.imageURI = imageURI; }

}
