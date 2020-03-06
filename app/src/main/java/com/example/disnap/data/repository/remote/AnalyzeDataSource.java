package com.example.disnap.data.repository.remote;

import com.example.disnap.data.pojo.Disease;

public interface AnalyzeDataSource {
    interface LoadAnalyzeCallback{
        void onAnalyzeSuccess();
        void onError();
        void onLoading();
    }

    void saveToDB(Disease disease);
}
