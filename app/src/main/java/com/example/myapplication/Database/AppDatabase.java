package com.example.myapplication.Database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.myapplication.Models.Movies;

@android.arch.persistence.room.Database(entities = Movies.class, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{

    public abstract DataAO MovieDAO ();
    private static AppDatabase database;

    public static AppDatabase getDatabase (Context context) {
        if (database == null){
            database = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "Local_Database")
                    .fallbackToDestructiveMigration()
                 //  .allowMainThreadQueries()
                    .build();
        }
        return database;
    }
}
