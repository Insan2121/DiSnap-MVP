package com.example.disnap.data.repository;

import com.example.disnap.data.repository.local.database.AppDatabase;
import com.example.disnap.data.repository.local.database.DiseaseDatabaseDataSource;
import com.example.disnap.data.repository.local.database.dao.DiseaseDAO;
import com.example.disnap.data.repository.local.jsonfile.DiseaseJSONFileDataSource;
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
        //DiseaseDAO diseaseDAO = AppDatabase.getDatabaseInstance().diseaseDAO();
        DiseaseDatabaseDataSource diseaseDatabaseDataSource = DiseaseDatabaseDataSource.getInstance();
        return DiseaseRepository.getInstanceDB(diseaseDatabaseDataSource);
    }

    public DiseaseRepository getDiseaseFromJSON(){
        DiseaseJSONFileDataSource diseaseJSONFileDataSource = DiseaseJSONFileDataSource.getInstance();
        return DiseaseRepository.getInstanceJSONFile(diseaseJSONFileDataSource);
    }
}
