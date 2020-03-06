package com.example.disnap.ui.home;

import com.example.disnap.data.pojo.Disease;
import com.example.disnap.ui.base.BasePresenter;
import com.example.disnap.ui.base.BaseView;

import java.util.ArrayList;

public interface HomeContract {
    interface Presenter extends BasePresenter {
        void populateDiseaseInfo();
    }

    interface View extends BaseView<HomeContract.Presenter> {
        void setDiseaseInfo(ArrayList<Disease> diseaseInfo);
        public void showProgress();
        public void hideProgress();
    }

    interface OnItemClickListener {
        void clickItem(Disease disease);
    }
}
