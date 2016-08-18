package com.example.selim.h2h.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.selim.h2h.R;
import com.example.selim.h2h.activity.AccueilDoctor;
import com.example.selim.h2h.activity.AccueilPatient;
import com.example.selim.h2h.adapter.ListHistoriqueAdapter;
import com.example.selim.h2h.adapter.ListPrescriptionAdapter;
import com.example.selim.h2h.utils.MyApp;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by selim on 23/02/2016.
 */
public class FragmentHistorique extends Fragment {


    Context context;
    Activity activity;
    ListView listView;
    List<ParseObject> listHistorique;
    ProgressDialog mProgressDialog;
    ListHistoriqueAdapter listHistoriqueAdapter;
    LinearLayout linearLayout;
    ParseUser user;
    ParseObject object;
    MyApp myApp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myApp =(MyApp)activity.getApplicationContext();
        user = myApp.userPatient;




    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.historique, container, false);



        listView = (ListView)view.findViewById(R.id.listView3);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, " Historique " + position, Toast.LENGTH_LONG).show();
                //ParseObject bb= listAppointments.get(position);
                object= listHistorique.get(position);
                System.out.println(object.getObjectId()+"ccccccccccc");
                //System.out.println("bonjour1");
//                Fragment fragment = new FragmentEditProfile();
//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.fragmentContent, fragment).commit();

               // System.out.println("bonjour");

                Fragment fragment1 = new BlankFragmentHistorique();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContent, fragment1).commit();
//                FragmentHistorique.this.startActivity(new Intent(activity, AccueilDoctor.class));
//                activity.finish();


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
            mProgressDialog.setTitle("Historique of seizure");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                ParseQuery<ParseObject> query = new ParseQuery<>(
                        "EEGBrain");
                query.orderByDescending("createdAt");
                query.whereEqualTo("Id", user.getObjectId());
                query.whereEqualTo("eegValue", 1);

                listHistorique = query.find();
                Log.d("tag2", "" + listHistorique.size());

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }



        @Override
        protected void onPostExecute(Void result) {
            listHistoriqueAdapter = new ListHistoriqueAdapter(context,R.layout.one_historique,listHistorique);
            listView.setAdapter(listHistoriqueAdapter);
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
                        FragmentHistorique.this.startActivity(new Intent(activity, AccueilDoctor.class));
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
