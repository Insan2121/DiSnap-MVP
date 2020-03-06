package com.example.disnap.ui.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public abstract void findViews();
    public abstract void initViews();
    public abstract void initListeners();
}
