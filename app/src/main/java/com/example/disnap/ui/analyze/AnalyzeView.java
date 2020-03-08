package com.example.disnap.ui.analyze;

import com.example.disnap.data.pojo.Disease;
import com.example.disnap.ui.base.BaseView;

public interface AnalyzeView extends BaseView {
    void showResultAnalyze(Disease disease);
    void showLoading();
    void hideLoading();
    void showErrorMessage();
}
