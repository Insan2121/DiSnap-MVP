package com.example.disnap.data.repository;

import com.example.disnap.data.repository.local.database.AppDatabase;
import com.example.disnap.data.repository.local.database.DiseaseLocalDataSource;
import com.example.disnap.data.repository.local.database.dao.DiseaseDAO;
import com.example.disnap.data.repository.remote.DiseaseRemoteDataSource;

public class DataManager {
    private static DataManager sInstance;

    public static synchronized DataManager getInstance() {
        if (sInstance == null) {
            sInstance = new DataManager();
        }
        return sInstance;
    }


    public DiseaseRepository analyzeImageRepositoryRemote(){
        DiseaseRemoteDataSource remoteDataSource = DiseaseRemoteDataSource.getInstance();
        return DiseaseRepository.getInstance(remoteDataSource);
    }

    public DiseaseRepository getDiseaseFromDB(){
        DiseaseDAO diseaseDAO = AppDatabase.getDatabaseInstance().diseaseDAO();
        DiseaseLocalDataSource diseaseLocalDataSource = DiseaseLocalDataSource.getInstance();
        return DiseaseRepository.getInstanceDB(diseaseLocalDataSource);
    }
}
