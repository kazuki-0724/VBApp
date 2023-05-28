package com.example.vbapp.database;

import android.content.Context;

import androidx.room.Room;


/**
 * インスタンス生成のためのシングルトンクラス
 */
public class AppDatabaseSingleton {

    private static AppDataBase instance = null;

    /**
     * 重複しないようにインスタンスを生成して返す
     * @param context
     * @return
     */
    public static AppDataBase getInstance(Context context){
        if(instance != null){
            return  instance;
        }

        //gamerecordsという名前でテーブル作成
        instance = Room.databaseBuilder(context,AppDataBase.class,"gamerecords")
                .fallbackToDestructiveMigration().build();

        return  instance;
    }
}
