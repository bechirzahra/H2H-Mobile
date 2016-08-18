//package com.example.selim.h2h.fragment;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.CalendarView;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.Toast;
//
//import com.example.selim.h2h.utils.MyApp;
//import com.example.selim.h2h.R;
//import com.fourmob.datetimepicker.date.DatePickerDialog;
//import com.parse.ParseObject;
//import com.parse.ParseRelation;
//import com.parse.ParseUser;
//import com.parse.SaveCallback;
//import com.sleepbot.datetimepicker.time.RadialPickerLayout;
//import com.sleepbot.datetimepicker.time.TimePickerDialog;
//
//import java.util.Calendar;
//import java.util.Date;
//
///**
// * Created by selim on 09/02/2016.
// */
//public class FragmentAddOppointment extends FragmentActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
//    Context context;
//    MyApp myApp;
//    Activity activity;
//    ImageButton imageButton;
//    Dialog dialog;
//    CalendarView calendarView;
//    EditText dateEdit,comment;
//    Bundle bundle;
//    String date;
//    Button ajout;
//    ParseUser parseUser;
//
//
//    public static final String DATEPICKER_TAG = "datepicker";
//    public static final String TIMEPICKER_TAG = "timepicker";
//    int y,m,d,h,min;
//    DatePickerDialog datePickerDialog;
//    TimePickerDialog timePickerDialog;
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        myApp = (MyApp)context.getApplicationContext();
//
//        final Calendar calendar = Calendar.getInstance();
//
//        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), true);
//        timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);
//
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.add_oppointment, container, false);
//
//        imageButton = (ImageButton)view.findViewById(R.id.btn_calender);
//        dateEdit = (EditText)view.findViewById(R.id.edit_date);
//        if(date!=null)
//            dateEdit.setText(date);
//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                datePickerDialog.setVibrate(true);
//                datePickerDialog.setYearRange(1985, 2028);
//                datePickerDialog.setCloseOnSingleTapDay(true);
//                //datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);
//
//            }
//        });
////        if (savedInstanceState != null) {
////            DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(DATEPICKER_TAG);
////            if (dpd != null) {
////                dpd.setOnDateSetListener(this);
////            }
////
////            TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(TIMEPICKER_TAG);
////            if (tpd != null) {
////                tpd.setOnTimeSetListener(this);
////            }
////        }
//
//        comment= (EditText)view.findViewById(R.id.btn_comment);
//        ajout = (Button)view.findViewById(R.id.add_btn);
//
////        ajout.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                ParseObject parseObject = new ParseObject("RendezVous");
////                ParseRelation relation = parseObject.getRelation("IdPatient");
////                relation.add(ParseUser.getCurrentUser());
////                parseObject.put("DateRendezVous", new Date(y, m, day));
////                parseObject.put("Comment", comment.getText().toString());
////                parseObject.put("Confirmation", 0);
////                //parseUser.add("Comment", phoneUser.getText().toString());
////
////                parseObject.saveInBackground(new SaveCallback() {
////                    @Override
////                    public void done(com.parse.ParseException e) {
////                        if (e == null) {
////                            Log.e("tag1", "null");
////                            Toast.makeText(FragmentAddOppointment.this.context.getApplicationContext(), "Added",
////                                    Toast.LENGTH_SHORT).show();
////                            Fragment fragment1 = new FragmentPatient();
////                            FragmentManager fragmentManager = getFragmentManager();
////                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////                            fragmentTransaction.replace(R.id.fragmentContent, fragment1).commit();
////
////                        }
////                        else
////                            Log.e("tag1", "e not null" +e.getMessage());
////
////                    }
////
////
////            });
////            }
////        });
//
//        return view;
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        this.context = context;
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        this.activity = activity;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if(dialog!=null)
//            dialog.dismiss();
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if(dialog!=null)
//            dialog.dismiss();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        if(dialog!=null)
//            dialog.dismiss();
//    }
//
//    @Override
//    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
//        Toast.makeText(context, "new date:" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
//        y=year;
//        m=month;
//        d=day;
//        timePickerDialog.setVibrate(true);
//        timePickerDialog.setCloseOnSingleTapMinute(true);
//        //timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
//
//    }
//
//    @Override
//    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
//        Toast.makeText(context, "new time:" + hourOfDay + "-" + minute, Toast.LENGTH_LONG).show();
//        h=hourOfDay;
//        min=minute;
//
//    }
//}
