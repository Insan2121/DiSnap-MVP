package com.example.disnap.ui.result;

import com.example.disnap.data.pojo.Disease;
import com.example.disnap.ui.base.BaseView;

import java.util.ArrayList;

public interface ResultContract {
    interface Presenter {
        void populateDiseaseHistory();
        void openDetailDiseaseInfoActivity(Disease disease);
    }

    interface View extends BaseView {
        void setDiseaseInfo(ArrayList<Disease> diseaseInfo);
        void showProgress();
        void hideProgress();
        void showDetailDiseaseInfoActivity(Disease disease);
    }

    interface OnItemClickListener {
        void clickItem(Disease disease);
    }
}
