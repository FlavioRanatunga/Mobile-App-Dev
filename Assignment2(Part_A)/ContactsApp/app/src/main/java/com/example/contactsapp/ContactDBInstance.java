package com.example.contactsapp;

import android.content.Context;

import androidx.room.Room;

public class ContactDBInstance {

    private static ContactDataBase database;

    public static ContactDataBase getDatabase(Context context) {
        if (database == null) {
            //Builds the database
            database = Room.databaseBuilder(context,
                            ContactDataBase.class, "app_database")
                    .allowMainThreadQueries()
                    .build();
        }
        return database;
    }

}
