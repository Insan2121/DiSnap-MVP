package com.example.disnap.ui.home;

import com.example.disnap.data.pojo.Disease;
import com.example.disnap.data.repository.DiseaseDataSource;
import com.example.disnap.data.repository.DiseaseRepository;
import com.example.disnap.ui.base.BasePresenter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


class HomePresenter extends BasePresenter<HomeView> {
    private DiseaseRepository diseaseRepository;


    HomePresenter(HomeView view, DiseaseRepository diseaseRepository) {
        super(view);
        this.diseaseRepository = diseaseRepository;
    }

    void getAllDiseaseInfo(){
        diseaseRepository.getDiseaseFromJSONFile(new DiseaseJSONFileDataCallListener(view));
    }

    public static class DiseaseJSONFileDataCallListener implements DiseaseDataSource.LoadDiseaseFromJSONFileCallback{
    private  WeakReference<HomeView> view;

        DiseaseJSONFileDataCallListener(HomeView view) {
            this.view = new WeakReference<>(view);
        }

        @Override
        public void onDiseaseLoaded(ArrayList<Disease> diseases) {
            if (view.get() == null) return;
            view.get().showDisease(diseases);
        }

        @Override
        public void onError() {
            if (view.get() == null) return;
            view.get().showErrorMessage();
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
    }

}
