package com.example.selim.h2h.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.selim.h2h.utils.MyApp;
import com.example.selim.h2h.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by selim on 13/02/2016.
 */
public class FragmentChart extends Fragment {
    List<ParseObject> listeAcc = new ArrayList<ParseObject>();
    Activity activity;
    Context context;
    double x;
    private static final Random RANDOM = new Random();
    private LineGraphSeries<DataPoint> series;
    private int lastX = 0;
    MyApp myApp;

    ParseUser currentPatient;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApp = (MyApp)activity.getApplicationContext();
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
        viewport.setMinY(-1);
        viewport.setMaxY(1);

        viewport.setScrollable(true);

        ParseQuery<ParseObject> query = new ParseQuery<>("Acceleoremeter");
        query.whereEqualTo("IdPatient",currentPatient.getObjectId());
        query.setLimit(20);
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
                for (int i =0; i< listeAcc.size();i++) {
                    final ParseObject item = listeAcc.get(i);
                            ((Activity) context).runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            addEntry(item);
                            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Acceleoremeter");
                            query.whereEqualTo("IdPatient",currentPatient.getObjectId());
                            query.orderByDescending("createdAt");

                            try {
                                ParseObject obj = query.getFirst();
                                listeAcc.add(obj);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

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




    // add random data to graph
    private void addEntry(ParseObject item) {
        //ParseObject parseObject = new ParseObject("Acceleoremeter");
        x= (double)item.get("X");
        Log.e("x", x + "");
        // here, we choose to display max 10 points on the viewport and we scroll to end
        series.appendData(new DataPoint(lastX++, x), true, 10);
        System.out.println("valeur" + x);
    }


}