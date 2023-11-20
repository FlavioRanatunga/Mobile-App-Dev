package com.example.assignment2;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {
    public MutableLiveData<String> search;
    public SearchViewModel(){
        search = new MutableLiveData<String>();
    }
    public String getSearch(){
        return search.getValue();
    }
    public void setSearch(String value){
        search.postValue(value);
    }
}
