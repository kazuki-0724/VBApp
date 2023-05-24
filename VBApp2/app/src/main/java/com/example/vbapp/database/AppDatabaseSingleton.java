package com.example.vbapp.database;

import android.content.Context;

import androidx.room.Room;

public class AppDatabaseSingleton {

    private static AppDataBase instance = null;

    public static AppDataBase getInstance(Context context){
        if(instance != null){
            return  instance;
        }

        instance = Room.databaseBuilder(context,AppDataBase.class,"gamerecords")
                .fallbackToDestructiveMigration().build();

        return  instance;
    }
}
