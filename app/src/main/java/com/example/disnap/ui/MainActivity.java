package com.example.disnap.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.coderfolk.multilamp.customView.MultiLamp;
import com.coderfolk.multilamp.model.Target;
import com.coderfolk.multilamp.shapes.Circle;
import com.example.disnap.App;
import com.example.disnap.R;
import com.example.disnap.ui.analyze.AnalyzeActivity;
import com.example.disnap.ui.bottomsheetdialog.ActionBottomDialogFragment;
import com.example.disnap.ui.base.BaseActivity;
import com.example.disnap.ui.history.HistoryFragment;
import com.example.disnap.ui.home.HomeFragment;
import com.example.disnap.ui.snap.SnapFragment;
import com.example.disnap.util.Constants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import io.isfaaghyth.rak.Rak;

public class MainActivity extends BaseActivity {
    public static int selectedMenuId;
    private androidx.fragment.app.Fragment home, history;
    private static BottomNavigationView bottomNavigationView;
    private static Fragment selectedFragment;
    private static FragmentTransaction fragmentTransaction;
    private SnapFragment snapFragment;
    private static ActionBottomDialogFragment fragment = new ActionBottomDialogFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Rak.initialize(App.getContext());
        AndroidNetworking.initialize(App.getContext());

        findViews();
        initViews();
        initListeners();
        bottomNavigationView.setSelectedItemId(R.id.menu_home);

        if (Rak.grab("spotlight") == null){
            showSpotLight();
        }
    }

    @Override
    public void findViews() {
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
    }

    @Override
    public void initViews() {
        home = new HomeFragment();
        history = new HistoryFragment();
        snapFragment = new SnapFragment();
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
    }

    @Override
    public void initListeners() {
        selectedMenuId = R.id.menu_home;
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.menu_home:
                        selectedFragment = home;
                        break;
                    case R.id.menu_snap:
                        selectedFragment = snapFragment;
                        showBottomSheetFragment();
                        break;
                    case R.id.menu_history:
                        selectedFragment = history;
                        break;
                }
                selectedMenuId = menuItem.getItemId();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, selectedFragment);
                fragmentTransaction.commit();
                return true;
            }
        });
    }


    public void showBottomSheetFragment() {
        fragment.show(getSupportFragmentManager(), fragment.getTag());
        fragment.setCancelable(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.GALERY_REQUEST) {
                Uri a = Objects.requireNonNull(data).getData();
                startCrop(Objects.requireNonNull(a));
            } else if (requestCode == Constants.CAMERA_REQUEST) {
                Uri a = Objects.requireNonNull(data).getData();
                startCrop(Objects.requireNonNull(a));
            } else if (requestCode == Constants.CROP_REQUEST) {
                Uri c = UCrop.getOutput(Objects.requireNonNull(data));
                String imgUri = getRealPathFromUri(c);
                Intent intent = new Intent(this, AnalyzeActivity.class);
                intent.putExtra("imageUri", imgUri);
                startActivity(intent);
                this.finish();
            }
        } else {
            Toast.makeText(getApplicationContext(), "There is something wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromUri(Uri uri) {
        String result;
        Cursor cursor = getApplicationContext().getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    private void startCrop(@NonNull Uri uri) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getApplicationContext().getCacheDir(), imageFileName)));
        uCrop.withAspectRatio(1, 1);
        uCrop.withMaxResultSize(450, 450);
        uCrop.start(MainActivity.this, Constants.CROP_REQUEST);
    }

    void showSpotLight(){
        View viewHome = findViewById(R.id.menu_home);
        View viewAnalyze = findViewById(R.id.menu_snap);
        View viewHistory = findViewById(R.id.menu_history);
        MultiLamp multiLamp = new MultiLamp(this);
        ArrayList<Target> targets = new ArrayList<>();
        targets.add(new Target(viewHome, "You can read \n" +
                "list of chili \n" +
                "disease information here", MultiLamp.TOP, new Circle(40)));

        targets.add(new Target(viewAnalyze,"Let's you analyze \n" +
                "image from camera \n" +
                "or gallery to \n" +
                "know the chili \n" +
                "disease here", MultiLamp.TOP, new Circle(40)));

        targets.add(new Target(viewHistory, "Let you look \n" +
                "back you're analyze \n" +
                "activity here", MultiLamp.TOP, new Circle(40)));
        multiLamp.build(targets);

        Rak.entry("spotlight", true);
    }



}
