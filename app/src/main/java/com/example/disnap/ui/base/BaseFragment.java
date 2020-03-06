package com.example.disnap.ui.base;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract void findViews(View view);
    public abstract void initViews(View view);
    public abstract void initListener(View view);

}
