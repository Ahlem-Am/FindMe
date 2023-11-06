package com.example.findme;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.telephony.SmsManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationService extends Service {
    public LocationService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String num=intent.getStringExtra("PHONE");
        //recuperation position gps
        FusedLocationProviderClient client= LocationServices.getFusedLocationProviderClient(this);
        client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location==null){
                    double a=location.getLatitude();
                    double o=location.getLongitude();
                    SmsManager manager= SmsManager.getDefault();
                    manager.sendTextMessage(
                            null,
                            num,
                            "FindMe:Position :"+a+"_"+o,
                            null,
                            null);

                }
            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}