package com.example.disnap.ui.snap;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.disnap.R;
import com.example.disnap.ui.base.BaseFragment;
import com.example.disnap.ui.bottomsheetdialog.ActionBottomDialogFragment;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SnapFragment extends BaseFragment {
    private ImageView btnSnap;
    private static ActionBottomDialogFragment fragment = new ActionBottomDialogFragment();


    public SnapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_snap, container, false);
        findViews(view);
        initViews(view);
        initListener(view);
        return view;
    }

    @Override
    public void findViews(View view) {
        btnSnap = view.findViewById(R.id.btn_snap);
    }

    @Override
    public void initViews(View view) {
        btnSnap.setClickable(true);
    }

    @Override
    public void initListener(View view) {
        btnSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Clicked", Toast.LENGTH_SHORT).show();
                showBottomSheetFragment();
            }
        });
    }

    private void showBottomSheetFragment() {
        fragment.show(Objects.requireNonNull(getFragmentManager()), fragment.getTag());
        fragment.setCancelable(true);
    }
}
