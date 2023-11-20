package com.example.tic_tac_toe;

import android.widget.ImageView;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.HashMap;

public class MainActivityData extends ViewModel{

    public MutableLiveData<Integer> clickedValue;
    public MutableLiveData<Boolean> profileBtnFlag ;

    public MutableLiveData<String> name = new MediatorLiveData<>();
    public MutableLiveData<Integer> clickedImageNumber ;
    private int[][] board;

    private final MutableLiveData<Integer> orientationLiveData = new MutableLiveData<>();

    // Expose LiveData for observing in the fragment
    public MutableLiveData<Integer> getOrientationLiveData() {
        return orientationLiveData;
    }

    // Update the screen orientation
    public void setOrientation(int orientation) {
        orientationLiveData.setValue(orientation);
    }
    public boolean isMultiplayerFlag() {
        return multiplayerFlag;
    }

    public void setMultiplayerFlag(boolean multiplayerFlag) {
        this.multiplayerFlag = multiplayerFlag;
    }

    private  boolean multiplayerFlag;




    public MainActivityData(){
        clickedValue = new MediatorLiveData<Integer>();
        clickedValue.setValue(0);
        profileBtnFlag = new MediatorLiveData<Boolean>();
        profileBtnFlag.setValue(false);
        name.setValue("");
        clickedImageNumber = new MediatorLiveData<Integer>();
        clickedImageNumber.setValue(0);
    }

    public int getClickedValue(){
        return clickedValue.getValue();
    }
    public void setClickedValue(int value){
        clickedValue.setValue(value);
    }
    public MutableLiveData<Boolean> getProfileBtnFlag(){
        return profileBtnFlag;
    }
    public void setProfileBtnFlag(boolean value){
        profileBtnFlag.setValue(value);
    }

    public String getName(){
        return name.getValue();
    }

    public void setName(String value){
        name.setValue(value);
    }
    public void setClickedImageNumber(int value) {clickedImageNumber.setValue(value);}
    public MutableLiveData<Integer> getClickedImageNumber() {return clickedImageNumber;}
}
