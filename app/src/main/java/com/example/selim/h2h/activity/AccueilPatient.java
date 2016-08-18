package com.example.selim.h2h.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.selim.h2h.R;
import com.example.selim.h2h.fragment.FragmentConsult;
import com.example.selim.h2h.fragment.FragmentDrawer;
import com.example.selim.h2h.fragment.FragmentEditProfilDoctor;
import com.example.selim.h2h.fragment.FragmentEditProfile;
import com.example.selim.h2h.fragment.FragmentListPrescription;
import com.example.selim.h2h.fragment.FragmentPatient;
import com.example.selim.h2h.utils.CircleTransformation;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by selim on 27/01/2016.
 */
public class AccueilPatient extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    Toolbar mToolbar;
    FragmentDrawer drawerFragment;
    FragmentManager fragmentManager;
    ParseUser parseUser;
    ParseObject parseObject;
    String messageToSend = "your child has an epleleptic sizure";
    String number = "25081169";
    String number2;
    ImageView img;
    Long x,y;
    List<ParseObject> listeAcc = new ArrayList<ParseObject>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceuil_patient);
 //       ParseQuery<ParseObject> query1 = new ParseQuery<>("GPS");

        // query.setLimit(20);
 //       query1.getFirstInBackground();
   //     query1.findInBackground(new FindCallback<ParseObject>() {
    //        @Override
     //       public void done(List<ParseObject> list, ParseException e) {
      //          listeAcc = list;
       //     }
       // });
        //for (int i = 0; i < listeAcc.size(); i++) {
         //    ParseObject item = listeAcc.get(i);
         //    x = (Long) item.get("Longitude");
         //    y = (Long) item.get("Altitude");
        //}
//
       //ParseObject parseObject1 = new ParseObject("GPS");
//        ParseRelation relation = parseObject1.getRelation("IdPatient");
//        relation.add(ParseUser.getCurrentUser());
//        parseObject1.put("eegValue", 1);
//        parseObject1.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if (e == null)
//                    Log.e("tag1", "null");
//                else
//                    Log.e("tag1", "e not null");
//            }
//        });
        parseUser = ParseUser.getCurrentUser();
        //parseObject = new ParseObject("EEGBrain");
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("EEGBrain");

        //ParseRelation relation = parseObject.getRelation("IdPatient");
        // ParseQuery query = ParseUser.getQuery();
        query.whereEqualTo("IdPatient", ParseUser.getCurrentUser());
        query.orderByDescending("createdAt");
        number2 = parseUser.getString("securityPhone");
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    if (e == null) {
                        if (parseObject.get("eegValue").equals(1))
                            Log.e("taag", "ok");
                        SmsManager.getDefault().sendTextMessage(number2, null, messageToSend+x+y, null,null);
                    } else
                        Log.e("taag2", "notok");
                    //if(parseObject.get("eegValue").equals(1))

                }
            });




            //SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null,null);

        fragmentManager = getSupportFragmentManager();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        Fragment fragment1 = new FragmentListPrescription();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContent, fragment1);
        fragmentTransaction.commit();

        ParseFile image = (ParseFile) parseUser.get("Image");

        img =(ImageView)findViewById(R.id.frag_img);
        if(image != null) {
            Uri imageUri = Uri.parse(image.getUrl());
            Picasso.with(this).load(imageUri.toString()).transform(new CircleTransformation()).into(img);
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new FragmentEditProfile();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContent, fragment).commit();
            }
        });

    }


    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                //fragment = new FragmentConsult();
                Intent intent = new Intent(AccueilPatient.this, BasicActivity.class);
                startActivity(intent);
                title = getString(R.string.title_rendp);
                break;
            case 1:
//                Intent intent1 = new Intent(AccueilPatient.this, BasicActivity.class);
//                startActivity(intent1);
                fragment = new FragmentListPrescription();
                title = getString(R.string.nav_list_p);
                break;

            default:
                break;

            case 2:

            //fragment = new FragmentConsult();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://h2h-esprit.azurewebsites.net/"));
                startActivity(browserIntent);
            title = getString(R.string.title_patp);
            break;
            case 3:
                new AlertDialog.Builder(AccueilPatient.this)
                        .setMessage("Voulez vous vraiment sortir de l'application?")
                        .setCancelable(false)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ParseUser.getCurrentUser().logOutInBackground();
                                Intent intent = new Intent(AccueilPatient.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Non", null)
                        .show();
                break;
        }

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContent, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
}
