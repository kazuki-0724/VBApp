package com.example.vbapp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.vb.R;
import com.example.vbapp.ui.GameScheduleAddDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * グリッドのカレンダーの制御を行っているクラス
 */
public class CalendarAdapter extends BaseAdapter {
    private List<Date> dateArray = new ArrayList();
    private Context mContext;
    private DateManager mDateManager;
    private LayoutInflater mLayoutInflater;

    private FragmentManager fragmentManager;
    private FragmentActivity fragmentActivity;

    private GameScheduleAddDialogFragment gameScheduleAddDialogFragment;

    private TextView dayTextView;

    private TextView matchScheTextView;

    private List<GameRecord> gameScheduleList;




    //カスタムセルを拡張したらここでWigetを定義
    private static class ViewHolder {
        public TextView dateText;
    }

    public CalendarAdapter(Context context, FragmentActivity fragmentActivity,
                           List<GameRecord> gameScheduleList){
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mDateManager = new DateManager();
        dateArray = mDateManager.getDays();
        this.fragmentActivity = fragmentActivity;
        this.gameScheduleList = gameScheduleList;
    }

    @Override
    public int getCount() {
        return dateArray.size();
    }


    /**
     * 一つのセルのViewの再定義
     * @param position 何個目のcellなのか
     * @param convertView 元のview
     * @param parent 親要素
     * @return 定義し直したviewを返す
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.calendar_cell, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }


        //セルのサイズを指定
        float dp = mContext.getResources().getDisplayMetrics().density;
        AbsListView.LayoutParams params
                = new AbsListView.LayoutParams(parent.getWidth()/7 - (int)dp, (parent.getHeight() - (int)dp * mDateManager.getWeeks() ) / mDateManager.getWeeks());
        convertView.setLayoutParams(params);

        //日付のみ表示させる
        SimpleDateFormat dateFormat_d = new SimpleDateFormat("d", Locale.JAPAN);
        dayTextView = (TextView) convertView.findViewById(R.id.day);
        dayTextView.setText(dateFormat_d.format(dateArray.get(position)));

        //リフレッシュ動作
        TextView scheTextView = convertView.findViewById(R.id.match_sch);
        scheTextView.setText("");

        //当月以外のセルをグレーアウト
        if (mDateManager.isCurrentMonth(dateArray.get(position))){
            convertView.setBackgroundColor(Color.WHITE);
        }else {
            convertView.setBackgroundColor(Color.LTGRAY);
        }

        //日曜日を赤、土曜日を青に
        int colorId;
        switch (mDateManager.getDayOfWeek(dateArray.get(position))){
            case 1:
                colorId = Color.RED;
                break;
            case 7:
                colorId = Color.BLUE;
                break;

            default:
                colorId = Color.BLACK;
                break;
        }
        //holder.dateText.setTextColor(colorId);
        dayTextView.setTextColor(colorId);


        //HashMapのほうがいいかも？
        SimpleDateFormat dateFormat_yyyyMMdd = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);
        //リストの中に今扱っている日付と同じものがあれば
        for(GameRecord gr : gameScheduleList){
            //今扱っている月の日数×gameListのサイズ分回ってただけ
            //Log.d("debug schedule",dateFormat_yyyyMMdd.format(dateArray.get(position)) +" " + gr.getGameDay() + gameScheduleList.size());
            if(gr.getGameDay().equals( dateFormat_yyyyMMdd.format(dateArray.get(position)) )){
                //見つけたログを排出
                Log.d("debug mikke",gr.getGameDay());
                scheTextView.setText(gr.getMatchName());
            }
        }



        //セルに関しての処理
        //セルをクリックしたときの
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat dF = new SimpleDateFormat("yyyy/MM/dd", Locale.JAPAN);

                /**確認用の**************************************************************/
                Toast.makeText(parent.getContext(),
                        dF.format(dateArray.get(position)),Toast.LENGTH_SHORT).show();
                Log.d("debug","cell clicked");
                /**********************************************************************/



                fragmentManager = fragmentActivity.getSupportFragmentManager();
                // DialogFragment を継承したAlertDialogFragmentのインスタンス

                //addした内容を反映するために
                //viewは先頭からn個目ってだけのものだから、月替えても残る
                matchScheTextView = (TextView) view.findViewById(R.id.match_sch);

                //↓↓ここで日付のStringを渡す
                gameScheduleAddDialogFragment =
                        new GameScheduleAddDialogFragment( dF.format(dateArray.get(position)),
                                gameScheduleList);

                // DialogFragmentの表示
                gameScheduleAddDialogFragment.show(fragmentManager, "test alert dialog");

            }
        });

        return convertView;
    }




    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    //表示月を取得
    public String getTitle(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM", Locale.US);
        return format.format(mDateManager.mCalendar.getTime());
    }

    //表示月のみを数値として取得
    public int getCurrentMonth(){
        SimpleDateFormat format = new SimpleDateFormat("MM", Locale.US);
        return Integer.parseInt(format.format(mDateManager.mCalendar.getTime()));
    }

    public String getRealCurrentMonth(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM", Locale.US);
        Date d = Calendar.getInstance().getTime();
        setMonth(d);
        return format.format(d);
    }


    //翌月表示
    public void nextMonth(){
        mDateManager.nextMonth();
        dateArray = mDateManager.getDays();
        this.notifyDataSetChanged();
    }

    //前月表示
    public void prevMonth(){
        mDateManager.prevMonth();
        dateArray = mDateManager.getDays();
        this.notifyDataSetChanged();
    }

    public void setMonth(Date d){
        mDateManager.setMonth(d);
        dateArray = mDateManager.getDays();
        this.notifyDataSetChanged();
    }



}