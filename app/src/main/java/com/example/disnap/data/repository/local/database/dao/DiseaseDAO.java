package com.example.disnap.data.repository.local.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.disnap.data.pojo.Disease;

@Dao
public interface DiseaseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertDiseaseToDB(Disease history);

    @Query("SELECT * FROM tHistory")
    public Disease[] selectAllHistory();

    @Query("DELETE FROM tHistory WHERE id = :id")
    void deleteDiseaseById(int id);

    @Delete
    void delete(Disease history);
}
