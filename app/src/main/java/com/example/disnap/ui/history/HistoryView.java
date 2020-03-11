package com.example.disnap.ui.history;

import com.example.disnap.data.pojo.Disease;
import com.example.disnap.ui.base.BaseView;

import java.util.ArrayList;

public interface HistoryView extends BaseView {
    void showHistory(ArrayList<Disease> disease);

    void showErrorMessage(String message);

    void showRemoveSuccess(String message);

    void showRemoveFailed(String message);
}
