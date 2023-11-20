package com.example.assignment2;

import android.graphics.Bitmap;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class ImageListViewModel extends ViewModel {
    public MutableLiveData<ArrayList<Bitmap>> imageList;
    public MutableLiveData<Bitmap> selectedImg = new MutableLiveData<>();
    private MutableLiveData<Boolean> loadFlag = new MutableLiveData<>();
    private int columnView;

    public ImageListViewModel() {
        imageList = new MutableLiveData<>();
        imageList.setValue(new ArrayList<>());
        columnView = 1;
    }

    public MutableLiveData<ArrayList<Bitmap>> getImageListLiveData() {
        return imageList;
    }

    public ArrayList<Bitmap> getImageList() {
        return imageList.getValue();
    }

    public void setSelectedImage(Bitmap selectedImage) {
        selectedImg.postValue(selectedImage);
    }
    public void setLoadFlag(boolean flag) { loadFlag.postValue(flag); }

    public int getColumnView()
    {
        return columnView;
    }

    public void setColumnView(int columnView)
    {
        this.columnView = columnView;
    }

    public void addImage(Bitmap image) {
        ArrayList<Bitmap> currentList = imageList.getValue();
        if (currentList == null) {
            currentList = new ArrayList<>();
        }
        currentList.add(image);
        imageList.postValue(currentList);
    }

    public void clearImageList() {
        ArrayList<Bitmap> currentList = imageList.getValue();
        if (currentList != null) {
            currentList.clear();
            imageList.setValue(currentList);
            setLoadFlag(false);
        }
    }

    public Bitmap getSelectedImage() {
        return selectedImg.getValue();
    }

    public int getImageCount() {
        ArrayList<Bitmap> currentList = imageList.getValue();
        if (currentList != null) {
            return currentList.size();
        } else {
            return 0; // Return 0 if the list is null or empty.
        }
    }

    public MutableLiveData<Boolean> getLoadFlag() {
        return loadFlag;
    }

    public void loaderDone() {
        loadFlag.postValue(true);
    }
}
