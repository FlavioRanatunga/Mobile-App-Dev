package com.example.contactsapp;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {

    //Initializing the three fragments
    AddContactFragment addContactFragment;

    AllContactsFragment allContactsFragment;

    SingleContactFragment singleContactFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Populating the contact list with some dummy data
        populateContactList();

        MainActivityData mainActivityDataViewModel = new ViewModelProvider(this).get(MainActivityData.class);

        //Loading the relevant fragment according to the user request.
        //All contacts fragment is loaded by default.
        mainActivityDataViewModel.clickedValue.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (mainActivityDataViewModel.getClickedValue() == 0) {
                    loadAllContactsFragment();
                } else if (mainActivityDataViewModel.getClickedValue() == 1) {
                    loadAddContactFragment();
                } else if (mainActivityDataViewModel.getClickedValue() == 2) {
                    loadSingleContactFragment();
                }

            }
        });
    }


    //Helper method to load the add contact fragment
    void loadAddContactFragment() {
        FragmentManager fm = getSupportFragmentManager();

        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) { //Portrait

            //Removing the other fragments if they are already loaded
            Fragment allContactsFragment = fm.findFragmentById(R.id.allcontacts_container);
            if (allContactsFragment != null) {
                fm.beginTransaction().remove(allContactsFragment).commit();
            }

            Fragment singleContactFragment = fm.findFragmentById(R.id.singlecontact_container);
            if (singleContactFragment != null) {
                fm.beginTransaction().remove(singleContactFragment).commit();
            }

            //Loading the add contact fragment
            addContactFragment = new AddContactFragment();
            fm.beginTransaction().replace(R.id.addcontact_container, addContactFragment).commit();
        }

        //Landscape
        else {
            addContactFragment = new AddContactFragment();
            fm.beginTransaction().replace(R.id.both_container, addContactFragment).commit();
        }
    }

    //Helper method to load the all contacts fragment
    void loadAllContactsFragment() {
        FragmentManager fm = getSupportFragmentManager();

        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) { //Portrait

            //Removing the other fragments if they are already loaded
            Fragment addContactFragment = fm.findFragmentById(R.id.addcontact_container);
            if (addContactFragment != null) {
                fm.beginTransaction().remove(addContactFragment).commit();
            }

            Fragment singleContactFragment = fm.findFragmentById(R.id.singlecontact_container);
            if (singleContactFragment != null) {
                fm.beginTransaction().remove(singleContactFragment).commit();
            }

            //Loading the all contacts fragment
            allContactsFragment = new AllContactsFragment();
            fm.beginTransaction().replace(R.id.allcontacts_container, allContactsFragment).commit();
        }
        //Landscape
        else {
            allContactsFragment = new AllContactsFragment();
            fm.beginTransaction().replace(R.id.both_container, allContactsFragment).commit();
        }
    }

    //Helper method to load the single contact fragment
    void loadSingleContactFragment() {
        FragmentManager fm = getSupportFragmentManager();

        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_PORTRAIT) { //Portrait

            //Removing the other fragments if they are already loaded
            Fragment allContactsFragment = fm.findFragmentById(R.id.allcontacts_container);
            if (allContactsFragment != null) {
                fm.beginTransaction().remove(allContactsFragment).commit();
            }

            Fragment addContactFragment = fm.findFragmentById(R.id.addcontact_container);
            if (addContactFragment != null) {
                fm.beginTransaction().remove(addContactFragment).commit();
            }

            //Loading the single contact fragment
            singleContactFragment = new SingleContactFragment();
            fm.beginTransaction().replace(R.id.singlecontact_container, singleContactFragment).commit();
        }
        //Landscape
        else {
            singleContactFragment = new SingleContactFragment();
            fm.beginTransaction().replace(R.id.both_container, singleContactFragment).commit();
        }

    }

    //Helper method to populate the contact list with some dummy data
    void populateContactList() {
        Contact contact1 = new Contact("Kevin Anderson", "(555) 234-5678", "kevin@mail.com", "789 Oakwood Ave", null);
        Contact contact2 = new Contact("Sarah Johnson", "(555) 987-6543", "sarah@example.com", "5678 Oak Ave, Austin", null);
        Contact contact3 = new Contact("Michael Smith", "(555) 555-5555", "michael@example.com", "9876 Pine Rd, Seattle", null);
        Contact contact4 = new Contact("Emily Davis", "(555) 321-6548", "emily@example.com", "2468 Cedar Ln, New York", null);
        Contact contact5 = new Contact("David Brown", "(555) 876-5432", "david@example.com", "1357 Maple Ave, Los Angeles", null);
        Contact contact6 = new Contact("Laura Anderson", "(555) 777-8888", "laura@example.com", "4321 Birch Blvd", null);
        Contact contact7 = new Contact("Alex Garcia", "(555) 222-3333", "alex@example.com", "98765 Willow St, Miami", null);
        Contact contact8 = new Contact("Jessica Thompson", "(555) 444-7890", "jessica@example.com", "3690 Aspen Dr, San Francisco", null);
        Contact contact9 = new Contact("Ryan Miller", "(555) 777-9999", "ryan@example.com", "7410 Juniper Rd, Denver", null);
        Contact contact10 = new Contact("Amanda Clark", "(555) 888-5678", "amanda@example.com", "8520 Redwood Ln, Portland", null);

        ContactDAO contactDAO = ContactDBInstance.getDatabase(this).contactDAO();

        if(contactDAO.getAllContacts().isEmpty()) {
            contactDAO.insert(contact1);
            contactDAO.insert(contact2);
            contactDAO.insert(contact3);
            contactDAO.insert(contact4);
            contactDAO.insert(contact5);
            contactDAO.insert(contact6);
            contactDAO.insert(contact7);
            contactDAO.insert(contact8);
            contactDAO.insert(contact9);
            contactDAO.insert(contact10);
        }
    }
}

