package com.example.vbapp;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


import com.example.vb.databinding.ActivityMainBinding;
import com.example.vbapp.ui.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

/**
 * @author Kazuki0724
 */
public class MainActivity extends AppCompatActivity{


    private ActivityMainBinding binding;
    private Toolbar toolbar;
    private TabLayout tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);

        tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);

        toolbar = binding.toolbar;


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getSupportActionBar() != null) {
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        getSupportActionBar().setTitle(null);

        tabs.setupWithViewPager(viewPager);

    }




}