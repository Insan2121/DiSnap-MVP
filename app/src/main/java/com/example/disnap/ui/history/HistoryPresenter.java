package com.example.disnap.ui.history;

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


    void getAllHistory() {
        diseaseRepository.getDiseaseAnalysisResultFromDB(new DiseaseCallListener(view));
    }

    void removeHistory(Disease disease){
        diseaseRepository.removeHistoryFromDB(new DiseaseCallListener(view), disease);
    }


    private static class DiseaseCallListener implements DiseaseDataSource.LoadDiseaseCallback, DiseaseDataSource.RemoveHistoryCallback {
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
        public void onError(String message) {
            if (view.get() == null) return;
            view.get().showErrorMessage(message);
        }


        @Override
        public void onRemoveSuccess(String message) {
            if (view.get() == null) return;
            view.get().showRemoveSuccess(message);
        }

        @Override
        public void onRemoveFailed(String message) {
            view.get().showRemoveFailed(message);
        }
    }
}
