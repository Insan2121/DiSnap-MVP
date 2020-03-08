package com.example.disnap.data.repository.local.database;

import android.util.Log;

import com.example.disnap.data.pojo.Disease;
import com.example.disnap.data.repository.DiseaseDataSource;
import com.example.disnap.util.DiskExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executor;

import static com.yalantis.ucrop.UCropFragment.TAG;

public class DiseaseLocalDataSource implements DiseaseDataSource {
    private Executor executor;
    private static DiseaseLocalDataSource instance;

    private DiseaseLocalDataSource(Executor executor) {
        this.executor = executor;
    }

    public static DiseaseLocalDataSource getInstance() {
        if (instance == null) {
            instance = new DiseaseLocalDataSource(new DiskExecutor());
        }
        return instance;
    }


    @Override
    public void insertAnalysisResultToDB(final InsertAnalysisResultCallback callback, final Disease disease) {
        final int prevDataSize = getDataSize();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                long status = AppDatabase.getDatabaseInstance().diseaseDAO().insertDiseaseToDB(disease);
                Log.d("cekstatusinsert", status + "");
                int presentDataSize = getDataSize();
                if (presentDataSize > prevDataSize) {
                    callback.onInsertSuccess("Success to Added");
                } else {
                    callback.onInsertError("Failed to Added");
                }
            }
        };
        executor.execute(runnable);
    }

    @Override
    public void analyzeImage(LoadAnalyzeCallback callback, String img) {

    }


    @Override
    public void getDiseaseAnalysisResultFromDB(final LoadDiseaseCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ArrayList<Disease> diseases = new ArrayList<>(Arrays.asList(AppDatabase.getDatabaseInstance().diseaseDAO().selectAllHistory()));
                if (diseases.size() != 0) {
                    callback.onDiseaseLoaded(diseases);
                } else {
                    callback.onError();
                }
            }
        };
        executor.execute(runnable);
    }


    private int getDataSize() {
        ArrayList<Disease> diseases = new ArrayList<>(Arrays.asList(AppDatabase.getDatabaseInstance().diseaseDAO().selectAllHistory()));
        Log.d(TAG, "getNameken:" + diseases.get(diseases.size() - 1).getDiseaseName());
        return diseases.size();
    }


}
