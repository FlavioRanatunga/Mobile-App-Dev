package com.example.assignment2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class UploadThread extends Thread{
    private ImageListViewModel imageViewModel;
    private Activity activity;
    private ErrorViewModel errorViewModel;

    public UploadThread(Activity activity, ImageListViewModel imageViewModel, ErrorViewModel errorViewModel) {
        this.imageViewModel = imageViewModel;
        this.errorViewModel = errorViewModel;
        this.activity = activity;
    }

    //Reference : https://github.com/firebase/snippets-android/blob/fc886ab7a7fef7d9d9ace46d9236bd4d1f982246/storage/app/src/main/java/com/google/firebase/referencecode/storage/StorageActivity.java#L176-L196
    //              https://firebase.google.com/docs/storage/android/upload-files
    public void run(){
        Log.d("UploadThread", "Thread started");

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        Bitmap selectedImage = imageViewModel.getSelectedImage();
        long currentTimeMillis = System.currentTimeMillis();

        // Convert the current time to a formatted string
        String currentTimeString = new SimpleDateFormat("HHmmss", Locale.US).format(new Date(currentTimeMillis));

        // Create a unique file name using current time and a random number
        String imageName = "image_" + currentTimeString + "_" + new Random().nextInt(1000) + ".jpg";
        //String imageName = "unique_image_name2.jpg";
        StorageReference imageRef = storageRef.child(imageName);
        StorageReference imagePathRef = storageRef.child("images/"+imageName);

        // While the file names are the same, the references point to different files
        imageRef.getName().equals(imagePathRef.getName());    // true
        imageRef.getPath().equals(imagePathRef.getPath());    // false

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();

        UploadTask uploadTask = imagePathRef.putBytes(imageData);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads (e.g., show an error message)
                Log.e("UploadThread", "Image upload failed: " + exception.getMessage());
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,"Image Upload failed",Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful uploads (e.g., retrieve the download URL)
                Log.d("UploadThread", "Image uploaded successfully");
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity,"Image Uploaded",Toast.LENGTH_LONG).show();
                    }
                });

                // Retrieving download url
                imagePathRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri downloadUri) {
                        // The download URL of the uploaded image
                        String downloadUrl = downloadUri.toString();
                        Log.d("UploadThread", "Download URL: " + downloadUrl);
                    }
                });
            }
        });

        }

}


