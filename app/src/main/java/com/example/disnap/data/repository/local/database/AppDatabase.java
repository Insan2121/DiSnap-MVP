package com.example.disnap.data.repository.local.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.disnap.App;
import com.example.disnap.data.pojo.Disease;
import com.example.disnap.data.repository.local.database.dao.DiseaseDAO;

@Database(entities = {Disease.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DiseaseDAO diseaseDAO();

    private static AppDatabase instance;

    static AppDatabase getDatabaseInstance() {
        if (instance == null) {
            instance = Room.databaseBuilder(App.getContext(), AppDatabase.class, "DiseaseDB")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
