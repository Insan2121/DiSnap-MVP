package com.example.disnap.data.repository;

import com.example.disnap.data.pojo.Disease;
import com.example.disnap.data.repository.local.database.DiseaseDatabaseDataSource;
import com.example.disnap.data.repository.local.jsonfile.DiseaseJSONFileDataSource;
import com.example.disnap.data.repository.remote.DiseaseRemoteDataSource;

import java.util.ArrayList;

public class DiseaseRepository implements DiseaseDataSource {

    private DiseaseRemoteDataSource remoteDataSource = null;
    private DiseaseDatabaseDataSource diseaseDatabaseDataSource = null;
    private DiseaseJSONFileDataSource diseaseJSONFileDataSource = null;
    private static DiseaseRepository instance;
    private static DiseaseRepository instanceDB;
    private static DiseaseRepository instanceJSONFile;

    private DiseaseRepository(DiseaseRemoteDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    private DiseaseRepository(DiseaseDatabaseDataSource diseaseDatabaseDataSource) {
        this.diseaseDatabaseDataSource = diseaseDatabaseDataSource;
    }

    public DiseaseRepository(DiseaseJSONFileDataSource diseaseJSONFileDataSource) {
        this.diseaseJSONFileDataSource = diseaseJSONFileDataSource;
    }

    static DiseaseRepository getInstance(DiseaseRemoteDataSource diseaseRemoteDataSource) {
        if (instance == null) {
            instance = new DiseaseRepository(diseaseRemoteDataSource);
        }
        return instance;
    }

    static DiseaseRepository getInstanceDB(DiseaseDatabaseDataSource diseaseDatabaseDataSource) {
        if (instanceDB == null) {
            instanceDB = new DiseaseRepository(diseaseDatabaseDataSource);
        }
        return instanceDB;
    }

    static DiseaseRepository getInstanceJSONFile(DiseaseJSONFileDataSource diseaseJSONFileDataSource) {
        if (instanceJSONFile == null) {
            instanceJSONFile = new DiseaseRepository(diseaseJSONFileDataSource);
        }
        return instanceJSONFile;
    }



    @Override
    public void getDiseaseAnalysisResultFromDB(final LoadDiseaseCallback callback) {
        diseaseDatabaseDataSource.getDiseaseAnalysisResultFromDB(new LoadDiseaseCallback() {
            @Override
            public void onDiseaseLoaded(ArrayList<Disease> diseases) {
                callback.onDiseaseLoaded(diseases);
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });

    }

    @Override
    public void getDiseaseFromJSONFile(final LoadDiseaseFromJSONFileCallback callback) {
        diseaseJSONFileDataSource.getDiseaseFromJSONFile(new LoadDiseaseFromJSONFileCallback() {
            @Override
            public void onDiseaseLoaded(ArrayList<Disease> diseases) {
                callback.onDiseaseLoaded(diseases);
            }

            @Override
            public void onError() {
                callback.onError();
            }

            @Override
            public void onHideLoading() {
                callback.onHideLoading();
            }

            @Override
            public void onShowLoading() {
                callback.onShowLoading();
            }
        });

    }

    @Override
    public void insertAnalysisResultToDB(final InsertAnalysisResultCallback callback, Disease disease) {
        diseaseDatabaseDataSource.insertAnalysisResultToDB(new InsertAnalysisResultCallback() {
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
    public void removeHistoryFromDB(final RemoveHistoryCallback callback, Disease disease) {
        diseaseDatabaseDataSource.removeHistoryFromDB(new RemoveHistoryCallback() {
            @Override
            public void onRemoveSuccess(String message) {
                callback.onRemoveSuccess(message);
            }

            @Override
            public void onRemoveFailed(String message) {
                callback.onRemoveFailed(message);
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
