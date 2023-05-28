package com.example.vbapp.database;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.BaseAdapter;

import com.example.vbapp.GameRecord;
import com.example.vbapp.ui.GameListAdapter;

import java.lang.ref.WeakReference;
import java.util.List;

public class SelectTask extends AsyncTask<Void,Void,Integer > {

    //db
    private AppDataBase db;
    //gameRecordのリスト
    private List<GameRecord> gameRecordList;

    /**
     * コンストラクタ
     * @param db
     * @param gameRecordList
     */
    public SelectTask(AppDataBase db, List<GameRecord> gameRecordList){
        this.db = db;
        this.gameRecordList = gameRecordList;
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
