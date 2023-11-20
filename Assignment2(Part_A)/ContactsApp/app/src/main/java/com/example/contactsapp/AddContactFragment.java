package com.example.contactsapp;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddContactFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddContactFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ImageView imageViewAddPhoto;

    Contact newContact = new Contact();

    File photoFile;

    String name ;
    String phone;
    String email ;
    String address ;

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    public AddContactFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddContactFragment newInstance(String param1, String param2) {
        AddContactFragment fragment = new AddContactFragment();
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

        View view= inflater.inflate(R.layout.fragment_add_contact, container, false);

        ContactDAO contactDAO = ContactDBInstance.getDatabase(requireContext()).contactDAO();

        MainActivityData mainActivityDataViewModel = new ViewModelProvider(getActivity()).
                get(MainActivityData.class);

        Button saveButton = view.findViewById(R.id.btnSave);
        Button backButton = view.findViewById(R.id.btnBack);
        Button addPhoto = view.findViewById(R.id.btnAddPhoto);
        EditText nameEditText = view.findViewById(R.id.contactName);
        EditText phoneEditText = view.findViewById(R.id.contactPhone);
        EditText emailEditText = view.findViewById(R.id.contactEmail);
        EditText addressEditText = view.findViewById(R.id.contactAddress);
        imageViewAddPhoto = view.findViewById(R.id.imageViewAddPhoto);


        //Setting the values of the edit text fields to the values stored in the view model
        //This is to ensure the value is displayed even when the orientation changes
        nameEditText.setText(mainActivityDataViewModel.getAddContactName());
        phoneEditText.setText(mainActivityDataViewModel.getAddContactPhone());
        emailEditText.setText(mainActivityDataViewModel.getAddContactEmail());
        addressEditText.setText(mainActivityDataViewModel.getAddContactAddress());

        if(mainActivityDataViewModel.getAddContactProfileImage()!=null ){
            Uri imageUri = Uri.parse(mainActivityDataViewModel.getAddContactProfileImage());
            imageViewAddPhoto.setImageURI(imageUri);
            newContact.setImageURI(String.valueOf(imageUri));
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 name = nameEditText.getText().toString();
                 phone = phoneEditText.getText().toString();
                 email = emailEditText.getText().toString();
                 address = addressEditText.getText().toString();

                //Setting the values of the new contact
                newContact.setName(name);
                newContact.setPhone(phone);
                newContact.setEmail(email);
                newContact.setAddress(address);

                //Inserting the new contact into the database
                contactDAO.insert(newContact);

                Toast.makeText(getActivity(), "Contact Saved", Toast.LENGTH_SHORT).show();

                //Setting the values of the add contact live data to null
                mainActivityDataViewModel.setAddContactName("");
                mainActivityDataViewModel.setAddContactPhone("");
                mainActivityDataViewModel.setAddContactEmail("");
                mainActivityDataViewModel.setAddContactAddress("");
                mainActivityDataViewModel.setAddContactProfileImage(null);

                //Going back to the main fragment
                mainActivityDataViewModel.setClickedValue(0);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Setting the values of the add contact live data to null
                mainActivityDataViewModel.setAddContactName("");
                mainActivityDataViewModel.setAddContactPhone("");
                mainActivityDataViewModel.setAddContactEmail("");
                mainActivityDataViewModel.setAddContactAddress("");
                mainActivityDataViewModel.setAddContactProfileImage(null);

                //Going back to the main fragment
                mainActivityDataViewModel.setClickedValue(0);
            }
        });

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Checking if permission is given to access the camera.If not permission is requested.
                if (ContextCompat.checkSelfPermission(requireContext(),
                        android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(requireActivity(),
                            new String[]{Manifest.permission.CAMERA},
                            REQUEST_IMAGE_CAPTURE);
                    Toast.makeText(view.getContext(), "No permission.Requesting Permission now.", Toast.LENGTH_SHORT).show();
                } else {
                    //Capturing the image if permission is already granted.
                    captureImage();
                }
            }
        });

        //Setting the name value of the add contact live data to the values entered by the user
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             mainActivityDataViewModel.setAddContactName(String.valueOf(nameEditText.getText()));
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });

        //Setting the phone value of the add contact live data to the values entered by the user
        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mainActivityDataViewModel.setAddContactPhone(String.valueOf(phoneEditText.getText()));
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        //Setting the email value of the add contact live data to the values entered by the user
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mainActivityDataViewModel.setAddContactEmail(String.valueOf(emailEditText.getText()));
            }

            @Override
            public void afterTextChanged(Editable editable) { }
        });

        //Setting the address value of the add contact live data to the values entered by the user
        addressEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mainActivityDataViewModel.setAddContactAddress(String.valueOf(addressEditText.getText()));
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        return view;
    }

    //Requesting permission from the user at run time to access the camera.
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                captureImage();
                Toast.makeText(requireContext(), "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Capturing the image
    public void captureImage(){

        //Creating a file to store the image
        photoFile = new File(getActivity().getFilesDir(),"photo.jpg");

        // Get a content URI using FileProvider for the given File
        Uri cameraUri = FileProvider.getUriForFile(requireContext(),
                requireContext().getPackageName() + ".fileprovider", photoFile);

        // Create an Intent to capture an image using the device's camera
        Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        photoIntent.putExtra(MediaStore.EXTRA_OUTPUT,cameraUri);

        PackageManager pm = requireActivity().getPackageManager();
        for (ResolveInfo resolveInfo : pm.queryIntentActivities(photoIntent, PackageManager.MATCH_DEFAULT_ONLY)) {
            requireActivity().grantUriPermission(resolveInfo.activityInfo.packageName, cameraUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }

        startActivityForResult(photoIntent, REQUEST_IMAGE_CAPTURE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            if (photoFile != null) {

                //Decode the image file into a Bitmap
                Bitmap photo = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                imageViewAddPhoto.setImageBitmap(photo);

                ContactDAO contactDAO = ContactDBInstance.getDatabase(requireContext()).contactDAO();
                List contacts = contactDAO.getAllContacts();
                int id = contacts.size();

                String saveUri= "photo_"+id ;

                //Create the file name for the image
                File uniquePhotoFile = new File(photoFile.getParent(), saveUri);
                photoFile.renameTo(uniquePhotoFile);

                Uri cameraUri = FileProvider.getUriForFile(requireContext(),
                        "com.example.contactsapp.fileprovider", uniquePhotoFile);

                MainActivityData mainActivityData = new ViewModelProvider(getActivity()).
                        get(MainActivityData.class);
                mainActivityData.setAddContactProfileImage(cameraUri.toString());

                newContact.setImageURI(cameraUri.toString());
                Toast.makeText(getActivity(), "Photo Added", Toast.LENGTH_SHORT).show();

            }
        }
        else
        Toast.makeText(getActivity(), "Photo didn't get added", Toast.LENGTH_SHORT).show();}
    }



