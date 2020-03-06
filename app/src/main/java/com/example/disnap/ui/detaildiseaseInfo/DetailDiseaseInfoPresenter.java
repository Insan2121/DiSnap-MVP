package com.example.disnap.ui.detaildiseaseInfo;

public class DetailDiseaseInfoPresenter implements DetailDiseaseInfoContract.Presenter {
    private DetailDiseaseInfoContract.View mView;

    public DetailDiseaseInfoPresenter(DetailDiseaseInfoContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void addControlFragment(String dataControl) {
    }

    @Override
    public void addIndicationFragment(String dataIndication) {
    }

    @Override
    public void addPesticideFragment(String dataPesticide) {
    }

    @Override
    public void start() {

    }
}
