package com.example.selim.h2h.utils;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

import com.parse.ParsePushBroadcastReceiver;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ouss on 22/12/2015.
 */
public class MyPushBroadcastReceiver extends ParsePushBroadcastReceiver {

    public static final String PARSE_DATA_KEY = "com.parse.Data";


    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
    }

    @Override
    protected void onPushReceive(final Context context, Intent intent) {
        //super.onPushReceive(context, intent);
    }

    @Override
    protected Notification getNotification(Context context, Intent intent) {
        return super.getNotification(context, intent);
    }

    private void buildNotif(Bitmap bitmap, ParseUser parseUser, Context context)
    {

    }

    private JSONObject getDataFromIntent(Intent intent) {
        JSONObject data = null;
        try {
            data = new JSONObject(intent.getExtras().getString(PARSE_DATA_KEY));
        } catch (JSONException e) {

        }
        return data;
    }


}
