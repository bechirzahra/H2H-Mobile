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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.selim.h2h.R;
import com.example.selim.h2h.activity.AccueilDoctor;
import com.example.selim.h2h.adapter.ListAppointmentsAdapter;
import com.example.selim.h2h.utils.MyApp;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by selim on 22/02/2016.
 */
public class FragmentAddPrescription extends Fragment {
    ProgressDialog mProgressDialog;
    Context context;
    Activity activity;
    TextView comment;
    Button add_pres;
    MyApp myApp;
    ParseUser currentPatient;
    ParseUser currentDoctor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myApp = (MyApp)activity.getApplicationContext();
        currentPatient = myApp.userPatient;
        currentDoctor = new ParseUser().getCurrentUser();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View x = inflater.inflate(R.layout.add_prescription, container, false);
        comment = (EditText)x.findViewById(R.id.comment_txt);
        add_pres = (Button)x.findViewById(R.id.add_prescription_btn);
    add_pres.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ParseObject parseObject = new ParseObject("Prescription");
        parseObject.put("comment", comment.getText().toString());
        parseObject.put("IdPatient", currentPatient.getObjectId());
        parseObject.put("IdDoctor",currentDoctor.getObjectId());
        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                public void done(ParseException e) {
                    if(e == null)
                        Log.e("tag1", "null");
                    else
                        Log.e("tag1","e not null");
                }
            });
        Fragment fragment1 = new BlankFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContent, fragment1).commit();
        new RemoteDataTask().execute();
    }
});

        return  x;
    }
    public void onBackKeyPressed() {
        if (getView() != null) {
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                        // FragmentEditProfile.this.startActivity(new Intent(activity, AccueilPatient.class));
                        Fragment fragment1 = new FragmentConsultPatient();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentContent, fragment1).commit();
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

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(activity);
            mProgressDialog.setTitle("Add a prescription");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();


        }

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
