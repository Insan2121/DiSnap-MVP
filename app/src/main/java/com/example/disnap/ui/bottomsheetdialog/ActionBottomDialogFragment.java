package com.example.disnap.ui.bottomsheetdialog;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.disnap.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;
import java.util.Objects;

import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActionBottomDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private static final int CAMERA_REQUEST = 1000;
    private static final int GALERY_REQUEST = 2000;

    public ActionBottomDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_action_bottom_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TextView llCamera = view.findViewById(R.id.viewCamera);
        TextView llGallery = view.findViewById(R.id.viewGallery);

        llCamera.setOnClickListener(this);
        llGallery.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.viewCamera:
                checkIfAnyCamera();
                break;
            case R.id.viewGallery:
                Toast.makeText(getContext(), "Gallery clicked", Toast.LENGTH_SHORT).show();
                galleryPermission();
                break;
        }
    }

    //fungsi permission pada camera
    private void cameraPermission() {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        openCamera();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        openSettingsDialog();
                    }
                }).check();
    }

    //fungsi permission pada gallery
    private void galleryPermission() {
        Dexter.withActivity(getActivity())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        openGallery();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toasty.warning(Objects.requireNonNull(getContext()), "Open setting to accepted permission", Toasty.LENGTH_SHORT).show();
                        openSettingsDialog();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    //fungsi melakukan cek terhadap keberadaan camera
    private void checkIfAnyCamera() {
        Context context = getContext();
        assert context != null;
        PackageManager packageManager = context.getPackageManager();
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            Toast.makeText(getActivity(), "This device does not have a camera.", Toast.LENGTH_SHORT)
                    .show();
        } else {
            cameraPermission();
        }
    }

    //fungsi membuka camera
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(Objects.requireNonNull(getContext()).getPackageManager()) != null) {
            Objects.requireNonNull(getActivity()).startActivityForResult(takePictureIntent, CAMERA_REQUEST);
        }
    }

    //fungsi membuka gallery
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Objects.requireNonNull(getActivity()).startActivityForResult(intent, GALERY_REQUEST);
        Log.d("statusReqFromFrag  : ", Integer.toString(GALERY_REQUEST));
    }

    //fungsi untuk popup permission
    private void openSettingsDialog() {
        new AlertDialog.Builder(getContext()).setTitle("Permissions Required")
                .setMessage("Permission is required for using this app. Please enable them in app settings.")
                .setPositiveButton("Go to setting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showsettings();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    //fungsi membuka setting
    private void showsettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", Objects.requireNonNull(getActivity()).getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }
}

