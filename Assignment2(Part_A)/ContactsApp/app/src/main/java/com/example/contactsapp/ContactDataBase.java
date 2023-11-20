package com.example.contactsapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1)
public  abstract class ContactDataBase extends RoomDatabase {

    //Abstract method that returns a Data Access Object for the contacts database
    public abstract ContactDAO contactDAO();
}
