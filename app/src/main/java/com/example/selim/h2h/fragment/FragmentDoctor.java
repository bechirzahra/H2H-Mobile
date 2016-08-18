package com.example.selim.h2h.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.selim.h2h.activity.LoginActivity;
import com.example.selim.h2h.R;
import com.parse.ParseUser;

/**
 * Created by selim on 10/02/2016.
 */
public class FragmentDoctor extends Fragment {
    Context context;
    Activity activity;
    Button editProfil,btnSignOut,btnConsult,btnPatient;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor, container, false);
        editProfil = (Button)view.findViewById(R.id.btn_edit_profile_doctor);
        editProfil.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new FragmentEditProfile();

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentContent, fragment).commit();

                    }
                });

        btnConsult = (Button)view.findViewById(R.id.btn_Consult_opp_doctor);
        btnConsult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentChart();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContent, fragment).commit();

            }
        });
        btnPatient = (Button)view.findViewById(R.id.consult_patient_btn);
        btnPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new FragmentConsultPatient();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContent, fragment).commit();
            }
        });
       btnSignOut = (Button)view.findViewById(R.id.btn_SignOut_doctor);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                startActivity(new Intent(activity, LoginActivity.class));
                activity.finish();
            }
        });
        return view;
    }
}
