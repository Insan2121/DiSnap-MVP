package com.example.disnap.ui.history;

import android.content.Context;

import com.example.disnap.data.pojo.Disease;
import com.example.disnap.data.repository.DiseaseDataSource;
import com.example.disnap.data.repository.DiseaseRepository;
import com.example.disnap.ui.base.BasePresenter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

class HistoryPresenter extends BasePresenter<HistoryView> {

    private DiseaseRepository diseaseRepository;

    HistoryPresenter(HistoryView view, DiseaseRepository diseaseRepository) {
        super(view);
        this.diseaseRepository = diseaseRepository;
    }

    HistoryPresenter(HistoryView view) {
        super(view);
    }


    void getAllHistory() {
        diseaseRepository.getDiseaseAnalysisResultFromDB(new DiseaseCallListener(view));
    }


    private static class DiseaseCallListener implements DiseaseDataSource.LoadDiseaseCallback {
        private WeakReference<HistoryView> view;

        private DiseaseCallListener(HistoryView view) {
            this.view = new WeakReference<>(view);
        }

        @Override
        public void onDiseaseLoaded(ArrayList<Disease> diseases) {
            if (view.get() == null) return;
            view.get().showHistory(diseases);
        }

        @Override
        public void onError() {
            if (view.get() == null) return;
            view.get().showErrorMessage();
        }
    }
}
