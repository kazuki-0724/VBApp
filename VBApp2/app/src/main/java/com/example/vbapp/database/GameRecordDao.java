package com.example.vbapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.vbapp.GameRecord;

import java.util.List;


/**
 * データベースの具体的な処理
 */


@Dao
public interface GameRecordDao {

    @Query("SELECT * FROM gamerecord")
    List<GameRecord> getAll();

    @Insert
    void insert(GameRecord gameRecord);

    @Update
    void update(GameRecord gameRecord);


    @Delete
    void delete(GameRecord gameRecord);


}
