package com.example.vbapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.vbapp.GameRecord;
import com.example.vb.R;
import com.example.vbapp.database.AppDataBase;
import com.example.vbapp.database.AppDatabaseSingleton;
import com.example.vbapp.database.SelectTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    //GameList
    private ListView gameListView;
    private ArrayList<GameRecord> gameRecordArrayList;

    private FloatingActionButton fab;

    private FragmentManager fragmentManager;

    private GameRecordAddDialogFragment gameRecordAddDialogFragment;

    private GameRecordDialogFragment gameRecordDialogFragment;
    private GameListAdapter gameListAdapter;

    //視聴URLに飛ばすためのボタン
    private Button watchButton;

    private String sampleURL = "https://www.volleyballworld.tv/video/473870/allianz-milano-vs-gas-sales-bluenergy-piacenza-3rd-place-playoff-replay";

    private AppDataBase db;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.list_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //パーツを拾ってくる
        fab = view.findViewById(R.id.fab);
        gameListView = view.findViewById(R.id.gameList);

        //List周りの処理
        gameRecordArrayList = new ArrayList<>();
        gameListAdapter = new GameListAdapter(getContext(), getActivity(), gameRecordArrayList);
        gameListView.setAdapter(gameListAdapter);


        //データベースのインスタンスを取得
        db = AppDatabaseSingleton.getInstance(getContext());
        SelectTask selectTask = new SelectTask(db,getActivity(),gameRecordArrayList,gameListAdapter);
        selectTask.execute();
        gameListAdapter.notifyDataSetChanged();



        //Listの挙動
        gameListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //for debug
                //Toast.makeText(getContext(),"list tapped",Toast.LENGTH_LONG).show();

                //タップされたところのGameRecordを取り出す
                GameRecord tmp = gameRecordArrayList.get(position);

                //ここではダイアログを表示してGameについての情報を開示+ボタンでURL遷移を実現する
                fragmentManager = getActivity().getSupportFragmentManager();

                // DialogFragment を継承したAlertDialogFragmentのインスタンス
                gameRecordDialogFragment = new GameRecordDialogFragment(tmp,db,gameListAdapter,gameRecordArrayList);
                // DialogFragmentの表示
                gameRecordDialogFragment.show(fragmentManager, "gameRecord dialog");



                //for debug
                //Toast.makeText(getContext(),tmp.getURL(),Toast.LENGTH_LONG).show();

            }
        });


        //fabのクリックリスナー
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //fabがクリックされた時の処理
                fragmentManager = getActivity().getSupportFragmentManager();

                // DialogFragment を継承したAlertDialogFragmentのインスタンス
                gameRecordAddDialogFragment = new GameRecordAddDialogFragment(gameRecordArrayList,gameListAdapter,db);
                // DialogFragmentの表示
                gameRecordAddDialogFragment.show(fragmentManager, "test alert dialog");
                Log.d("debug","fab tapped");
            }
        });







        //for test**********************************************************************
        GameRecord testGameRecord = new GameRecord("Milano VS Pia","2023/04/30",sampleURL,2);
        GameRecord testGameRecord2 = new GameRecord("JTECKT VS Panasonic","2023/05/01",sampleURL,1);
        gameRecordArrayList.add(testGameRecord);
        gameRecordArrayList.add(testGameRecord2);
        gameListAdapter.notifyDataSetChanged();
        //******************************************************************************

    }


}