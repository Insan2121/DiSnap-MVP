package com.example.disnap.ui.detaildiseaseInfo;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.example.disnap.R;
import com.example.disnap.data.adapter.HeightWrappingViewPager;
import com.example.disnap.data.adapter.ViewPagerAdapter;
import com.example.disnap.data.pojo.Disease;
import com.example.disnap.ui.base.BaseActivity;
import com.example.disnap.ui.control.ControlFragment;
import com.example.disnap.ui.indication.IndicationFragment;
import com.example.disnap.ui.pesticide.PesticideFragment;
import com.google.android.material.tabs.TabLayout;
import java.util.Objects;

public class DetailDiseaseInfoActivity extends BaseActivity {
    private TextView name, latin;
    private ImageView btnBack, diseaseImg;
    private HeightWrappingViewPager viewPager;
    private TabLayout tabLayout;
    private ImagePopup imagePopup;

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
        Intent intent = getIntent();
        if (intent.getParcelableExtra("Data") != null) {
            Disease disease = intent.getParcelableExtra("Data");
            name.setText(Objects.requireNonNull(disease).getDiseaseName());
            latin.setText(disease.getDiseaseLatin());

            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(disease.getUserImage())
                    .into(diseaseImg);

            imagePopup = new ImagePopup(this);

            imagePopup.setWindowHeight(800);
            imagePopup.setWindowWidth(800);
            imagePopup.setBackgroundColor(Color.BLACK);
            imagePopup.setFullScreen(true);
            imagePopup.setHideCloseIcon(true);
            imagePopup.setImageOnClickClose(true);

            imagePopup.initiatePopupWithGlide(disease.getUserImage());

            Log.d("TAG", "initViews: " + disease.getDiseaseName());
            showAllDetailDiseaseFragment(disease.getIndication(), disease.getControling(), disease.getPesticide());
        }
    }

    @Override
    public void initListeners() {
        diseaseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePopup.viewPopup();
            }
        });

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
        Bundle bundle;

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
        viewPager.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

}
