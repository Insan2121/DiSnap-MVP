package com.example.disnap.ui.pesticide;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.disnap.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PesticideFragment extends Fragment {

    public PesticideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pesticide, container, false);
    }
}
