package com.example.disnap.ui.result;

import com.example.disnap.ui.base.BaseView;

public interface ResultView extends BaseView {
    void showInsertSucess(String message);

    void showInsertFailed(String message);
}
