package com.example.selim.h2h.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
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
import com.example.selim.h2h.utils.MyApp;
import com.parse.ParseUser;


public class BlankFragment extends Fragment {

    private PagerSlidingTabStrip tabs;
    private static ViewPager viewPager;
    private final int int_items = 3;
    FloatingActionButton buttonAdd ,call;
    MyApp myApp;
    ParseUser currentuser;
    Activity activity;
    Context context;

    public BlankFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApp =(MyApp)activity.getApplicationContext();
        currentuser =myApp.userPatient;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View x = inflater.inflate(R.layout.fragment_blank, container, false);
        buttonAdd = (FloatingActionButton)x.findViewById(R.id.buttonAdd1);
        call = (FloatingActionButton)x.findViewById(R.id.btn_call);
        final String phone = currentuser.getString("Phone");

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                startActivity(intent);

            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentAddPrescription();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContent, fragment).commit();

            }
        });

        tabs = (PagerSlidingTabStrip) x.findViewById(R.id.tab_strip);

        viewPager = (ViewPager) x.findViewById(R.id.viewpager);

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
                case 0 : return new FragmentEMGChart();
                case 1 : return new FragmentPulseChart();
                case 2 : return new FragmentHistorique();
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
                case 2 :
                    return "Analyse Data";
            }
            return null;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;
    }
}
