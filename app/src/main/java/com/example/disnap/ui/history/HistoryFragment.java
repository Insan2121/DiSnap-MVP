package com.example.disnap.ui.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.disnap.R;
import com.example.disnap.data.adapter.HistoryAdapter;
import com.example.disnap.data.pojo.Disease;
import com.example.disnap.data.repository.DataManager;
import com.example.disnap.data.repository.DiseaseRepository;
import com.example.disnap.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.Objects;

import static com.yalantis.ucrop.UCropFragment.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends BaseFragment implements HistoryView, HistoryContract.OnItemClickListener {
    private RecyclerView recyclerView;
    private HistoryAdapter historyAdapter;
    private TextView tvHistory;

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
        DiseaseRepository diseaseRepository = DataManager.getInstance().getDiseaseFromDB();
        HistoryPresenter historyPresenter = new HistoryPresenter(this, diseaseRepository);
        historyPresenter.getAllHistory();
    }

    @Override
    public void findViews(View view) {
        recyclerView = view.findViewById(R.id.rc_disease_history);
        tvHistory = view.findViewById(R.id.tv_info_history);
    }

    @Override
    public void initViews(View view) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        historyAdapter = new HistoryAdapter(this);
        recyclerView.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();


    }

    @Override
    public void initListener(View view) {

    }

    @Override
    public void showHistory(final ArrayList<Disease> disease) {
        Log.d(TAG, "showHistory: " + disease.get(0).getDiseaseName());
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*historyAdapter = new HistoryAdapter(disease);
                recyclerView.setAdapter(historyAdapter);
                historyAdapter.notifyDataSetChanged();*/
                historyAdapter.setValues(disease);
            }
        });
    }

    @Override
    public void showErrorMessage() {
        tvHistory.setVisibility(View.VISIBLE);
    }

    @Override
    public void clickItem(Disease disease) {

    }
}
