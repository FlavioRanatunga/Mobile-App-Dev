package com.example.assignment2;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;

import java.net.HttpURLConnection;

public class SearchThread extends Thread {

    private String searchtxt;
    private String baseUrl;
    private RemoteUtils remoteUtils;
    private SearchViewModel viewModel;
    public SearchThread(String searchKey, Activity uiActivity, SearchViewModel viewModel) {
        this.searchtxt = searchKey;
        baseUrl ="https://pixabay.com/api/";
        remoteUtils = RemoteUtils.getInstance(uiActivity);
        this.viewModel = viewModel;
    }
    public void run(){
        Log.d("SearchThread", "Thread started");
        String endpoint = getSearchEndpoint();
        Log.d("SearchThread", "Endpoint: " + endpoint);
        HttpURLConnection connection = remoteUtils.openConnection(endpoint);
        if(connection!=null){
            if(remoteUtils.isConnectionOkay(connection)==true){
                String response = remoteUtils.getResponseString(connection);
                connection.disconnect();
                try {
                    Thread.sleep(4000);
                }
                catch (Exception e){

                }
                viewModel.setSearch(response);
            }
        }

    }
    private String getSearchEndpoint(){
        String data = null;
        Uri.Builder url = Uri.parse(this.baseUrl).buildUpon();
        url.appendQueryParameter("key","39746215-82780a9c79e310b3504628cf2");
        url.appendQueryParameter("q",this.searchtxt);
        String urlString = url.build().toString();
        return urlString;
    }




}
