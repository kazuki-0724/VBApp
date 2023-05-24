package com.example.vbapp.database;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.vbapp.GameRecord;

import java.lang.ref.WeakReference;

public class DeleteTask extends AsyncTask<GameRecord,Void,Integer> {

    private AppDataBase db;
    private WeakReference weakReference;


    public DeleteTask(AppDataBase db, Activity activity){
        this.db = db;
        this.weakReference = new WeakReference<>(activity);
    }

    @Override
    protected Integer doInBackground(GameRecord... gameRecords) {

        GameRecordDao gameRecordDao = db.gameRecordDao();
        gameRecordDao.delete(gameRecords[0]);

        return 0;
    }
}
