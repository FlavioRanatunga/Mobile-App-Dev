package com.example.contactsapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityData  extends ViewModel {

    //This variable is used to store the value of the button clicked by the user.
    public MutableLiveData<Integer> clickedValue;

    //This variable is used to store the id of the contact clicked by the user.
    public MutableLiveData<Integer> currentContactId;

    //Live data to store add contact information temporarily to display when orientation changes.
    public  MutableLiveData<String> addContactName ;
    public  MutableLiveData<String> addContactPhone;
    public  MutableLiveData<String> addContactEmail ;
    public  MutableLiveData<String> addContactAddress ;
    public  MutableLiveData<String> addContactProfileImage ;

    //Live data to store edit contact information temporarily to display when orientation changes.
    public  MutableLiveData<String> editContactName ;
    public  MutableLiveData<String> editContactPhone;
    public  MutableLiveData<String> editContactEmail ;
    public  MutableLiveData<String> editContactAddress ;

    public MainActivityData(){
        clickedValue = new MutableLiveData<Integer>();
        clickedValue.setValue(0);
        currentContactId = new MutableLiveData<Integer>();
        currentContactId.setValue(null);

        addContactName = new MutableLiveData<String>();
        addContactName.setValue("");
        addContactPhone = new MutableLiveData<String>();
        addContactPhone.setValue("");
        addContactEmail = new MutableLiveData<String>();
        addContactEmail.setValue("");
        addContactAddress = new MutableLiveData<String>();
        addContactAddress.setValue("");
        addContactProfileImage = new MutableLiveData<String>();
        addContactProfileImage.setValue(null);

        editContactName = new MutableLiveData<String>();
        editContactName.setValue(null);
        editContactPhone = new MutableLiveData<String>();
        editContactPhone.setValue(null);
        editContactEmail = new MutableLiveData<String>();
        editContactEmail.setValue(null);
        editContactAddress = new MutableLiveData<String>();
        editContactAddress.setValue(null);
    }

    //Getters and Setters
    public int getClickedValue(){
        return clickedValue.getValue();
    }

    public void setClickedValue(int value){
        clickedValue.setValue(value);
    }

    public int getCurrentContactId() { return currentContactId.getValue(); }

    public void setCurrentContactId(int value) { currentContactId.setValue(value); }

    public String getAddContactName() { return addContactName.getValue(); }

    public void setAddContactName(String value) { addContactName.setValue(value); }

    public String getAddContactPhone() { return addContactPhone.getValue(); }

    public void setAddContactPhone(String value) { addContactPhone.setValue(value); }

    public String getAddContactEmail() { return addContactEmail.getValue(); }

    public void setAddContactEmail(String value) { addContactEmail.setValue(value); }

    public String getAddContactAddress() { return addContactAddress.getValue(); }

    public void setAddContactAddress(String value) { addContactAddress.setValue(value); }

    public String getAddContactProfileImage() { return addContactProfileImage.getValue(); }

    public void setAddContactProfileImage(String value) { addContactProfileImage.setValue(value); }

    public String getEditContactName() { return editContactName.getValue(); }

    public void setEditContactName(String value) { editContactName.setValue(value); }

    public String getEditContactPhone() { return editContactPhone.getValue(); }

    public void setEditContactPhone(String value) { editContactPhone.setValue(value); }

    public String getEditContactEmail() { return editContactEmail.getValue(); }

    public void setEditContactEmail(String value) { editContactEmail.setValue(value); }

    public String getEditContactAddress() { return editContactAddress.getValue(); }

    public void setEditContactAddress(String value) { editContactAddress.setValue(value); }

}
