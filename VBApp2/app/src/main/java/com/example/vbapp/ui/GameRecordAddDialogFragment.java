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

import com.example.vbapp.DatePick;
import com.example.vbapp.GameRecord;
import com.example.vb.R;
import com.example.vbapp.database.AppDataBase;
import com.example.vbapp.database.InsertTask;


import java.util.List;

public class GameRecordAddDialogFragment extends DialogFragment{

    //ダイアログ
    private AlertDialog dialog ;
    //ダイアログ生成用
    private AlertDialog.Builder alert;
    //ダイアログのView
    private View alertView;
    //gameRecordのリスト
    private List<GameRecord> gameRecordArrayList;
    //listのアダプター
    private GameListAdapter gameListAdapter;
    //ダイアログの日付ピッカー用のTextView
    private TextView datePickerTextView;
    //database
    private AppDataBase db;

    public GameRecordAddDialogFragment(List<GameRecord> gameRecordArrayList,
                                       GameListAdapter gameListAdapter,AppDataBase db){
        this.gameRecordArrayList = gameRecordArrayList;
        this.gameListAdapter = gameListAdapter;
        this.db = db;
    }


    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

            alert = new AlertDialog.Builder(getActivity());
            alert.setTitle("Add new game record");

            // カスタムレイアウトの生成
            if (getActivity() != null) {
                alertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.custom_dialog, null);
            }

            // パーツを拾ってくる
            EditText nameEditText = alertView.findViewById(R.id.match_name_editText);
            datePickerTextView = alertView.findViewById(R.id.match_day_TextView);
            EditText urlEditText = alertView.findViewById(R.id.match_url_editText);
            Button addButton = alertView.findViewById(R.id.addButton);
            RadioGroup radioGroup = alertView.findViewById(R.id.radioGroup);

            //レコードを追加する処理
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("debug","Button Clicked");

                    //GameRecordの要素取得
                    String name = nameEditText.getText().toString();
                    String day = datePickerTextView.getText().toString();
                    String url = urlEditText.getText().toString();

                    //ここでnullになる可能性←対応済み
                    int radioButtonId = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = alertView.findViewById(radioButtonId);

                    if(radioButton != null) {
                        GameRecord gameRecord = new GameRecord(name, day, url, checkLeagueType(radioButton.getText().toString()));

                        //データの挿入処理
                        InsertTask insertTask = new InsertTask(db);
                        insertTask.execute(gameRecord);

                        //Listにデータを追加
                        gameRecordArrayList.add(gameRecord);
                        //変化を通知
                        gameListAdapter.notifyDataSetChanged();
                        //dialogを閉じる処理
                        dialog.dismiss();

                    }else{
                        //radio Buttonのチェックがないとnullになるから
                        Toast.makeText(getContext(),"check league type",Toast.LENGTH_SHORT).show();
                    }

                }
            });

            //日付
            datePickerTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFragment = new DatePick(datePickerTextView);
                    newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
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
