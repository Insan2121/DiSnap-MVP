package com.example.disnap.ui.home;

import com.example.disnap.data.pojo.Disease;
import com.example.disnap.ui.base.BaseView;

import java.util.ArrayList;

public interface HomeView extends BaseView {
    void showDisease(ArrayList<Disease> disease);
    void showLoading();
    void hideLoading();
    void showErrorMessage();
}
