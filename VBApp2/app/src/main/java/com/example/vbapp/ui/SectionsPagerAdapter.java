package com.example.vbapp.ui;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.vbapp.GameRecord;
import com.example.vbapp.database.AppDataBase;

import java.util.ArrayList;
import java.util.List;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    String[] tabs_name = {"List","Calendar"};

    private List<GameRecord> list;

    private BaseAdapter baseAdapter;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        //どうせ使うistは同じものだから
        list = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //return PlaceholderFragment.newInstance(position + 1);

        Fragment fragment = null;



        //何枚目のタブか(はじめだけ呼ばれる)
        switch (position){
            case 0:
                fragment = new ListFragment(list);
                Log.d("SPA","List側のフラグメント");
                break;
            case 1:
                fragment = new CalendarFragment(list);
                Log.d("SPA","カレンダー側のフラグメント");
                break;
        }

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return tabs_name[position];
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}