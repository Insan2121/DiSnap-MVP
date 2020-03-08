package com.example.disnap.ui.detaildiseaseInfo;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.disnap.R;
import com.example.disnap.data.adapter.ViewPagerAdapter;
import com.example.disnap.data.pojo.Disease;
import com.example.disnap.ui.base.BaseActivity;
import com.example.disnap.ui.control.ControlFragment;
import com.example.disnap.ui.indication.IndicationFragment;
import com.example.disnap.ui.pesticide.PesticideFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class DetailDiseaseInfoActivity extends BaseActivity {
    Intent intent;
    Disease disease;
    private TextView name, latin;
    private ImageView btnBack, diseaseImg;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_disease_info);

        findViews();
        initViews();
        initListeners();
    }

    @Override
    public void findViews() {
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        name = findViewById(R.id.tv_name_disease_info_detail);
        latin = findViewById(R.id.tv_latin_disease_info_detail);
        diseaseImg = findViewById(R.id.img_desease_plant);
        btnBack = findViewById(R.id.btn_back_detailInfo);
    }

    @Override
    public void initViews() {
        intent = getIntent();
        if (intent.getParcelableExtra("Data") != null) {
            disease = intent.getParcelableExtra("Data");
            name.setText(Objects.requireNonNull(disease).getDiseaseName());
            latin.setText(disease.getDiseaseLatin());

            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(disease.getUserImage())
                    .into(diseaseImg);

            Log.d("TAG", "initViews: " + disease.getDiseaseName());
            showAllDetailDiseaseFragment(disease.getIndication(), disease.getControling(), disease.getPesticide());
        }
    }

    @Override
    public void initListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    void showAllDetailDiseaseFragment(String mIndication, String mControl, String mPesticide) {
        //attach tablayout with viewpager
        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        bundle = new Bundle();

        Fragment indicationFragment = new IndicationFragment();
        Fragment controlFragment = new ControlFragment();
        Fragment pesticideFragment = new PesticideFragment();


        bundle = new Bundle();
        bundle.putString("indication", mIndication);
        bundle.putString("control", mControl);
        bundle.putString("pesticide", mPesticide);

        indicationFragment.setArguments(bundle);
        controlFragment.setArguments(bundle);
        pesticideFragment.setArguments(bundle);

        viewPagerAdapter.addFragment(indicationFragment, "Gejala");
        viewPagerAdapter.addFragment(controlFragment, "Pengendalian");
        viewPagerAdapter.addFragment(pesticideFragment, "Pestisida");

        //set adapter on viewpager
        viewPager.setAdapter(viewPagerAdapter);
    }

}
