package com.example.selim.h2h.activity;

import android.content.DialogInterface;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alamkanak.weekview.DateTimeInterpreter;
import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.example.selim.h2h.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by selim on 21/02/2016.
 */
public class BasicActivity extends BaseActivity implements View.OnClickListener{
    ListView listView;
    List<ParseObject> listAppointments;
    Calendar startTime;
    Calendar endTime;
    WeekViewEvent event;
    int year,month,day,hours,minute;

    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with some events.
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        ParseQuery<ParseObject> query = new ParseQuery<>(
                "RendezVous");
        query.orderByDescending("DateRendezVous");
        query.whereEqualTo("IdPatient", ParseUser.getCurrentUser().getObjectId());
        try {
            listAppointments = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i =0; i< listAppointments.size();i++) {
            ParseObject item = listAppointments.get(i);
            Date date = item.getDate("DateRendezVous");
            System.out.println("date" + date);
            int v = Integer.parseInt(date.toString().substring(8, 10));
            System.out.println("ggggggg" + v);
            System.out.println(listAppointments.size() + "cccccccccccc");
            year = date.getYear() + 1900;
            System.out.println("yyyyyyyyyyyyyy" + year);
            month = date.getMonth();
            System.out.println("eeeeeeeeeeeee" + month);
            day = date.getDay();
            System.out.println("rrrrrrrrrrrrr" + day);
            hours = date.getHours();
            System.out.println("hhhhhhhhhhhh" + hours);
            minute = date.getMinutes();
            startTime = Calendar.getInstance();

            startTime.set(Calendar.DAY_OF_MONTH, v);
            startTime.set(Calendar.HOUR_OF_DAY, hours - 1);
            startTime.set(Calendar.MINUTE, minute);
            startTime.set(Calendar.MONTH, month);
            startTime.set(Calendar.YEAR, year);
            endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR, 1);



            WeekViewEvent event = new WeekViewEvent(0, getEventTitle(startTime), startTime, endTime);

            if (new Date().after(item.getDate("DateRendezVous"))) {
                event.setColor(getResources().getColor(R.color.event_color_04));
            }
            if (new Date().equals(item.getDate("DateRendezVous"))) {
                event.setColor(getResources().getColor(R.color.event_color_01));
            }
            if ((new Date().before(item.getDate("DateRendezVous"))) && ((item.getInt("Confirmation"))==1  ))
                event.setColor(getResources().getColor(R.color.event_color_03));

            else if ((new Date().before(item.getDate("DateRendezVous"))) && ((item.getInt("Confirmation"))==0  ))
                event.setColor(getResources().getColor(R.color.event_color_02));


            events.add(event);

        }


        return events;
    }


    @Override
    public void onClick(View v) {

    }
}
