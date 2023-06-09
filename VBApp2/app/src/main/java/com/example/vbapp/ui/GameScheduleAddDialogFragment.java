package com.example.vbapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.vbapp.CalendarAdapter;
import com.example.vbapp.GameRecord;
import com.example.vb.R;
import com.example.vbapp.database.AppDataBase;
import com.example.vbapp.database.AppDatabaseSingleton;
import com.example.vbapp.database.InsertTask;

import java.util.List;

public class GameScheduleAddDialogFragment extends DialogFragment {

    // 選択肢のリスト
    private String[] menulist = {"Match Name", "date", "URL"};

    AlertDialog dialog ;
    AlertDialog.Builder alert;
    View alertView;

    private AppDataBase db;

    private String title;

    private TextView scheTextView;

    private Button scheduleAddButton;

    private List<GameRecord> gameScheduleList;

    private CalendarAdapter calendarAdapter;

    private FragmentManager fragmentManager;

    public GameScheduleAddDialogFragment(String title,
                                         List<GameRecord> gameScheduleList,
                                         FragmentManager fragmentManager
                                         ){
        this.title = title;
        this.gameScheduleList = gameScheduleList;
        //this.calendarAdapter = calendarAdapter;
        this.db = AppDatabaseSingleton.getInstance(getContext());
        this.fragmentManager = fragmentManager;
    }


    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(this.title);

        // カスタムレイアウトの生成
        if (getActivity() != null) {
            alertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.game_schedule_fragment, null);
        }



        scheduleAddButton = alertView.findViewById(R.id.addScheduleButton);
        scheduleAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**入力値の取り出し***************************************************/
                EditText nameEditText = alertView.findViewById(R.id.match_sch_name);
                String matchName = nameEditText.getText().toString();
                RadioGroup radioGroup = alertView.findViewById(R.id.sch_radioGroup);
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = alertView.findViewById(radioButtonId);
                /******************************************************************/

                //ここでリストに突っ込む
                if(radioButton != null) {
                    GameRecord gr = new GameRecord(matchName, title, "", checkLeagueType(radioButton.getText().toString()));

                    //データの挿入処理
                    InsertTask insertTask = new InsertTask(db);
                    insertTask.execute(gr);

                    //とりあえず外で呼ぶ
                    //calendarAdapter.notifyDataSetChanged();

                    gameScheduleList.add(gr);
                    Log.d("debug size Check",gameScheduleList.size()+"");

                    dialog.dismiss();


                    //ここで向こう側のnotify()呼ぶ
                    Bundle bundle = new Bundle();
                    fragmentManager.setFragmentResult("fromCF",bundle);


                }else{
                    //radio Buttonのチェックがないとnullになるから
                    Toast.makeText(getContext(),"check league type",Toast.LENGTH_SHORT).show();
                }

            }
        });


        // ViewをAlertDialog.Builderに追加
        alert.setView(alertView);

        // Dialogを生成
        dialog = alert.create();
        dialog.show();

        return dialog;
    }

    public int checkLeagueType(String str){
        int leagueType = 3;
        switch(str){
            case "V League":
                leagueType = 1;
                break;
            case "Serie A":
                leagueType = 2;
                break;
            case "NT":
                leagueType = 3;
                break;
        }
        return leagueType;
    }


}
