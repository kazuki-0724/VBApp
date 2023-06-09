package com.example.vbapp.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.vbapp.GameRecord;
import com.example.vb.R;
import com.example.vbapp.database.AppDataBase;
import com.example.vbapp.database.DeleteTask;

import java.util.List;

public class GameRecordDialogFragment extends DialogFragment {


    private AlertDialog dialog ;
    private AlertDialog.Builder alert;
    private View alertView;

    private GameRecord gameRecord;

    private AppDataBase db;

    private GameListAdapter gameListAdapter;

    private List<GameRecord> gameRecordList;

    private FragmentManager fragmentManager;

    public GameRecordDialogFragment(GameRecord gameRecord, AppDataBase db, GameListAdapter gameListAdapter,
                                    List<GameRecord> gameRecordList, FragmentManager fragmentManager){
        //フラグメント生成のためにタップされた試合のレコードを貰う
        this.gameRecord = gameRecord;
        this.db = db;
        this.gameListAdapter = gameListAdapter;
        this.gameRecordList = gameRecordList;
        this.fragmentManager = fragmentManager;
    }


    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(gameRecord.getMatchName());

        // カスタムレイアウトの生成
        if (getActivity() != null) {
            alertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.game_record_dialog, null);
        }

        // パーツを拾ってくる
        //TextView matchNameText = alertView.findViewById(R.id.match_name_GRD);
        TextView matchDayText = alertView.findViewById(R.id.match_day_GRD);
        TextView matchUrlText = alertView.findViewById(R.id.match_url_GRD);
        Button watchButton = alertView.findViewById(R.id.watchButton);
        Button deleteButton = alertView.findViewById(R.id.deleteButton);

        //matchNameText.setText(gameRecord.getName());
        matchDayText.setText(gameRecord.getGameDay());
        matchUrlText.setText(gameRecord.getUrl());



        //watchButtonの挙動
        watchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("debug","watchButton Clicked");

                //レコードから取り出したURLでブラウザへ遷移させる処理をここで
                Uri uri = Uri.parse(gameRecord.getUrl());
                Intent i = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(i);


            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //データの挿入処理
                DeleteTask deleteTask = new DeleteTask(db);
                deleteTask.execute(gameRecord);
                gameRecordList.remove(gameRecord);
                gameListAdapter.notifyDataSetChanged();

                //ここで向こう側のnotify()呼ぶ
                Bundle bundle = new Bundle();
                fragmentManager.setFragmentResult("fromLF", bundle);

                dialog.dismiss();
            }
        });

        // ViewをAlertDialog.Builderに追加
        alert.setView(alertView);

        // Dialogを生成
        dialog = alert.create();
        dialog.show();

        return dialog;
    }

}
