package com.example.selim.h2h.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.selim.h2h.activity.AccueilDoctor;
import com.example.selim.h2h.activity.AccueilPatient;
import com.example.selim.h2h.utils.MyApp;
import com.example.selim.h2h.R;
import com.example.selim.h2h.utils.CircleTransformation;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

/**
 * Created by selim on 03/02/2016.
 */
public class FragmentEditProfile extends Fragment {
    EditText phoneUser,addressUser,pwdUser,pwdUserCheck,phoneSecurity;
    Button update;
    ImageView imgUser;
    Context context;
    ParseUser parseUser;
    MyApp myApp;
    Activity activity;
    Bitmap bmp;
    ParseFile parseFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseUser = ParseUser.getCurrentUser();
        myApp = (MyApp)context.getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_profile, container, false);

        imgUser = (ImageView)view.findViewById(R.id.imgUser);
        phoneUser = (EditText)view.findViewById(R.id.telUser);
        addressUser = (EditText)view.findViewById(R.id.addressUser);
        pwdUser = (EditText)view.findViewById(R.id.pwdUser);
        pwdUserCheck = (EditText)view.findViewById(R.id.pwdUserCheck);
        phoneSecurity =(EditText)view.findViewById(R.id.phone_security_btn);
        update = (Button)view.findViewById(R.id.btnUpdate);

        ParseFile image = (ParseFile) parseUser.get("Image");//live url
        if(image != null) {
            Uri imageUri = Uri.parse(image.getUrl());
            Picasso.with(context).load(imageUri.toString()).transform(new CircleTransformation()).into(imgUser);
        }

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 30);
            }
        });


        phoneUser.setText(parseUser.getString("Phone"));
        addressUser.setText(parseUser.getString("adresse"));
        phoneSecurity.setText(parseUser.getString("securityPhone"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!pwdUser.getText().toString().equals(""))
                    parseUser.put("password", pwdUser.getText().toString());
                    initUser();

        }});

        return view;
    }

    public void initUser()
    {
        if (bmp != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            parseFile = new ParseFile("h2h", byteArray);
            parseFile.saveInBackground();
            parseUser.put("Image", parseFile);
        }
        parseUser.put("Phone", phoneUser.getText().toString());
        parseUser.put("adresse", addressUser.getText().toString());
        parseUser.put("securityPhone", phoneSecurity.getText().toString());
        parseUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(FragmentEditProfile.this.context.getApplicationContext(), "Updated",
                            Toast.LENGTH_SHORT).show();
//                    Fragment fragment1 = new FragmentPatient();
//                    FragmentManager fragmentManager = getFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.fragmentContent, fragment1).commit();
                }
                else
                    Log.e("tag1", "e not null" +e.getMessage());
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
        Log.d("ffff", "pfff");
        if(resultCode == Activity.RESULT_OK) {
            Bundle bundle = new Bundle();
            if (requestCode == 30) {
                Log.d("wtf", "30");
                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = context.getContentResolver().query(selectedImage, filePath,
                        null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);

                bmp = BitmapFactory.decodeFile(c.getString(columnIndex)); // load
                c.close();
                // preview image
                bmp = Bitmap.createScaledBitmap(bmp, 400, 400, false);

                bundle.putParcelable("img", bmp);

                imgUser.setImageBitmap(bmp);


            } else if (requestCode == 31) {
                Log.d("wtf", "zzzza");
                bmp = (Bitmap) data.getExtras().get("data");
                bundle.putParcelable("img", bmp);
            }

        }
        else {
            Toast.makeText(this.context.getApplicationContext(), "Cancelled",
                    Toast.LENGTH_SHORT).show();
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
                       // FragmentEditProfile.this.startActivity(new Intent(activity, AccueilPatient.class));
                            FragmentEditProfile.this.startActivity(new Intent(activity, AccueilPatient.class));
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
