package com.example.disnap.ui.result;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.disnap.data.adapter.ImageViewPagerAdapter;
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
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.text.DecimalFormat;
import java.util.Objects;

public class ResultActivity extends BaseActivity implements ResultView {
    private TextView tvDiseaseName;
    private TextView tvLatinName;
    private TextView tvAccuration;
    private ImageView btnBack;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private ViewPager viewPagerImage;
    private WormDotsIndicator wormDotsIndicator;

    private ProgressBar progressBar;


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

        tabLayout = findViewById(R.id.tab_layout_result);
        viewPager = findViewById(R.id.view_pager_result);
        btnBack = findViewById(R.id.btn_back_detail_result);

        viewPagerImage = findViewById(R.id.viewPagerImage);
        wormDotsIndicator = findViewById(R.id.worm_dots_indicator);

        progressBar = findViewById(R.id.pgAcuration);

    }

    @Override
    public void initViews() {
        Intent intent = getIntent();
        Disease disease = intent.getParcelableExtra("Result");

        saveToDB(disease);

        tvDiseaseName.setText(Objects.requireNonNull(disease).getDiseaseName());
        tvLatinName.setText(disease.getDiseaseLatin());
        tvAccuration.setText(getPersentageResult(disease.getAccuration()));

        int progress = (int) (disease.getAccuration() * 100);
        Log.d("akurasicek", "initViews: " + progress);
        progressBar.setProgress(progress);

        String[][] urlImgAndDesc = {{disease.getResultImage(), "Result Image"}, {disease.getUserImage(), "Your Image"}};
        Log.d("akurasicek12", "initViews: " + urlImgAndDesc[0][0] + urlImgAndDesc[1][0]);

        ImageViewPagerAdapter imageViewPagerAdapter = new ImageViewPagerAdapter(this, urlImgAndDesc);
        viewPagerImage.setAdapter(imageViewPagerAdapter);
        wormDotsIndicator.setViewPager(viewPagerImage);

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
        //return Double.toString(result);
        Log.d("sun1", "getPersentageResult: "+result);
        Log.d("sun2", "getPersentageResult: "+new DecimalFormat("##.##").format(result));
        return new DecimalFormat("##.##").format(result);
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
