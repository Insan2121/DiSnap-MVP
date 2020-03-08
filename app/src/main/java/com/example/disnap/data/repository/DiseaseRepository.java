package com.example.disnap.data.repository;
import com.example.disnap.data.pojo.Disease;
import com.example.disnap.data.repository.local.database.DiseaseLocalDataSource;
import com.example.disnap.data.repository.remote.DiseaseRemoteDataSource;

import java.util.ArrayList;

public class DiseaseRepository implements DiseaseDataSource {

    private DiseaseRemoteDataSource remoteDataSource = null;
    private DiseaseLocalDataSource diseaseLocalDataSource = null;
    private static DiseaseRepository instance;
    private static DiseaseRepository instanceDB;

    private DiseaseRepository(DiseaseRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    private DiseaseRepository(DiseaseLocalDataSource diseaseLocalDataSource) {
        this.diseaseLocalDataSource = diseaseLocalDataSource;
    }

    static DiseaseRepository getInstance(DiseaseRemoteDataSource diseaseRemoteDataSource) {
        if (instance == null) {
            instance = new DiseaseRepository(diseaseRemoteDataSource);
        }
        return instance;
    }

    static DiseaseRepository getInstanceDB(DiseaseLocalDataSource diseaseLocalDataSource) {
        if (instanceDB == null) {
            instanceDB = new DiseaseRepository(diseaseLocalDataSource);
        }
        return instanceDB;
    }


    @Override
    public void getDiseaseAnalysisResultFromDB(final LoadDiseaseCallback callback) {
        diseaseLocalDataSource.getDiseaseAnalysisResultFromDB(new LoadDiseaseCallback() {
            @Override
            public void onDiseaseLoaded(ArrayList<Disease> diseases) {
                callback.onDiseaseLoaded(diseases);
            }

            @Override
            public void onError() {
                callback.onError();
            }
        });

    }

    @Override
    public void insertAnalysisResultToDB(final InsertAnalysisResultCallback callback, Disease disease) {
        diseaseLocalDataSource.insertAnalysisResultToDB(new InsertAnalysisResultCallback() {
            @Override
            public void onInsertSuccess(String message) {
                callback.onInsertSuccess(message);
            }

            @Override
            public void onInsertError(String message) {
                callback.onInsertError(message);
            }
        }, disease);
    }

    @Override
    public void analyzeImage(final LoadAnalyzeCallback callback, String img) {
        remoteDataSource.analyzeImage(new LoadAnalyzeCallback() {
            @Override
            public void onAnalyzeSuccess(Disease disease) {
                callback.onAnalyzeSuccess(disease);
            }

            @Override
            public void onHideLoading() {
                callback.onHideLoading();
            }

            @Override
            public void onShowLoading() {
                callback.onShowLoading();
            }

            @Override
            public void onError() {
                callback.onError();
            }
        }, img);
    }


}
