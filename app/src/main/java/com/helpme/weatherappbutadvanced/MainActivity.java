package com.helpme.weatherappbutadvanced;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.helpme.weatherappbutadvanced.Adapters.TabAdapter;
import com.helpme.weatherappbutadvanced.Fragements.CurrentFragment;
import com.helpme.weatherappbutadvanced.Fragements.SearchFragment;
import com.helpme.weatherappbutadvanced.Fragements.WeeklyFragment;

public class MainActivity extends AppCompatActivity
{

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tlTabs);
        viewPager = findViewById(R.id.vpViewPager);

        TabAdapter adapter = new TabAdapter(getSupportFragmentManager(),0);
        adapter.addFragment(CurrentFragment.newInstance(), "Current");
        adapter.addFragment(WeeklyFragment.newInstance(), "Weekly");
        adapter.addFragment(SearchFragment.newInstance(), "Search");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}