package com.example.disnap.ui.base;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import java.util.Objects;

import io.isfaaghyth.rak.Rak;

public abstract class BaseFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Rak.initialize(Objects.requireNonNull(getActivity()).getApplicationContext());
    }

    public abstract void findViews(View view);
    public abstract void initViews(View view);
    public abstract void initListener(View view);

}
