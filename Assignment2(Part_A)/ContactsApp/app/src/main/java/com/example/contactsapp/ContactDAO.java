package com.example.contactsapp;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ContactDAO {

    //Inserts a new contact into the database
    @Insert
    void insert(Contact contact);

    //Updates an existing contact in the database
    @Update
    void update(Contact contact);

    //Deletes a contact from the database
    @Delete
    void delete(Contact contact);

    //Returns a list of all contacts in the database
    @Query("SELECT * FROM contacts")
    List<Contact> getAllContacts();

    //Returns a list of contacts with the given id
    @Query("SELECT * FROM contacts WHERE id = :id")
    Contact getContactById(int id);

}
