package com.example.vbapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;

import com.example.vbapp.CalendarAdapter;
import com.example.vbapp.GameRecord;
import com.example.vb.R;
import com.example.vbapp.database.AppDataBase;
import com.example.vbapp.database.AppDatabaseSingleton;
import com.example.vbapp.database.SelectTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    //GameList
    private ListView gameListView;
    private List<GameRecord> gameRecordArrayList;

    private FloatingActionButton fab;

    private FragmentManager fragmentManager;

    private GameRecordAddDialogFragment gameRecordAddDialogFragment;

    private GameRecordDialogFragment gameRecordDialogFragment;
    private GameListAdapter gameListAdapter;

    private String sampleURL = "https://www.volleyballworld.tv/video/473870/allianz-milano-vs-gas-sales-bluenergy-piacenza-3rd-place-playoff-replay";

    private AppDataBase db;

    public ListFragment(List<GameRecord> gameRecordList){
        this.gameRecordArrayList = gameRecordList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.list_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("fromCF",
                this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                //別にバンドルはいらない
                //↓の処理をやるためにここは使っている
                gameListAdapter.notifyDataSetChanged();
                //Toast.makeText(getContext(),"notify GLA gla#notify() called",Toast.LENGTH_SHORT).show();
            }
        });

        //パーツを拾ってくる
        fab = view.findViewById(R.id.fab);
        gameListView = view.findViewById(R.id.gameList);

        //List周りの処理
        //gameRecordArrayList = new ArrayList<>();
        //上位クラスで作ったものを渡している
        gameListAdapter = new GameListAdapter(getContext(), gameRecordArrayList);
        gameListView.setAdapter(gameListAdapter);


        //データベースのインスタンスを取得
        db = AppDatabaseSingleton.getInstance(getContext());
        SelectTask selectTask = new SelectTask(db,gameRecordArrayList);
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
                gameRecordDialogFragment = new GameRecordDialogFragment(tmp,db,gameListAdapter,
                        gameRecordArrayList,getParentFragmentManager());
                // DialogFragmentの表示
                gameRecordDialogFragment.show(fragmentManager, "gameRecord dialog");


            }
        });


        //fabのクリックリスナー
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //fabがクリックされた時の処理
                fragmentManager = getActivity().getSupportFragmentManager();

                // DialogFragment を継承したAlertDialogFragmentのインスタンス
                gameRecordAddDialogFragment = new GameRecordAddDialogFragment(
                        gameRecordArrayList,gameListAdapter,db,getParentFragmentManager());
                // DialogFragmentの表示
                gameRecordAddDialogFragment.show(fragmentManager, "test alert dialog");
                Log.d("debug","fab tapped");


            }
        });


    }
}


