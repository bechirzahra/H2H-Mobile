package com.example.selim.h2h.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.selim.h2h.R;
import com.example.selim.h2h.adapter.ListAppointmentsAdapter;
import com.example.selim.h2h.utils.MyApp;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by selim on 14/02/2016.
 */
public class FragmentEMGChart extends Fragment {
    List<ParseObject> listeAcc = new ArrayList<ParseObject>();

    double x;
    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    Activity activity;
    Context context;
    ImageButton heart_img;
    MyApp myApp;
    // Bundle bundle;
    int id;
    ParseUser currentPatient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApp =(MyApp)activity.getApplicationContext();
        currentPatient = myApp.userPatient;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chart, container, false);
        // we get graph view instance
        GraphView graph = (GraphView)view.findViewById(R.id.graph);
        // data
        series = new LineGraphSeries<DataPoint>();
        graph.addSeries(series);
        // customize a little bit viewport
        Viewport viewport = graph.getViewport();
        viewport.setYAxisBoundsManual(true);
        viewport.setMinY(200);
        viewport.setMaxY(1200);

        viewport.setScrollable(true);

        ParseQuery<ParseObject> query = new ParseQuery<>("EMG");
        query.whereEqualTo("Id", currentPatient.getObjectId());
        // query.setLimit(20);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                listeAcc = list;
            }
        });
        return  view;

    }

    @Override
    public void onResume() {
        super.onResume();

        // we're going to simulate real time with thread that append data to the graph
        new Thread(new Runnable() {
            @Override
            public void run() {
                // we add 100 new entries
                for (int i = 0; i < listeAcc.size(); i++) {
                    final ParseObject item = listeAcc.get(i);
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry(item);
                            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("EMG");
                            query.whereEqualTo("Id", currentPatient.getObjectId());
                            query.orderByDescending("createdAt");

                            query.getFirstInBackground(new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject parseObject, ParseException e) {
                                    listeAcc.add(parseObject);
                                }
                            });



                        }
                    });

                    // sleep to slow down the add of entries
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        // manage error ...
                    }
                }
            }
        }).start();
    }

    private void addEntry(ParseObject item) {
        //ParseObject parseObject = new ParseObject("Acceleoremeter");
        x= (Integer)item.get("emgValue");
        Log.e("x", x + "");
        // here, we choose to display max 10 points on the viewport and we scroll to end
        series.appendData(new DataPoint(lastX++, x), true, 10);
        System.out.println("valeur" + x);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
}
