package com.example.disnap.data.repository;

import com.example.disnap.data.pojo.Disease;

import java.util.ArrayList;

public interface DiseaseDataSource {
    interface LoadAnalyzeCallback {
        void onAnalyzeSuccess(Disease disease);

        void onHideLoading();

        void onShowLoading();

        void onError();
    }


    interface LoadDiseaseCallback {
        void onDiseaseLoaded(ArrayList<Disease> diseases);

        void onError(String message);
    }

    interface LoadDiseaseFromJSONFileCallback {
        void onDiseaseLoaded(ArrayList<Disease> diseases);

        void onError();

        void onHideLoading();

        void onShowLoading();
    }

    interface InsertAnalysisResultCallback {
        void onInsertSuccess(String message);

        void onInsertError(String message);
    }

    interface RemoveHistoryCallback {
        void onRemoveSuccess(String message);

        void onRemoveFailed(String message);
    }


    void insertAnalysisResultToDB(InsertAnalysisResultCallback callback, Disease disease);

    void removeHistoryFromDB(RemoveHistoryCallback callback, Disease disease);

    void analyzeImage(LoadAnalyzeCallback callback, String img);

    void getDiseaseAnalysisResultFromDB(LoadDiseaseCallback callback);

    void getDiseaseFromJSONFile(LoadDiseaseFromJSONFileCallback callback);
}
