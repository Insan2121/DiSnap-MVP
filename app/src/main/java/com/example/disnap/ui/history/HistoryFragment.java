package com.example.disnap.ui.history;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.disnap.R;
import com.example.disnap.data.adapter.HistoryAdapter;
import com.example.disnap.data.pojo.Disease;
import com.example.disnap.data.repository.DataManager;
import com.example.disnap.data.repository.DiseaseRepository;
import com.example.disnap.ui.base.BaseFragment;
import com.example.disnap.ui.detaildiseaseInfo.DetailDiseaseInfoActivity;
import com.example.disnap.util.SendDataToHistoryAdapter;

import java.util.ArrayList;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

import static com.yalantis.ucrop.UCropFragment.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends BaseFragment implements HistoryView, HistoryAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private DiseaseRepository diseaseRepository;
    private HistoryPresenter historyPresenter;
    private LinearLayout llHistoryWarning;


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        findViews(view);
        initViews(view);
        initListener(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        diseaseRepository = DataManager.getInstance().getDiseaseFromDB();
        historyPresenter = new HistoryPresenter(this, diseaseRepository);
        historyPresenter.getAllHistory();
    }

    @Override
    public void findViews(View view) {
        recyclerView = view.findViewById(R.id.rc_disease_history);
        llHistoryWarning = view.findViewById(R.id.ll_history_warning);
    }

    @Override
    public void initViews(View view) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        historyAdapter = new HistoryAdapter(this, getContext());
        recyclerView.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();
    }

    @Override
    public void initListener(View view) {

    }

    @Override
    public void showHistory(final ArrayList<Disease> disease) {
        llHistoryWarning.setVisibility(View.GONE);
        Log.d(TAG, "showHistory: " + disease.get(0).getDiseaseName());
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                historyAdapter.setValues(disease);
            }
        });
    }

    @Override
    public void showErrorMessage(final String message) {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //llHistoryWarning.setText(message);
                llHistoryWarning.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void showRemoveSuccess(final String message) {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toasty.success(getContext(), message, Toasty.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showRemoveFailed(final String message) {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toasty.error(getContext(), message, Toasty.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void clickItem(Disease disease) {
        Log.d("fak", "onDiseaseClicked: " + disease.getDiseaseName());
        Intent intent = new Intent(getContext(), DetailDiseaseInfoActivity.class);
        intent.putExtra("Data", (Parcelable) disease);
        startActivity(intent);
    }

    @Override
    public void clickRemoveButton(final Disease disease) {
        historyPresenter.removeHistory(disease);
    }
}
