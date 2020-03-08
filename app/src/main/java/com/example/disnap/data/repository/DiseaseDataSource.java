package com.example.disnap.data.repository;
import com.example.disnap.data.pojo.Disease;
import java.util.ArrayList;

public interface DiseaseDataSource {
    interface LoadAnalyzeCallback{
        void onAnalyzeSuccess(Disease disease);
        void onHideLoading();
        void onShowLoading();
        void onError();
    }


    interface LoadDiseaseCallback{
        void onDiseaseLoaded(ArrayList<Disease> diseases);
        void onError();
    }

    interface InsertAnalysisResultCallback{
        void onInsertSuccess(String message);
        void onInsertError(String message);
    }

    void insertAnalysisResultToDB(InsertAnalysisResultCallback callback,  Disease disease);

    void analyzeImage(LoadAnalyzeCallback callback, String img);
   void getDiseaseAnalysisResultFromDB(LoadDiseaseCallback callback);
}
