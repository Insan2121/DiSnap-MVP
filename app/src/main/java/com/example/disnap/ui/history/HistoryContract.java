package com.example.disnap.ui.history;

import com.example.disnap.data.pojo.Disease;

public interface HistoryContract {
    interface OnItemClickListener {
        void clickItem(Disease disease);
    }
}
