package com.example.selim.h2h.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.selim.h2h.R;
import com.example.selim.h2h.fragment.FragmentConsultOppDoctor;
import com.example.selim.h2h.fragment.FragmentConsultPatient;
import com.example.selim.h2h.fragment.FragmentDoctor;
import com.example.selim.h2h.fragment.FragmentDrawer;
import com.example.selim.h2h.fragment.FragmentEditProfilDoctor;
import com.example.selim.h2h.fragment.FragmentEditProfile;
import com.example.selim.h2h.fragment.FragmentPatient;
import com.example.selim.h2h.utils.CircleTransformation;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

public class AccueilDoctor extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    Toolbar mToolbar;
    FragmentDrawer drawerFragment;
    FragmentManager fragmentManager;
    ImageView img;
    ParseUser parseUser;
    Context context;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acceuil_patient);
        parseUser = ParseUser.getCurrentUser();

        fragmentManager = getSupportFragmentManager();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        Fragment fragment = new FragmentConsultPatient();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContent, fragment).commit();
        ParseFile image = (ParseFile) parseUser.get("Image");

        img =(ImageView)findViewById(R.id.frag_img);
        if(image != null) {
            Uri imageUri = Uri.parse(image.getUrl());
            Picasso.with(this).load(imageUri.toString()).transform(new CircleTransformation()).into(img);
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new FragmentEditProfilDoctor();
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
                fragment = new FragmentConsultPatient();
                title = getString(R.string.title_pat);
                break;
            case 1:
                Intent intent = new Intent(AccueilDoctor.this, BasicActivityDoctor.class);
                startActivity(intent);
                title = getString(R.string.title_rend);
                break;
            case 2:
                new AlertDialog.Builder(AccueilDoctor.this)
                        .setMessage("Voulez vous vraiment sortir de l'application?")
                        .setCancelable(false)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ParseUser.getCurrentUser().logOutInBackground();
                                Intent intent = new Intent(AccueilDoctor.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("Non", null)
                        .show();
                break;

            default:
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
