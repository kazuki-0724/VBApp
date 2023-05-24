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

import com.example.vb.R;
import com.example.vbapp.CalendarAdapter;
import com.example.vbapp.GameRecord;


import java.util.ArrayList;
import java.util.List;

public class CalendarFragment extends Fragment {

    private GridView calendarGridView;
    private CalendarAdapter mCalendarAdapter;

    private TextView textView;

    private Button prevButton;

    private Button nextButton;

    private List<GameRecord> gameScheduleList;


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
        textView = view.findViewById(R.id.year_month);
        prevButton = view.findViewById(R.id.prevButton);
        nextButton = view.findViewById(R.id.nextButton);

        //カレンダー周りの準備
        gameScheduleList = new ArrayList<>();
        mCalendarAdapter = new CalendarAdapter(getContext(),getActivity(),gameScheduleList);
        calendarGridView.setAdapter(mCalendarAdapter);

        //カレンダーの年月を入れる
        textView.setText(mCalendarAdapter.getTitle());


        //ボタンの名前設定
        updateButtonName();

        //カレンダーを戻す
        textView.setOnClickListener(new View.OnClickListener() {
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
                textView.setText(mCalendarAdapter.getTitle());
                updateButtonName();
            }
        });

        //次の月へ
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCalendarAdapter.nextMonth();
                textView.setText(mCalendarAdapter.getTitle());
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
        textView.setText(mCalendarAdapter.getRealCurrentMonth());
        updateButtonName();
    }




}
