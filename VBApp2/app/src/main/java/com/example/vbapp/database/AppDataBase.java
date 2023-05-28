package com.example.vbapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.vbapp.GameRecord;

/**
 * データベース処理の初期クラス
 */
@Database(entities = {GameRecord.class},version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract GameRecordDao gameRecordDao();
}
