package com.example.disnap.data.repository;

public class DataManager {
    private static DataManager sInstance;

    public static synchronized DataManager getInstance() {
        if (sInstance == null) {
            sInstance = new DataManager();
        }
        return sInstance;
    }

    /*public AnalyzeRepository analyzeImage(){
        AnalyzeDataSource analyzeDataSource =
    }*/
}
