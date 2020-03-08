package com.example.disnap.ui.base;

import com.example.disnap.ui.analyze.AnalyzeView;

public abstract class BasePresenter<View extends BaseView> {
    protected View view;



    protected BasePresenter(View view) {
        this.view = view;
    }

    void destroyView() {
        //avoid memory leak
        view = null;
    }
}
