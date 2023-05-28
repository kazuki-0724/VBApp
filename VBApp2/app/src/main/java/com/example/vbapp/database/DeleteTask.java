package com.example.vbapp.database;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.vbapp.GameRecord;

import java.lang.ref.WeakReference;


/**
 * データベースから特定のデータを削除する
 */
public class DeleteTask extends AsyncTask<GameRecord,Void,Integer> {

    private AppDataBase db;

    /**
     * コンストラクタ
     * @param db
     */
    public DeleteTask(AppDataBase db){
        this.db = db;
    }

    /**
     * データの削除
     * @param gameRecords 削除するデータ
     * @return
     */
    @Override
    protected Integer doInBackground(GameRecord... gameRecords) {

        GameRecordDao gameRecordDao = db.gameRecordDao();
        gameRecordDao.delete(gameRecords[0]);

        return 0;
    }
}
