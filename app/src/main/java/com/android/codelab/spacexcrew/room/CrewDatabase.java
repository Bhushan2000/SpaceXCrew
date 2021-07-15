package com.android.codelab.spacexcrew.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = CrewEntity.class, version = 1,exportSchema = false)
public abstract class CrewDatabase extends RoomDatabase {

    public abstract CrewDAO crewDAO();

    public static CrewDatabase crewDatabase;

    public static CrewDatabase getInstance(Context context) {
        if (crewDatabase == null) {
            crewDatabase = buildDatabaseInstance(context);

        }
        return crewDatabase;

    }

    private static CrewDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context, CrewDatabase.class, "CrewDatabase.db").allowMainThreadQueries().build();

    }

    public void cleanup() {
        crewDatabase = null;
    }

}
