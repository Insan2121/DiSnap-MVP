package com.example.disnap.ui.pesticide;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.disnap.R;
import com.example.disnap.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PesticideFragment extends BaseFragment {
    private TextView tPesticide;
    private String mPesticide;

    public PesticideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pesticide, container, false);
        findViews(view);
        initViews(view);
        initListener(view);
        return view;
    }

    @Override
    public void findViews(View view) {
        tPesticide = view.findViewById(R.id.tv_pesticide);
    }

    @Override
    public void initViews(View view) {
        if (getArguments().getString("pesticide") != null){
            mPesticide = setString(getArguments().getString("pesticide"));
            tPesticide.setText(mPesticide);
        }
    }

    @Override
    public void initListener(View view) {

    }

    String setString(String pes){
        String res = pes;
        res = res.replace("]", "");
        res = res.replace("[", "");
        res = res.replace("," , "`\n\n`");
        return res;
    }
}
