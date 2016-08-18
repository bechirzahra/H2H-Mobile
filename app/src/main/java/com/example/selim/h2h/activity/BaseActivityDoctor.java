package com.example.selim.h2h.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.selim.h2h.R;
import com.example.selim.h2h.adapter.ListAppointmentsAdapter;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by selim on 22/02/2016.
 */
public abstract class BaseActivityDoctor extends AppCompatActivity implements WeekView.EventClickListener, MonthLoader.MonthChangeListener, WeekView.EventLongPressListener, WeekView.EmptyViewLongPressListener {
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    private WeekView mWeekView;
    List<ParseObject> listAppointments;
    ProgressDialog mProgressDialog;
    int day,month,year,minute,hours;
    int id_user;
   List<ParseUser> list5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_doctor);



        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

        // Show a toast message about the touched event.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);



        // Set long press listener for empty view
        mWeekView.setEmptyViewLongPressListener(this);

        // Set up a date time interpreter to interpret how the date and time will be formatted in
        // the week view. This is optional.
        setupDateTimeInterpreter(false);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        setupDateTimeInterpreter(id == R.id.action_week_view);
        switch (id){
            case R.id.action_today:
                mWeekView.goToToday();
                return true;
            case R.id.action_day_view:
                if (mWeekViewType != TYPE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(1);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_three_day_view:
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
                return true;
            case R.id.action_week_view:
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    item.setChecked(!item.isChecked());
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Set up a date time interpreter which will show short date values when in week view and long
     * date values otherwise.
     * @param shortDate True if the date values should be short.
     */
    private void setupDateTimeInterpreter(final boolean shortDate) {
        mWeekView.setDateTimeInterpreter(new DateTimeInterpreter() {
            @Override
            public String interpretDate(Calendar date) {
                SimpleDateFormat weekdayNameFormat = new SimpleDateFormat("EEE", Locale.getDefault());
                String weekday = weekdayNameFormat.format(date.getTime());
                SimpleDateFormat format = new SimpleDateFormat(" M/d", Locale.getDefault());

                // All android api level do not have a standard way of getting the first letter of
                // the week day name. Hence we get the first char programmatically.
                // Details: http://stackoverflow.com/questions/16959502/get-one-letter-abbreviation-of-week-day-of-a-date-in-java#answer-16959657
                if (shortDate)
                    weekday = String.valueOf(weekday.charAt(0));
                return weekday.toUpperCase() + format.format(date.getTime());
            }

            @Override
            public String interpretTime(int hour) {
                return hour > 11 ? (hour - 12) + " PM" : (hour == 0 ? "12 AM" : hour + " AM");
            }
        });
    }

    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(this, "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
       final String name = event.getName();
           if (event.getColor() == -688280) {


               new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                       .setTitleText("Are you sure?")
                       .setContentText("Are you able to accept this appointment!")
                       .setCancelText("No,cancel plx!")
                       .setConfirmText("Yes,accept it!")
                       .showCancelButton(true)
                       .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                           @Override
                           public void onClick(SweetAlertDialog sDialog) {
                               // reuse previous dialog instance, keep widget user state, reset them if you need
                               sDialog.setTitleText("Not accepted")
                                       .setContentText("you didn't accept ")
                                       .setConfirmText("OK")
                                       .showCancelButton(false)
                                       .setCancelClickListener(null)
                                       .setConfirmClickListener(null)
                                       .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                               // or you can new a SweetAlertDialog to show
                               /* sDialog.dismiss();
                                new SweetAlertDialog(SampleActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .show();*/
                           }
                       })
                       .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                           @Override
                           public void onClick(SweetAlertDialog sDialog) {
                               if (name.length()==19) {

                                   day = Integer.parseInt(name.toString().substring(17, 19));
                                   month = Integer.parseInt(name.toString().substring(15, 16));
                                   hours = Integer.parseInt(name.toString().substring(9, 11));
                                   minute = Integer.parseInt(name.toString().substring(12, 14));
                                   month = month-1;
                                   hours = hours+1;
                               }
                               else if (name.length()==18)
                               {

                                   day = Integer.parseInt(name.toString().substring(17, 18));
                                   month = Integer.parseInt(name.toString().substring(15, 16));
                                   hours = Integer.parseInt(name.toString().substring(9, 11));
                                   minute = Integer.parseInt(name.toString().substring(12, 14));
                                   month = month-1;
                                   hours = hours+1;
                               }



                               System.out.println("day"+day+"month"+month+"hours"+hours+"minute"+minute);
                               Date date = new Date(116,month,day,hours,minute);

                               ParseObject parseObject1 = new ParseObject("RendezVous");
                               ParseQuery<ParseObject> query = new ParseQuery<>(
                                       "RendezVous");
                               query.whereEqualTo("DateRendezVous", date);

                               try {
                                   parseObject1 =query.getFirst();
                                   System.out.println(parseObject1.getObjectId() + "iddddd");
                                   parseObject1.put("Confirmation", 1);
                                   parseObject1.saveInBackground(new SaveCallback() {
                                       @Override
                                       public void done(com.parse.ParseException e) {

                                           if (e == null) {

                                           }
                                           else
                                           Log.e("tag1", "e not null" +e.getMessage());
                                       }
                                   });

                               } catch (com.parse.ParseException e) {
                                   e.printStackTrace();
                               }
                               ParseRelation<ParseUser> id_user1 =parseObject1.getRelation("IdPatient");

                               ParseQuery<ParseUser> query5 = ParseUser.getQuery();
                               query5.whereEqualTo("objectId", id_user1);
                               System.out.println(id_user1 + "id user");
                               try {
                                   System.out.println(query.find()+"pppppppppp");
                               } catch (com.parse.ParseException e) {
                                   e.printStackTrace();
                               }
                               try {
                                   list5=query5.find();
                               } catch (com.parse.ParseException e) {
                                   e.printStackTrace();
                               }
                               System.out.println("sizeUser"+list5.size());

//



//                               System.out.println(parseObject1.getObjectId() + "id");
//                               parseObject1.put("Confirmation", 1);
//                              // parseObject1.saveInBackground();
//                               System.out.println(date + "kkkkkkkk");
//                               parseObject1.saveInBackground(new SaveCallback() {
//                                   @Override
//                                   public void done(com.parse.ParseException e) {
//                                       if (e == null) {
//                                           Log.e("tag1", "e  null" + e.getMessage());
////
//                                       } else
//                                           Log.e("tag1", "e not null" + e.getMessage());
//                                       parseObject1.save();
//                                   }
//                               });

//                               query.getFirstInBackground(new GetCallback<ParseObject>() {
//                                                              @Override
//                                                              public void done(ParseObject parseObject, com.parse.ParseException e) {
//
//                                                                  parseObject.put("Confirmation", 1);
//                                                                  parseObject.saveInBackground();
//
//                                                              }
//                                                          }
//                               );
                               //event.setColor(-7875960);

                               sDialog.setTitleText("Accept")
                                       .setContentText("oppointment accepted")
                                       .setConfirmText("Accepted")
                                       .showCancelButton(false)
                                       .setCancelClickListener(null)
                                       .setConfirmClickListener(null)
                                       .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);

                           }
                       })
                       .show();

               System.out.println(event.getName() + "name");



           }

    }

    @Override
    public void onEmptyViewLongPress(Calendar time) {
        Toast.makeText(this, "Empty view long pressed: " + getEventTitle(time), Toast.LENGTH_SHORT).show();
    }

    public WeekView getWeekView() {
        return mWeekView;
    }


//    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
//
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            mProgressDialog = new ProgressDialog(getApplicationContext());
//            mProgressDialog.setTitle("List of appointements");
//            mProgressDialog.setMessage("Loading...");
//            mProgressDialog.setIndeterminate(false);
//            mProgressDialog.show();
//        }
//        @Override
//        protected Void doInBackground(Void... params) {
//            return null;
//
//        }
//        @Override
//        protected void onPostExecute(Void result) {
//            mProgressDialog.dismiss();
//
//
//        }
//    }

}

