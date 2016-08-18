package com.example.selim.h2h.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.example.selim.h2h.R;

/**
 * Created by selim on 24/02/2016.
 */
public class BlankFragmentHistorique extends Fragment {

    private PagerSlidingTabStrip tabs;
    private static ViewPager viewPager;
    private final int int_items = 2;



    public BlankFragmentHistorique() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View x = inflater.inflate(R.layout.fragment_blank_historique, container, false);


        tabs = (PagerSlidingTabStrip) x.findViewById(R.id.tab_strip2);

        viewPager = (ViewPager) x.findViewById(R.id.viewpager2);

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        tabs.post(new Runnable() {
            @Override
            public void run() {
                tabs.setViewPager(viewPager);
            }
        });


        int[][] tabStates = new int[][] {
                new int[] { android.R.attr.state_pressed}, // enabled
                new int[] { android.R.attr.state_selected},  // unchecked
                new int[] { -android.R.attr.state_selected}
        };

        int[] tabColors = new int[] {
                getResources().getColor((R.color.TabTextColor)),
                getResources().getColor((R.color.TabTextColor)),
                getResources().getColor((R.color.TabTextColor)) // Default Tab Text Color
        };

        ColorStateList tabList = new ColorStateList(tabStates, tabColors);
        tabs.setTextColor(tabList); // Setting the Tab Text Color

        return x;
    }

    public class MyAdapter extends FragmentStatePagerAdapter { // Tab Adapter

        public MyAdapter(FragmentManager fm) { super(fm); }

        @Override
        public Fragment getItem(int position)
        {
            switch (position){
                case 0 : return new FragmentEMGChartHistorique();
                case 1 : return new FragmentPulseHistorique();
            }

            return null;
        }

        @Override
        public int getCount() { return int_items; }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "EMG Stat";
                case 1 :
                    return "Pulse Stat";
            }
            return null;
        }
    }



}

