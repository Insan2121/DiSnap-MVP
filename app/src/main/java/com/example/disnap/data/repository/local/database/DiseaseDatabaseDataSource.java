package com.example.disnap.data.repository.local.database;

import android.util.Log;

import com.example.disnap.data.pojo.Disease;
import com.example.disnap.data.repository.DiseaseDataSource;
import com.example.disnap.util.DiskExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Executor;

public class DiseaseDatabaseDataSource implements DiseaseDataSource {
    private Executor executor;
    private static DiseaseDatabaseDataSource instance;
    private int prevDataSize;
    private int presentDataSize;

    private DiseaseDatabaseDataSource(Executor executor) {
        this.executor = executor;
    }

    public static DiseaseDatabaseDataSource getInstance() {
        if (instance == null) {
            instance = new DiseaseDatabaseDataSource(new DiskExecutor());
        }
        return instance;
    }


    @Override
    public void insertAnalysisResultToDB(final InsertAnalysisResultCallback callback, final Disease disease) {
        prevDataSize = getDataSize();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                long status = AppDatabase.getDatabaseInstance().diseaseDAO().insertDiseaseToDB(disease);
                Log.d("cekstatusinsert", status + "");
                presentDataSize = getDataSize();
                if (presentDataSize > prevDataSize) {
                    callback.onInsertSuccess("Hasil analisis dapat dilihat di menu riwayat");
                } else {
                    callback.onInsertError("Gagal memasukan hasil analsisi ke dalam database");
                }
            }
        };
        executor.execute(runnable);
    }

    @Override
    public void removeHistoryFromDB(final RemoveHistoryCallback callback, final Disease disease) {
        prevDataSize = getDataSize();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                AppDatabase.getDatabaseInstance().diseaseDAO().delete(disease);
                presentDataSize = getDataSize();
                if (presentDataSize < prevDataSize) {
                    callback.onRemoveSuccess("Riwayat berhasil dihapus");
                } else {
                    callback.onRemoveFailed("Riwayat gagal dihapus");
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
                    callback.onError("Tidak ada riwayat deteksi");
                }
            }
        };
        executor.execute(runnable);
    }

    @Override
    public void getDiseaseFromJSONFile(LoadDiseaseFromJSONFileCallback callback) {

    }


    private int getDataSize() {
        ArrayList<Disease> diseases = new ArrayList<>(Arrays.asList(AppDatabase.getDatabaseInstance().diseaseDAO().selectAllHistory()));
        //Log.d(TAG, "getNameken:" + diseases.get(diseases.size() - 1).getDiseaseName());
        return diseases.size();
    }


}
