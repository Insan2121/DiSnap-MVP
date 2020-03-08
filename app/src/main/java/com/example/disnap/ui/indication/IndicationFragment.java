package com.example.disnap.ui.indication;

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
public class IndicationFragment extends BaseFragment {
    private TextView tIndication;

    public IndicationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_indication, container, false);
        findViews(view);
        initViews(view);
        initViews(view);
        return view;
    }

    @Override
    public void findViews(View view) {
        tIndication = view.findViewById(R.id.tv_indication);
    }

    @Override
    public void initViews(View view) {
        if (getArguments().getString("indication") != null) {
            String mIndication = getArguments().getString("indication");
            tIndication.setText(mIndication);
        }
    }

    @Override
    public void initListener(View view) {

    }
}
