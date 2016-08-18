package com.example.selim.h2h.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.ImageView;
import android.widget.ListView;

import com.example.selim.h2h.activity.AccueilDoctor;
import com.example.selim.h2h.utils.MyApp;
import com.example.selim.h2h.R;
import com.example.selim.h2h.adapter.ListPatientAdapter;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by selim on 10/02/2016.
 */
public class FragmentConsultPatient extends Fragment {
    Activity activity;
    Context context;
    ListView listView;
    List<ParseUser> listPatient;
    ProgressDialog mProgressDialog;
    ListPatientAdapter listpatientsAdapter;
    FloatingActionButton buttonAdd ;
    ParseUser user;
    ImageView call;
    MyApp myApp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new ParseUser();
        myApp = (MyApp)activity.getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.consult_patient, container, false);
        listView = (ListView) view.findViewById(R.id.listView1);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                myApp.userPatient = listPatient.get(position);

                System.out.println(myApp.userPatient+"rrrrrrrrrrrrrr");

                Fragment fragment = new BlankFragment();
                //Bundle bundle = new Bundle();
                //bundle.putString("id_user",user.getObjectId());

                //fragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContent, fragment).commit();


            }
        });
        //System.out.println(user.getObjectId()+" bonjour");
        buttonAdd = (FloatingActionButton)view.findViewById(R.id.buttonAddPat);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentAddPatient();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContent, fragment).commit();
            }
        });
        new RemoteDataTask().execute();
        return  view;
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(activity);
            mProgressDialog.setTitle("List of Patients");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                ParseQuery<ParseUser> query = ParseUser.getQuery();

                query.orderByDescending("createdAt");
                query.whereEqualTo("idDoctor", ParseUser.getCurrentUser());
                listPatient = query.find();
                Log.d("tag2", "" + listPatient.size());

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }



        @Override
        protected void onPostExecute(Void result) {
            listpatientsAdapter = new ListPatientAdapter(context,R.layout.one_patient,listPatient);
            listView.setAdapter(listpatientsAdapter);
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
                        FragmentConsultPatient.this.startActivity(new Intent(activity, AccueilDoctor.class));
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
        this.context=context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;
    }
}
