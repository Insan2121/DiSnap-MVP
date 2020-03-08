package com.example.disnap.ui.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import io.isfaaghyth.rak.Rak;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Rak.initialize(getApplicationContext());
    }

    public abstract void findViews();
    public abstract void initViews();
    public abstract void initListeners();
}
