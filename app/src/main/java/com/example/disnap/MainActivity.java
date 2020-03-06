package com.example.disnap;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.androidnetworking.AndroidNetworking;
import com.example.disnap.ui.bottomsheetdialog.ActionBottomDialogFragment;
import com.example.disnap.ui.base.BaseActivity;
import com.example.disnap.ui.history.HistoryFragment;
import com.example.disnap.ui.home.HomeFragment;
import com.example.disnap.ui.snap.SnapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import io.isfaaghyth.rak.Rak;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static int selectedMenuId;
    private static Context context;
    private static MainActivity mainActivity;
    private androidx.fragment.app.Fragment home, history;
    private static BottomNavigationView bottomNavigationView;
    private static Fragment selectedFragment;
    private static FragmentTransaction fragmentTransaction;
    private SnapFragment snapFragment;
    private static ActionBottomDialogFragment fragment = new ActionBottomDialogFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Rak.initialize(getApplicationContext());
        AndroidNetworking.initialize(getApplicationContext());

        findViews();
        initViews();
        initListeners();
        bottomNavigationView.setSelectedItemId(R.id.menu_home);
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
                switch (menuItem.getItemId()){
                    case R.id.menu_home:
                        selectedFragment = home;
                        break;
                    case R.id.menu_snap:
                       // showBottomSheet();
                        //showBottomSheetFragment();
                        selectedFragment = snapFragment;
                        showBottomSheetFragment();
                        //selectedFragment = snapFragment;
                        //showBottomSheet();

                        break;
                    case R.id.menu_history:
                        selectedFragment = history;
                        break;
                }
                selectedMenuId  = menuItem.getItemId();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, selectedFragment);
                fragmentTransaction.commit();
                return true;
            }
        });
    }


    public void showBottomSheetFragment(){
        fragment.show(getSupportFragmentManager(), fragment.getTag());
        fragment.setCancelable(true);
    }

}
