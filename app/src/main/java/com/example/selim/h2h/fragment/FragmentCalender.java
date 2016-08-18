//package com.example.selim.h2h.fragment;
//
//import android.app.Activity;
//import android.content.Context;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CalendarView;
//
//import com.example.selim.h2h.utils.MyApp;
//import com.example.selim.h2h.R;
//
///**
// * Created by selim on 09/02/2016.
// */
//public class FragmentCalender extends Fragment {
//    Context context;
//    Activity activity;
//    CalendarView calendarView;
//    MyApp myApp;
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        myApp = (MyApp)context.getApplicationContext();
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.calander, container, false);
//        calendarView = (CalendarView)view.findViewById(R.id.calender_btnt);
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                int m = month +1 ;
//                String date = dayOfMonth+ "/"+m+"/"+year;
//                int day = dayOfMonth;
//                int y = year ;
//                Fragment fragment = new FragmentAddOppointment();
//                Bundle bundle = new Bundle();
//                bundle.putInt("month", m);
//                bundle.putInt("day",day);
//                bundle.putInt("year",y);
//                bundle.putString("date",date);
//
//                fragment.setArguments(bundle);
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragmentContent, fragment).commit();
//            }
//        });
//        return view;
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        this.context=context;
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        this.activity=activity;
//    }
//}
