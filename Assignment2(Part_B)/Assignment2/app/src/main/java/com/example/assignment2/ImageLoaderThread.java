package com.example.assignment2;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Arrays;

public class ImageLoaderThread extends Thread{
    private RemoteUtils remoteUtils;
    private SearchViewModel sViewModel;
    private ImageListViewModel imageViewModel;
    private ErrorViewModel errorViewModel;
    private Activity activity;
    int count;

    public ImageLoaderThread(Activity activity, SearchViewModel viewModel, ImageListViewModel imageViewModel, ErrorViewModel errorViewModel) {
        remoteUtils = RemoteUtils.getInstance(activity);
        this.sViewModel = viewModel;
        this.imageViewModel = imageViewModel;
        this.errorViewModel = errorViewModel;
        this.activity=activity;
    }

    public void run(){
        Log.d("ImageLoaderThread", "Thread started");
        String endpoint[] = getEndpoint(sViewModel.getSearch());
        Log.d("Endpooint", Arrays.toString(endpoint));
        if(endpoint.length == 0){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    errorViewModel.setErrorCode(1);
                    Toast.makeText(activity,"Images not found",Toast.LENGTH_LONG).show();
                }
            });
            //return;
        }
        else {
            for(int i = 0; i < endpoint.length; i++)
            {
                Bitmap image = getImageFromUrl(endpoint[i]);
                imageViewModel.addImage(image);
                Log.d("ImageLoaderThread", "Added image to ImageListViewModel");
            }
            //Bitmap image = getImageFromUrl(endpoint);
            try {
                Thread.sleep(3000);
            }
            catch (Exception e) {
            }
            count = imageViewModel.getImageCount();
            Log.d("ImageListViewModel", "Number of images in the list: " + count);
            imageViewModel.loaderDone();
        }
    }

    private String[] getEndpoint(String data) {
        String[] imageUrls = null;
        try {
            JSONObject jBase = new JSONObject(data);
            JSONArray jHits = jBase.getJSONArray("hits");

            int nHits = 0;
            if(jHits.length()<15) {
                nHits = jHits.length();
            }
            else {
                nHits = 15;
            }
            imageUrls = new String[nHits];

            if (jHits.length()>0) {
                for (int i = 0; i < nHits; i++) {
                    JSONObject jHitsItem = jHits.getJSONObject(i);
                    imageUrls[i] = jHitsItem.getString("previewURL");
                    Log.d("ImageURL", imageUrls[i]);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return imageUrls; // Return null in case of any errors or when no URLs are found.
    }

    public Bitmap getBitmapFromConnection(HttpURLConnection conn) {
        Bitmap data = null;
        try {
            InputStream inputStream = conn.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] byteData = new byte[4096];
            while ((nRead = inputStream.read(byteData, 0, byteData.length)) != -1) {
                buffer.write(byteData, 0, nRead);
            }
            byte[] imageData = buffer.toByteArray();
            data = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    private Bitmap getImageFromUrl(String imageUrl){
        Bitmap image = null;
        Uri.Builder url = Uri.parse(imageUrl).buildUpon();
        String urlString = url.build().toString();
        HttpURLConnection connection = remoteUtils.openConnection(urlString);
        if(connection!=null){
            if(remoteUtils.isConnectionOkay(connection)==true){
                image = getBitmapFromConnection(connection);
                connection.disconnect();
            }
        }
        return image;
    }


}
