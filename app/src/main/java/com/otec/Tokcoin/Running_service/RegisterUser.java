package com.otec.Tokcoin.Running_service;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.otec.Tokcoin.R;
import com.otec.Tokcoin.utils.utilJava;



import java.net.URL;

import me.pushy.sdk.Pushy;

public class RegisterUser extends AsyncTask<Void,Void,Object> {

    private Activity activity;
    private  String TAG="RegisterUser";
    public RegisterUser(Activity context) {
        this.activity = context;
    }

    @Override
    protected Object doInBackground(Void... voids) {
        try {
            String deviceToken = Pushy.register(activity.getApplicationContext());
            new URL("https://com.otec.Tokcoin/regsiter/device?token="+deviceToken).openConnection();
            return  deviceToken;
        } catch (Exception e) {

            return e;
        }
    }


    @Override
    protected void onPostExecute(Object o) {
        if(o instanceof Exception){
            Toast.makeText(activity, "Error occurred while request for device token pls turn on internet connection and relaunch app", Toast.LENGTH_LONG).show();
            new utilJava().init(activity.getApplicationContext()).edit().putString(activity.getString(R.string.DEVICE_TOKEN),"").apply();
        }
        else
            new utilJava().init(activity.getApplicationContext()).edit().putString(activity.getString(R.string.DEVICE_TOKEN),String.valueOf(o)).apply();
            Log.d(TAG, "onPostExecute: "+o);
    }

}
