package com.example.selim.h2h.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.selim.h2h.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class LoginActivity extends Activity {

    EditText login, password;
    Button connect;
    ProgressDialog mProgressDialog;

    ParseObject parseObject;
    ParseUser parseUser;
    int num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        parseUser = ParseUser.getCurrentUser();
//        //parseObject = new ParseObject("EEGBrain");
//ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("EEGBrain");
//
//        //ParseRelation relation = parseObject.getRelation("IdPatient");
//       // ParseQuery query = ParseUser.getQuery();
//        query.whereEqualTo("IdPatient", ParseUser.getCurrentUser());
//        query.orderByDescending("createdAt");
//
//        try {
//            parseObject = query.getFirst();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        if(parseObject.get("eegValue").equals(1))
//           parseUser= ParseUser.getCurrentUser();
//        num=parseUser.getInt("phoneSecurite");
//
//
//            SmsManager.getDefault().sendTextMessage(num, null, messageToSend, null,null);

        if(ParseUser.getCurrentUser()!=null)
        {

//
//
//            ParseObject parseObject1 = new ParseObject("Acceleoremeter");
//            ParseRelation relation1 = parseObject1.getRelation("IdPatient");
//            relation1.add(ParseUser.getCurrentUser());
//            parseObject1.put("X",0.2);
//            parseObject1.saveInBackground(new SaveCallback() {
//                @Override
//                public void done(ParseException e) {
//                    if(e == null)
//                        Log.e("tag1","null");
//                    else
//                        Log.e("tag1","e not null");
//                }
//            });
            if(ParseUser.getCurrentUser().get("Type").equals("Doctor"))
                startActivity(new Intent(LoginActivity.this, AccueilDoctor.class));
            else
                startActivity(new Intent(LoginActivity.this, AccueilPatient.class));
        }

        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        connect = (Button) findViewById(R.id.btnLogin);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(login.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (parseUser != null) {
                            mProgressDialog.dismiss();
                            if (parseUser.getString("Type").equals("Doctor"))
                            {
                                startActivity(new Intent(LoginActivity.this, AccueilDoctor.class));
                            }

                            else if (parseUser.getString("Type").equals("Patient"))
                            {
                                startActivity(new Intent(LoginActivity.this, AccueilPatient.class));
                            }
                            finish();

                        }
                        else{

                            Toast.makeText(getApplicationContext(),"Veuiller vérifier vos informations",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                mProgressDialog = new ProgressDialog(LoginActivity.this);
                mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                mProgressDialog.setMessage("Loading...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
