package com.example.disnap.ui.result;

import com.example.disnap.data.pojo.Disease;
import com.example.disnap.data.repository.DiseaseDataSource;
import com.example.disnap.data.repository.DiseaseRepository;
import com.example.disnap.ui.base.BasePresenter;

import java.lang.ref.WeakReference;

class ResultPresenter extends BasePresenter<ResultView> {
    private DiseaseRepository diseaseRepository;

    ResultPresenter(ResultView view, DiseaseRepository diseaseRepository) {
        super(view);
        this.diseaseRepository = diseaseRepository;
    }


    void insertHistoryToDB(Disease disease) {
        diseaseRepository.insertAnalysisResultToDB(new DiseaseInsertCallListener(view), disease);
    }

    public static class DiseaseInsertCallListener implements DiseaseDataSource.InsertAnalysisResultCallback {
        private static WeakReference<ResultView> view;

        private DiseaseInsertCallListener(ResultView view) {
            DiseaseInsertCallListener.view = new WeakReference<>(view);
        }

        @Override
        public void onInsertSuccess(String message) {
            if (view.get() == null) return;
            view.get().showInsertSucess(message);
        }

        @Override
        public void onInsertError(String message) {
            if (view.get() == null) return;
            view.get().showInsertFailed(message);
        }
    }


}
