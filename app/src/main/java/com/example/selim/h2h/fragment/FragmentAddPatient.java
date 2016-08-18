package com.example.selim.h2h.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.selim.h2h.utils.MyApp;
import com.example.selim.h2h.R;
import com.parse.ParseException;
import com.parse.ParseRelation;
import com.parse.ParseUser;

/**
 * Created by selim on 10/02/2016.
 */
public class FragmentAddPatient extends Fragment{
    Context context;
    Activity activity;
    EditText adress,phone,username,password,security;
    Button ajouter;
    ParseUser parseUser;
    MyApp myApp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseUser =new ParseUser();
        myApp = (MyApp)context.getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_patient, container, false);
        adress = (EditText)view.findViewById(R.id.address_btn_pa);
        phone = (EditText)view.findViewById(R.id.phone_btn_pa);
        username = (EditText)view.findViewById(R.id.username_btn_pa);
        password = (EditText)view.findViewById(R.id.password_btn);
        security = (EditText)view.findViewById(R.id.phone_btn_patient_securty);


        ajouter = (Button)view.findViewById(R.id.add_patient);
        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!password.getText().toString().equals(""))
                    parseUser.put("password", password.getText().toString());
                    parseUser.put("username",username.getText().toString());
                    parseUser.put("Type","Patient" );
                    parseUser.put("Phone",phone.getText().toString());
                    parseUser.put("adresse",adress.getText().toString());
                    parseUser.put("securityPhone",security.getText().toString());
                    ParseRelation relation = parseUser.getRelation("idDoctor");
                    relation.add(ParseUser.getCurrentUser());
                try {

                    //ParseACL acl = ParseUser.getCurrentUser().getACL();
                    String session = ParseUser.getCurrentUser().getSessionToken();
                    parseUser.signUp();

                    ParseUser.getCurrentUser().become(session);
                    Toast.makeText(FragmentAddPatient.this.context.getApplicationContext(), "Added",
                                        Toast.LENGTH_SHORT).show();
                                Fragment fragment1 = new FragmentConsultPatient();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.fragmentContent, fragment1).commit();

//                    parseUser.saveInBackground(new SaveCallback() {
//                        @Override
//                        public void done(ParseException e) {
//                            if (e == null) {
//                                Toast.makeText(FragmentAddPatient.this.context.getApplicationContext(), "Added",
//                                        Toast.LENGTH_SHORT).show();
//                                Fragment fragment1 = new FragmentConsultPatient();
//                                FragmentManager fragmentManager = getFragmentManager();
//                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                fragmentTransaction.replace(R.id.fragmentContent, fragment1).commit();
//
//                            } else
//                                Log.e("tag1", "e not null" + e.getMessage());
//                        }
//                    });
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Cannot add the patient", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
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
