package com.example.disnap.ui.result;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.disnap.data.repository.DataManager;
import com.example.disnap.data.repository.DiseaseRepository;
import com.example.disnap.ui.MainActivity;
import com.example.disnap.R;
import com.example.disnap.data.adapter.ViewPagerAdapter;
import com.example.disnap.data.pojo.Disease;
import com.example.disnap.ui.base.BaseActivity;
import com.example.disnap.ui.control.ControlFragment;
import com.example.disnap.ui.indication.IndicationFragment;
import com.example.disnap.ui.pesticide.PesticideFragment;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import technolifestyle.com.imageslider.FlipperLayout;
import technolifestyle.com.imageslider.FlipperView;

import static com.yalantis.ucrop.UCropFragment.TAG;

public class ResultActivity extends BaseActivity implements ResultView {
    private TextView tvDiseaseName;
    private TextView tvLatinName;
    private TextView tvAccuration;
    private ImageView imgResult;
    private ImageView imgUser;
    private ImageView btnBack;

    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        findViews();
        initViews();
        initListeners();

    }

    @Override
    public void findViews() {
        tvDiseaseName = findViewById(R.id.tv_name_disease_info_detail);
        tvDiseaseName = findViewById(R.id.tv_result_name_disease_);
        tvLatinName = findViewById(R.id.tv_result_latin_disease);
        tvAccuration = findViewById(R.id.tv_result_accuration);
        imgResult = findViewById(R.id.img_result_data);
        imgUser = findViewById(R.id.img_result_user_photo);

        tabLayout = findViewById(R.id.tab_layout_result);
        viewPager = findViewById(R.id.view_pager_result);
        btnBack = findViewById(R.id.btn_back_detail_result);
    }

    @Override
    public void initViews() {
        Intent intent = getIntent();
        Disease disease = intent.getParcelableExtra("Result");

        saveToDB(disease);

        tvDiseaseName.setText(Objects.requireNonNull(disease).getDiseaseName());
        tvLatinName.setText(disease.getDiseaseLatin());
        tvAccuration.setText(getPersentageResult(disease.getAccuration()));

        Picasso.get()
                .load(disease.getResultImage())
                .into(imgResult);

        Picasso.get()
                .load(disease.getUserImage())
                .into(imgUser);

        Log.d(TAG, "1onResponse0: " + disease.getDiseaseName());
        Log.d(TAG, "1onResponse1: " + disease.getDiseaseLatin());
        Log.d(TAG, "1onResponse2: " + disease.getAccuration());
        Log.d(TAG, "1onResponse3: " + disease.getResultImage());
        Log.d(TAG, "1onResponse4: " + disease.getUserImage());
        Log.d(TAG, "1onResponse5: " + disease.getIndication());
        Log.d(TAG, "1onResponse6: " + disease.getControling());
        Log.d(TAG, "1onResponse7: " + disease.getPesticide());
        Log.d(TAG, "1onResponse8: " + disease.getDate());

        showAllDetailDiseaseFragment(disease.getIndication(), disease.getControling(), disease.getPesticide());

    }

    @Override
    public void initListeners() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMainMenu();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToMainMenu();
    }

    public String getPersentageResult(double value) {
        double result = value * 100;
        return Double.toString(result);
    }

    void goToMainMenu() {
        startActivity(new Intent(ResultActivity.this, MainActivity.class));
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
    }

    void saveToDB(Disease disease) {
        DiseaseRepository diseaseRepository = DataManager.getInstance().getDiseaseFromDB();
        ResultPresenter historyPresenter = new ResultPresenter(this, diseaseRepository);
        historyPresenter.insertHistoryToDB(disease);

    }

    @Override
    public void showInsertSucess(String message) {
        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showInsertFailed(String message) {
        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}
