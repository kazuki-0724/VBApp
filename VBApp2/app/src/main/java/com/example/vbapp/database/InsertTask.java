package com.example.vbapp.database;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.vbapp.GameRecord;

import java.lang.ref.WeakReference;

public class InsertTask extends AsyncTask<GameRecord,Void,Integer> {

    private WeakReference<Activity> weakReference;
    private AppDataBase db;


    public InsertTask(AppDataBase db, Activity activity){
        this.db = db;
        this.weakReference = new WeakReference<>(activity);
    }


    /**
     * データを挿入するInsert処理
     * @param gameRecords
     * @return
     */
    @Override
    protected Integer doInBackground(GameRecord... gameRecords) {

        GameRecordDao gameRecordDao = db.gameRecordDao();
        gameRecordDao.insert(gameRecords[0]);

        return 0;
    }
}
