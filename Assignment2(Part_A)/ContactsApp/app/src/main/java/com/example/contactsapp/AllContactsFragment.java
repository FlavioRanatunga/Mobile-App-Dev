package com.example.contactsapp;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toast;
import androidx.core.content.ContextCompat;

import android.provider.ContactsContract;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AllContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllContactsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerview;
    private List<Contact> contactsList;

    private static final int REQUEST_READ_CONTACT_PERMISSION = 3;

    public AllContactsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllContactsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AllContactsFragment newInstance(String param1, String param2) {
        AllContactsFragment fragment = new AllContactsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_all_contacts, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ContactDAO contactDAO = ContactDBInstance.getDatabase(requireContext()).contactDAO();
        contactsList = contactDAO.getAllContacts();

        recyclerview = view.findViewById(R.id.allContactsRecyclerView);
        FloatingActionButton addContactButton = view.findViewById(R.id.btnAddContact);
        FloatingActionButton importContact = view.findViewById(R.id.btnImportContact);


        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        ContactAdapter contactAdapter = new ContactAdapter(getContext(), contactsList, new MainActivityData(), new ContactAdapter.ItemClickListner() {
            @Override
            public void onItemClick(Contact contact) {
                MainActivityData mainActivityDataViewModel = new ViewModelProvider(getActivity()).
                        get(MainActivityData.class);

                mainActivityDataViewModel.setCurrentContactId(contact.getId());
                mainActivityDataViewModel.setClickedValue(2);

            }
        });

        //Setting the contactAdapter for the recyclerview
        recyclerview.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();


        addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityData mainActivityDataViewModel = new ViewModelProvider(getActivity()).
                        get(MainActivityData.class);
                mainActivityDataViewModel.setClickedValue(1);
            }
        });


        importContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checking if the permission is already granted.Requesting permission if not granted
                if (ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{Manifest.permission.READ_CONTACTS},
                            REQUEST_READ_CONTACT_PERMISSION);
                } else {
                    //Imports contact if permission is already granted
                    importContactDetails();
                }
            }
        });
    }

    //Requesting permission to read contacts
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_READ_CONTACT_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                importContactDetails();
            } else {
                Toast.makeText(requireContext(), "Permission denied to read contacts!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Creates intent to import contacts.
    public void importContactDetails(){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String importName = "";
        String importPhone = "";
        String importEmail = "";
        boolean inContacts = false;


        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {

            //Imports the relevant contact in to application.
            importName = importContactName(data);
            importPhone= importPhoneNumber(data);
            importEmail =  importEmail(data);

            //Checks to see if the imported contact is already in the contacts list.
            inContacts = inDatabase( importName, importPhone);

             if (inContacts == true){
                 Toast.makeText(requireContext(), "Contact already exists!", Toast.LENGTH_SHORT).show();
             }
             else{
                 //Adds the imported contact into the contacts list.
                 addContactToDatabase(importName,importPhone,importEmail);
                 Toast.makeText(requireContext(), "Imported contact added to contacts list", Toast.LENGTH_SHORT).show();
                 MainActivityData mainActivityDataViewModel = new ViewModelProvider(getActivity()).
                         get(MainActivityData.class);
                 mainActivityDataViewModel.setClickedValue(0);

             }
        }
    }

    //Helper method to import the contact name.
    //Based on the lecture slide's code.
    private String importContactName(Intent data){
        String contactName = "";
        Uri contactUri = data.getData();
        Cursor c;
        String[] queryFields = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        c = requireActivity().getContentResolver().query(
                contactUri, queryFields, null, null, null);
        try {
            if (c.getCount() > 0) {
                c.moveToFirst();
                 contactName = c.getString(1);
            }
        }
        finally {
            c.close();
        }

        return contactName;

    }

    //Helper method to import the contact phone number.
    //Based on the lecture slide's code.
    private String importPhoneNumber(Intent data) {

        String phoneNumber = null;
        Uri contactUri = Uri.parse(data.getData().getLastPathSegment());
        Cursor c;

       c = requireActivity().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                new String[]{String.valueOf(contactUri)},
                null
        );

        if (c != null) {
            if (c.moveToFirst()) {
                int phoneNumberColumnIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                if (phoneNumberColumnIndex >= 0) {
                    phoneNumber = c.getString(phoneNumberColumnIndex);
                }
            }
            c.close();
        }

        return phoneNumber;
    }

    //Helper method to import the contact email.
    //Based on the lecture slide's code.
    private String importEmail(Intent data) {

        String email = null;
        Uri contactUri = Uri.parse(data.getData().getLastPathSegment());
        Cursor c;

         c = requireActivity().getContentResolver().query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{String.valueOf(contactUri)},
                null
        );

        if (c != null) {
            if (c.moveToFirst()) {
                int emailColumnIndex = c.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS);
                if (emailColumnIndex >= 0) {
                    email = c.getString(emailColumnIndex);
                }
            }
            c.close();
        }

        return email;
    }


    //Helper method to check if the imported contact is already in the contacts list.
    public boolean inDatabase(String name, String phoneNo){
        boolean inDatabase = false;

        ContactDAO contactDAO = ContactDBInstance.getDatabase(requireContext()).contactDAO();
        List<Contact> contactsList = contactDAO.getAllContacts();
        for (Contact contact : contactsList) {
            if (contact.getName().equals(name) && contact.getPhone().equals(phoneNo)) {
                inDatabase = true;
            }
        }
        return inDatabase;
    }


    //Helper method to add the imported contacted into the database.
    public void addContactToDatabase(String name, String phone,String email) {

        ContactDAO contactDAO = ContactDBInstance.getDatabase(requireContext()).contactDAO();
        Contact contact = new Contact();
        contact.setName(name);
        contact.setPhone(phone);
        contact.setEmail(email);
        contactDAO.insert(contact);

    }

}





