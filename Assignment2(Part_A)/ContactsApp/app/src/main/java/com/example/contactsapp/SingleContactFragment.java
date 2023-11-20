package com.example.contactsapp;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleContactFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SingleContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingleContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingleContactFragment newInstance(String param1, String param2) {
        SingleContactFragment fragment = new SingleContactFragment();
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
        View view= inflater.inflate(R.layout.fragment_single_contact, container, false);

        MainActivityData mainActivityData = new ViewModelProvider(getActivity()).get(MainActivityData.class);

        //Getting the contact DAO
        ContactDAO contactDAO = ContactDBInstance.getDatabase(requireContext()).contactDAO();

        //Getting the contacts information from the database
        Contact contact = contactDAO.getContactById(mainActivityData.getCurrentContactId());

                EditText txtContactName = view.findViewById(R.id.txtContactName);
                EditText txtContactPhoneNumber = view.findViewById(R.id.txtContactNumber);
                EditText txtContactEmail = view.findViewById(R.id.txtContactEmail);
                EditText txtContactAddress = view.findViewById(R.id.txtContactAddress);
                ImageView imageViewProfilePhoto = view.findViewById(R.id.imageViewProfilePhoto);

                //Displaying the contact information
                txtContactName.setText(contact.getName());
                txtContactPhoneNumber.setText(contact.getPhone());
                txtContactEmail.setText(contact.getEmail());
                txtContactAddress.setText(contact.getAddress());

                //Displaying the edit contact information when the orientation changes
                //This is to ensure the value is displayed even when the orientation changes
                if(mainActivityData.getEditContactName()!=null){
                    txtContactName.setText(mainActivityData.getEditContactName());
                }
                if(mainActivityData.getEditContactPhone()!=null){
                    txtContactPhoneNumber.setText(mainActivityData.getEditContactPhone());
                }

                if(mainActivityData.getEditContactEmail()!=null){
                    txtContactEmail.setText(mainActivityData.getEditContactEmail());
                }

                if(mainActivityData.getEditContactAddress()!=null){
                    txtContactAddress.setText(mainActivityData.getEditContactAddress());
                }

                //Displaying the contact image if there is one
                if(contact.getImageURI()!=null){
                Uri imageUri = Uri.parse(contact.getImageURI());
                imageViewProfilePhoto.setImageURI(imageUri);
                }else{
                imageViewProfilePhoto.setImageResource(R.drawable.user);
                }

                Button saveButton = view.findViewById(R.id.btnSave);

                saveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = txtContactName.getText().toString();
                        String phone = txtContactPhoneNumber.getText().toString();
                        String email = txtContactEmail.getText().toString();
                        String address = txtContactAddress.getText().toString();

                        contact.setId(mainActivityData.getCurrentContactId());
                        contact.setName(name);
                        contact.setPhone(phone);
                        contact.setEmail(email);
                        contact.setAddress(address);

                        //Saving the updated contact
                        contactDAO.update(contact);

                        //Setting the edit contact live data to null
                        mainActivityData.setEditContactName(null);
                        mainActivityData.setEditContactPhone(null);
                        mainActivityData.setEditContactEmail(null);
                        mainActivityData.setEditContactAddress(null);

                        Toast.makeText(getActivity(), "Contact Saved", Toast.LENGTH_SHORT).show();
                    }
                }
                );

                Button delete = view.findViewById(R.id.btnDelete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Deleting the contact
                        contactDAO.delete(contact);
                        Toast.makeText(getActivity(), "Contact Deleted", Toast.LENGTH_SHORT).show();

                        //Setting the edit contact live data to null
                        mainActivityData.setEditContactName(null);
                        mainActivityData.setEditContactPhone(null);
                        mainActivityData.setEditContactEmail(null);
                        mainActivityData.setEditContactAddress(null);

                        mainActivityData.setClickedValue(0);
                    }
                });

                Button back = view.findViewById(R.id.btnBack);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Setting the edit contact live data to null
                        mainActivityData.setEditContactName(null);
                        mainActivityData.setEditContactPhone(null);
                        mainActivityData.setEditContactEmail(null);
                        mainActivityData.setEditContactAddress(null);

                        mainActivityData.setClickedValue(0);
                    }
                });

        //Updating the edit contact name live data if the user inputs new information
        txtContactName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mainActivityData.setEditContactName(String.valueOf(txtContactName.getText()));
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        //Updating the edit contact phone live data if the user inputs new information
        txtContactPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mainActivityData.setEditContactPhone(String.valueOf(txtContactPhoneNumber.getText()));
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        //Updating the edit contact email live data if the user inputs new information
        txtContactEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mainActivityData.setEditContactEmail(String.valueOf(txtContactEmail.getText()));
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });


        //Updating the edit contact address live data if the user inputs new information
        txtContactAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mainActivityData.setEditContactAddress(String.valueOf(txtContactAddress.getText()));
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });


        return view;
    }
}