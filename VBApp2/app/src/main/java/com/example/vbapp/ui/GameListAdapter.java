package com.example.vbapp.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vbapp.GameRecord;
import com.example.vb.R;

import java.util.List;

public class GameListAdapter extends BaseAdapter {

    //レイアウト処理するための
    private LayoutInflater layoutInflater = null;
    //gameのレコードを追記
    private List<GameRecord> gameRecordList;


    //コンストラクタ
    public GameListAdapter(Context context, List<GameRecord> gameRecordList){
        this.gameRecordList = gameRecordList;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return gameRecordList.size();
    }


    @Override
    public Object getItem(int i) {
        return gameRecordList.get(i);
    }

    @Override
    public long getItemId(int i) {
        //return gameRecordList.get(i).getId();
        return i;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        //新たにViewを差し替える
        view = layoutInflater.inflate(R.layout.game_record_row,viewGroup,false);


        //今選択されたレコードを取得
        GameRecord gr = gameRecordList.get(position);

        //こっから下で上書き処理をする
        ((TextView)view.findViewById(R.id.match_name)).setText(gr.getMatchName());
        ((TextView)view.findViewById(R.id.match_day)).setText(gr.getGameDay());

        //背景のxmlを切り替え
        switch(gr.getLeagueType()){
            case 1:
                //Vリーグ用のxmlに切り替え
                //view.setBackgroundColor(Color.WHITE);
                view.findViewById(R.id.linear_layout_item).setBackgroundResource(R.drawable.list_item_vleague);
                ((TextView) view.findViewById(R.id.match_name)).setTextColor(Color.WHITE);
                break;
            case 2:
                //イタリア用 ここでレイアウトの差し替えを行う
                view.findViewById(R.id.linear_layout_item).setBackgroundResource(R.drawable.list_item_italy);
                break;
            case 3:
                //その他
                view.setBackgroundColor(Color.GRAY);
                ((TextView) view.findViewById(R.id.match_name)).setTextColor(Color.WHITE);
                break;
        }

        //Animation objectを作成
        // XMLで定義したアニメーションを読み込む
        Animation anim = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_motion);
        // リストアイテムのアニメーションを開始
        view.startAnimation(anim);

        return view;
    }



}
