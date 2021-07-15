package com.android.codelab.spacexcrew.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface CrewDAO {
    @Insert
    void insertCrew(CrewEntity crewEntity);

    @Delete
    void deleteCrew(CrewEntity crewEntity);

    @Query("SELECT * FROM CrewEntity ORDER BY id DESC")
    List<CrewEntity> getAll();



    @Query("DELETE FROM CrewEntity")
    void deleteAll();

}
