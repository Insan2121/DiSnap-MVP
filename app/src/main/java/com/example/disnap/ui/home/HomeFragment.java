package com.example.disnap.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.disnap.R;
import com.example.disnap.data.adapter.DiseaseAdapter;
import com.example.disnap.data.pojo.Disease;
import com.example.disnap.ui.base.BaseFragment;
import com.example.disnap.ui.detaildiseaseInfo.DetailDiseaseInfoActivity;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import static com.yalantis.ucrop.UCropFragment.TAG;

public class HomeFragment extends BaseFragment implements HomeContract.View, HomeContract.OnItemClickListener {

    private HomeContract.Presenter presenter;
    private DiseaseAdapter diseaseAdapter;
    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;




    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.populateDiseaseInfo();


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public void findViews(View view) {
        shimmerFrameLayout = view.findViewById(R.id.shimmer);
        recyclerView = view.findViewById(R.id.rc_disease_info);
    }

    @Override
    public void initViews(View view) {

        presenter = new HomePresenter(this, getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        diseaseAdapter = new DiseaseAdapter(this);
        recyclerView.setAdapter(diseaseAdapter);
        diseaseAdapter.notifyDataSetChanged();

    }

    @Override
    public void initListener(View view) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        findViews(view);
        initViews(view);
        initListener(view);
        return view;
    }


    @Override
    public void setDiseaseInfo(ArrayList<Disease> diseaseInfo) {
        diseaseAdapter.setValues(diseaseInfo);
    }

    @Override
    public void showProgress() {
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void hideProgress() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.hideShimmer();
            }
        }, 1500);
    }

    @Override
    public void showDetailDiseaseInfoActivity(Disease disease) {
        Intent intent = new Intent(getContext(), DetailDiseaseInfoActivity.class);
        intent.putExtra("Data", disease);
        startActivity(intent);
    }


    @Override
    public void clickItem(Disease disease) {
        Log.d(TAG, "clickItem: "+disease.getDiseaseName());
        presenter.openDetailDiseaseInfoActivity(disease);
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
