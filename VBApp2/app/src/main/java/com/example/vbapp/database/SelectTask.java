package com.example.vbapp.database;

import android.app.Activity;
import android.os.AsyncTask;

import com.example.vbapp.GameRecord;
import com.example.vbapp.ui.GameListAdapter;

import java.lang.ref.WeakReference;
import java.util.List;

public class SelectTask extends AsyncTask<Void,Void,Integer > {

    private WeakReference<Activity> weakReference;
    private AppDataBase db;
    private List<GameRecord> gameRecordList;
    private GameListAdapter gameListAdapter;


    public SelectTask(AppDataBase db, Activity activity,List<GameRecord> gameRecordList,
                        GameListAdapter gameListAdapter){
        this.db = db;
        this.weakReference = new WeakReference<>(activity);
        this.gameRecordList = gameRecordList;
        this.gameListAdapter = gameListAdapter;
    }


    @Override
    protected Integer doInBackground(Void... voids) {

        GameRecordDao gameRecordDao = db.gameRecordDao();
        List<GameRecord> gameRecords =gameRecordDao.getAll();

        for(GameRecord gr:gameRecords){
            gameRecordList.add(gr);
        }

        return null;
    }
}
