package com.example.findme;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MySmsReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String messageBody, phoneNumber;
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                final SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                if (messages.length > -1) {
                    messageBody = messages[0].getMessageBody();
                    phoneNumber = messages[0].getDisplayOriginatingAddress();

                    Toast.makeText(context,
                                    "Message : " + messageBody + "Reçu de la part de;" + phoneNumber,
                                    Toast.LENGTH_LONG)
                            .show();
                    if (messageBody.contains("FindMe:send me your position")) {
                        System.out.println("send me");
                        //lancer un service qui recuppere la position gps et le renvoie
                        Intent i = new Intent(context, LocationService.class);
                        i.putExtra("PHONE", phoneNumber);
                        System.out.println(phoneNumber);
                        context.startService(i);
                    }
                    System.out.println("recu");
                    if (messageBody.contains("FindMe:Position :")) {
                        System.out.println("FindMe:Position :");
                        //lancer un service qui recuppere la position gps et le renvoie
                        Intent i = new Intent(context, LocationService.class);
                        String[] msg = (messageBody.split("FindMe:Position :"))[1].split("_");
                        System.out.println("FindMe:Position :" + msg[0] + msg[1]);

                        String Longitude = msg[1];
                        String Latitude = msg[0];
                        //notif build
                        NotificationCompat.Builder notif = new NotificationCompat.Builder(
                                context, "canal");
                        notif.setContentText("Voir la position");
                        notif.setContentTitle("position recue");
                        notif.setSmallIcon(android.R.drawable.ic_dialog_map);
                        notif.setAutoCancel(true);

                        Intent in = new Intent(context, MapsActivity.class);
                        in.putExtra("longitude", String.valueOf(Longitude));
                        in.putExtra("latitude", String.valueOf(Latitude));
                        PendingIntent pi = PendingIntent.getActivity(context, 1, in, PendingIntent.FLAG_MUTABLE);
                        notif.setContentIntent(pi);
                        // gestionnaire de notif
                        NotificationManagerCompat manager = NotificationManagerCompat.from(context);

                        NotificationChannel channel = new NotificationChannel(
                                "canal", "mon canal", NotificationManager.IMPORTANCE_DEFAULT);

                        manager.createNotificationChannel(channel);

                        manager.notify(1, notif.build());




                    }

                }
            }
        }
    }
}