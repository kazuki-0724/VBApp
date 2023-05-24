package com.example.vbapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GameRecord {

    //主キー
    @PrimaryKey(autoGenerate = true)
    private int id;
    //試合名(必須)
    @ColumnInfo(name = "matchName")
    private String matchName;
    //試合日(自動2023/04/25)
    @ColumnInfo(name = "gameDay")
    private String gameDay;
    //元URL
    @ColumnInfo(name = "url")
    private String url;
    //ファイルの内部URI
    @ColumnInfo(name = "uri")
    private String uri;

    @ColumnInfo(name = "leagueType")
    private int leagueType;


    public GameRecord(String matchName, String gameDay, String url, int leagueType){
        this.matchName = matchName;
        this.gameDay = gameDay;
        this.url = url;
        this.leagueType = leagueType;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatchName() {
        return this.matchName;
    }

    public void setMatchName(String name) {
        this.matchName = name;
    }

    public String getGameDay() {
        return this.gameDay;
    }

    public void setGameDay(String gameDay) {
        this.gameDay = gameDay;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String URI) {
        this.uri = URI;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String URL) {
        this.url = URL;
    }

    public int getLeagueType(){
        return this.leagueType;
    }

    public void setLeagueType(int leagueType){
        this.leagueType = leagueType;
    }

}


