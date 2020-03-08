package com.example.disnap.ui.control;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.disnap.R;
import com.example.disnap.ui.base.BaseFragment;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControlFragment extends BaseFragment {

    private TextView tControl;

    public ControlFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        findViews(view);
        initViews(view);
        initListener(view);
        return view;
    }

    @Override
    public void findViews(View view) {
        tControl = view.findViewById(R.id.tv_control);
    }

    @Override
    public void initViews(View view) {
        if (Objects.requireNonNull(getArguments()).getString("control") != null) {
            String mControl = getArguments().getString("control");
            tControl.setText(setString(mControl));
        }
    }

    @Override
    public void initListener(View view) {

    }

    private String setString(String pes) {
        String res = pes;
        res = res.replace("]", "");
        res = res.replace("[", "");
        res = res.replace(",", "\n\n");
        return res;
    }
}
