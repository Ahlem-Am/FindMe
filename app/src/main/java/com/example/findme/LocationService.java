package com.example.findme;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.IBinder;
import android.telephony.SmsManager;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationService extends Service {
    public LocationService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String num = intent.getStringExtra("PHONE");
        System.out.println("location service" + num);
        System.out.println(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);

        //recuperation position gps
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
            client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        System.out.println(latitude+" "+longitude);
                        SmsManager manager = SmsManager.getDefault();
                    System.out.println("send msg");
                        manager.sendTextMessage(

                                num,
                                null,
                                "FindMe:Position :" + latitude + "_" + longitude,
                                null,
                                null);
                    System.out.println("send msg");

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

