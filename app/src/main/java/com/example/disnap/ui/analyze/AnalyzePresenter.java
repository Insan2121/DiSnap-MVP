package com.example.disnap.ui.analyze;

import com.example.disnap.data.pojo.Disease;
import com.example.disnap.data.repository.DiseaseDataSource;
import com.example.disnap.data.repository.DiseaseRepository;
import com.example.disnap.ui.base.BasePresenter;

import java.lang.ref.WeakReference;

class AnalyzePresenter extends BasePresenter<AnalyzeView> {
    private final DiseaseRepository diseaseRepository;

    AnalyzePresenter(AnalyzeView view, DiseaseRepository diseaseRepository) {
        super(view);
        this.diseaseRepository = diseaseRepository;
    }

    // network
    void AnalyzeImageFromRemote(String url) {
        diseaseRepository.analyzeImage(new DiseaseCallListener(view), url);
    }

    private static class DiseaseCallListener implements DiseaseDataSource.LoadAnalyzeCallback {
        private WeakReference<AnalyzeView> view;

        DiseaseCallListener(AnalyzeView view) {
            this.view = new WeakReference<>(view);
        }

        @Override
        public void onAnalyzeSuccess(Disease disease) {
            if (view.get() == null) return;
            view.get().showResultAnalyze(disease);
        }

        @Override
        public void onHideLoading() {
            if (view.get() == null) return;
            view.get().hideLoading();
        }

        @Override
        public void onShowLoading() {
            if (view.get() == null) return;
            view.get().showLoading();
        }


        @Override
        public void onError() {
            if (view.get() == null) return;
            view.get().showErrorMessage();
        }
    }


}
