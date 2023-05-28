package com.example.vbapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.vb.R;
import com.example.vbapp.CalendarAdapter;
import com.example.vbapp.GameRecord;
import com.example.vbapp.database.AppDataBase;
import com.example.vbapp.database.AppDatabaseSingleton;
import com.example.vbapp.database.SelectTask;


import java.util.ArrayList;
import java.util.List;

public class CalendarFragment extends Fragment {

    //カレンダーのビュー部分
    private GridView calendarGridView;
    //カレンダーの機能
    private CalendarAdapter mCalendarAdapter;
    //年と月のTextView
    private TextView ymTextView;
    //次の月に移動するボタン
    private Button prevButton;
    //前の月に移動するボタン
    private Button nextButton;
    //スケジュールのリスト
    private List<GameRecord> gameScheduleList;

    private AppDataBase db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.calendar_fragment,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //なんか書くときはこの部分に書く


        //パーツを拾ってくる
        calendarGridView = view.findViewById(R.id.calendar_grid);
        ymTextView = view.findViewById(R.id.year_month);
        prevButton = view.findViewById(R.id.prevButton);
        nextButton = view.findViewById(R.id.nextButton);


        //カレンダー周りの準備
        gameScheduleList = new ArrayList<>();
        //0528追記：データベースのインスタンスを取得
        db = AppDatabaseSingleton.getInstance(getContext());
        //読み込み処理
        SelectTask selectTask = new SelectTask(db,gameScheduleList);
        selectTask.execute();

        mCalendarAdapter = new CalendarAdapter(getContext(),getActivity(),gameScheduleList);
        calendarGridView.setAdapter(mCalendarAdapter);


        //読み込んだもので更新
        //notify()してあげないとlistに反映されないっぽい
        mCalendarAdapter.notifyDataSetChanged();


        //カレンダーの年月を入れる
        ymTextView.setText(mCalendarAdapter.getTitle());


        //ボタンの月設定
        updateButtonName();

        //カレンダーを戻す
        ymTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setCurrentCalendar(view);
                Log.d("debug","text clicked");
            }
        });

        //前の月
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalendarAdapter.prevMonth();
                ymTextView.setText(mCalendarAdapter.getTitle());
                updateButtonName();
            }
        });

        //次の月へ
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalendarAdapter.nextMonth();
                ymTextView.setText(mCalendarAdapter.getTitle());
                updateButtonName();
            }
        });


    }

    //ボタンの表示月を更新する
    public void updateButtonName(){

        //今カレンダーが表示している月を数値として
        int target = mCalendarAdapter.getCurrentMonth();

        //カレンダーの循環を考慮して
        if(target == 12){
            nextButton.setText( ">> 1"  );
            prevButton.setText( (mCalendarAdapter.getCurrentMonth()-1) + " <<");

        }else if(target == 1){
            nextButton.setText( ">> " + (mCalendarAdapter.getCurrentMonth()+1) );
            prevButton.setText( "12 <<");

        }else {
            nextButton.setText(">> " + (mCalendarAdapter.getCurrentMonth() + 1));
            prevButton.setText((mCalendarAdapter.getCurrentMonth() - 1) + " <<");
        }
    }


    public void setCurrentCalendar(View v){
        ymTextView.setText(mCalendarAdapter.getRealCurrentMonth());
        updateButtonName();
    }




}
