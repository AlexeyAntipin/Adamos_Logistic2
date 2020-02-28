package com.example.adamos_logistic.ui.Chat;

import android.content.Intent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.adamos_logistic.ChatActivity;
import com.example.adamos_logistic.MainMenuActivity;

public class ChatViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public ChatViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}