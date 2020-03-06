package com.example.disnap.ui.detaildiseaseInfo;

import com.example.disnap.ui.base.BasePresenter;
import com.example.disnap.ui.base.BaseView;

public interface DetailDiseaseInfoContract {
    interface Presenter extends BasePresenter{
        void addControlFragment(String dataControl);
        void addIndicationFragment(String dataIndication);
        void addPesticideFragment(String dataPesticide);
    }
    interface View extends BaseView<DetailDiseaseInfoContract.Presenter>{
        void showControlFragment();
        void showIndicationFragment();
        void showPesticideFragment();
    }
}
