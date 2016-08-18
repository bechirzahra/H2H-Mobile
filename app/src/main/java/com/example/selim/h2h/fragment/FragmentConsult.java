package com.example.selim.h2h.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.LinearLayout;

import com.example.selim.h2h.activity.AccueilPatient;
import com.example.selim.h2h.R;
import com.example.selim.h2h.activity.ActivityAddCalander;
import com.example.selim.h2h.activity.LoginActivity;
import com.example.selim.h2h.adapter.ListAppointmentsAdapter;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


import java.util.List;

/**
 * Created by selim on 03/02/2016.
 */
public class FragmentConsult extends Fragment{
    Context context;
    Activity activity;
    ListView listView;
    List<ParseObject> listAppointments;
    ProgressDialog mProgressDialog;
    ListAppointmentsAdapter listAppointmentsAdapter;
    FloatingActionButton buttonAdd ;
    LinearLayout linearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consult_appointments, container, false);
        View view2 = inflater.inflate(R.layout.add_oppointment, container, false);
        buttonAdd = (FloatingActionButton)view.findViewById(R.id.buttonAddO);
        linearLayout = (LinearLayout)view2.findViewById(R.id.linear2);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
//               Fragment fragment = new FragmentAddCalander();
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragmentContent, fragment).commit();
                //linearLayout.setVisibility(View.GONE);
                startActivity(new Intent(activity, ActivityAddCalander.class));
                activity.finish();

            }
        });
        listView = (ListView)view.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context," Appoitement "+position,Toast.LENGTH_LONG).show();
                //ParseObject bb= listAppointments.get(position);


            }
        });
        new RemoteDataTask().execute();
        return view;
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(activity);
            mProgressDialog.setTitle("List of appointements");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                ParseQuery<ParseObject> query = new ParseQuery<>(
                        "RendezVous");
                query.orderByDescending("createdAt");
                query.whereEqualTo("IdPatient", ParseUser.getCurrentUser().getObjectId());
                listAppointments = query.find();
                Log.d("tag2",""+listAppointments.size());

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }



        @Override
        protected void onPostExecute(Void result) {
            listAppointmentsAdapter = new ListAppointmentsAdapter(context,R.layout.one_appointment,listAppointments);
            listView.setAdapter(listAppointmentsAdapter);
            mProgressDialog.dismiss();


        }
    }

    public void onBackKeyPressed() {
        if (getView() != null) {
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                        FragmentConsult.this.startActivity(new Intent(activity, AccueilPatient.class));
                        activity.finish();
                        return true;

                    }
                    return false;
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        onBackKeyPressed();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
}
