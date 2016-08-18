package com.example.selim.h2h.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.selim.h2h.R;
import com.example.selim.h2h.fragment.FragmentConsult;
import com.example.selim.h2h.fragment.FragmentConsultPatient;
import com.example.selim.h2h.fragment.FragmentDrawer;
import com.example.selim.h2h.fragment.FragmentEditProfilDoctor;
import com.example.selim.h2h.utils.MyApp;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.Date;

public class ActivityAddCalander extends FragmentActivity implements OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    public static final String DATEPICKER_TAG = "datepicker";
    public static final String TIMEPICKER_TAG = "timepicker";


    int y,m,d,h,min;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Context context;
    MyApp myApp;
    Activity activity;
    ImageButton imageButton;
    Dialog dialog;
    CalendarView calendarView;
    EditText dateEdit,comment;
    Bundle bundle;
    // String date;
    Button ajout;
    ParseUser parseUser;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_oppointment);
        Calendar today = Calendar.getInstance();

        imageButton = (ImageButton)findViewById(R.id.btn_calender);

        final Calendar calendar = Calendar.getInstance();

        datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), true);
        timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);


        dateEdit = (EditText)findViewById(R.id.edit_date);
        comment= (EditText)findViewById(R.id.btn_comment);
        ajout = (Button)findViewById(R.id.add_btn);

        //dateEdit.setText(date);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datePickerDialog.setVibrate(true);
                datePickerDialog.setYearRange(1985, 2028);
                datePickerDialog.setCloseOnSingleTapDay(true);
                datePickerDialog.show(getSupportFragmentManager(), DATEPICKER_TAG);


            }
        });

        System.out.println(1+"number1");


        if (savedInstanceState != null) {
            DatePickerDialog dpd = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag(DATEPICKER_TAG);
            if (dpd != null) {
                dpd.setOnDateSetListener(this);
            }

            TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(TIMEPICKER_TAG);
            if (tpd != null) {
                tpd.setOnTimeSetListener(this);
            }
        }
//        result = m+" "+d+", "+y+", "+h+":"+min;
//        System.out.println(result);


//        ajout.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ParseObject parseObject = new ParseObject("RendezVous");
//                ParseRelation relation = parseObject.getRelation("IdPatient");
//
//                relation.add(ParseUser.getCurrentUser());
//                parseObject.put("DateRendezVous", new Date(m,d,y,h,min));
//                parseObject.put("Comment", comment.getText().toString());
//                parseObject.put("Confirmation", 0);
//                //parseUser.add("Comment", phoneUser.getText().toString());
//
//                parseObject.saveInBackground(new SaveCallback() {
//                    @Override
//                    public void done(com.parse.ParseException e) {
//                        if (e == null) {
//                            Log.e("tag1", "null");
//                            Toast.makeText(MainActivity.this.context.getApplicationContext(), "Added",
//                                    Toast.LENGTH_SHORT).show();
//                            Fragment fragment = new FragmentConsult();
//                            FragmentManager fragmentManager = getSupportFragmentManager();
//                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                            fragmentTransaction.replace(R.id.fragmentContent, fragment).commit();
//
//                        }
//                        else
//                            Log.e("tag1", "e not null" +e.getMessage());
//
//                    }
//
//
//            });
//            }
//        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(dialog!=null)
            dialog.dismiss();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(dialog!=null)
            dialog.dismiss();
    }
    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
       // Toast.makeText(MainActivity.this, "new date:" + year + "-" + month + "-" + day, Toast.LENGTH_LONG).show();
        y=year;
        m=month;
        d=day;
        dateEdit.setText(d + "/" + m + "/" + y + ", " + h + ":" + min);
        System.out.println(2+"number2");
        System.out.println(year + "bonjourrrrrr");
        timePickerDialog.setVibrate(true);
        timePickerDialog.setCloseOnSingleTapMinute(true);
        timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
       // Toast.makeText(MainActivity.this, "new time:" + hourOfDay + "-" + minute, Toast.LENGTH_LONG).show();
        h = hourOfDay;
        min=minute;
        System.out.println(3 + "number3");

        dateEdit.setText(d + "/" + m + "/" + y + ", " + h + ":" + min);
        y=y-1900;
        final Date date =new Date(y, m, d, h, min);
        System.out.println("ddddddddddddddddd"+date);

        ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject parseObject = new ParseObject("RendezVous");
                ParseRelation relation = parseObject.getRelation("IdPatient");
                ParseACL defaultACL = new ParseACL();


                relation.add(ParseUser.getCurrentUser());
                System.out.println("year" + y);
                System.out.println("month" + m);
                System.out.println("day" + d);
                System.out.println("hour "+h);
                System.out.println("minute" + min);
                parseObject.put("DateRendezVous", date);
                parseObject.put("Comment", comment.getText().toString());
                parseObject.put("Confirmation", 0);
                defaultACL.setPublicReadAccess(true);
                defaultACL.setPublicWriteAccess(true);
                ParseACL.setDefaultACL(defaultACL, true);

                parseObject.put("ACL", defaultACL);
                //parseUser.add("Comment", phoneUser.getText().toString());

                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(com.parse.ParseException e) {
                        if (e == null) {
                            Log.e("tag1", "null");
//                            Toast.makeText(MainActivity.this.context.getApplicationContext(), "Added",
//                                    Toast.LENGTH_SHORT).show();


                        } else
                            Log.e("tag1", "e not null" + e.getMessage());

                    }


                });

                Intent intent = new Intent(ActivityAddCalander.this, AccueilPatient.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
