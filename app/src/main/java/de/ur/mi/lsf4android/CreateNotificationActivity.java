//verglichen
//hat Sabi gebaut


package de.ur.mi.lsf4android;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Sabi on 07.05.2017.
 */

public class CreateNotificationActivity extends Activity {




    NotificationManager notificationManager;
    Notification noti;
    long[] vibrate = {0,100};
    NotificationCompat.Builder b;
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private Context context;


    public CreateNotificationActivity(){
    }

    public CreateNotificationActivity(Context context){
        this.context = context;
        notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        b = new NotificationCompat.Builder(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_create);
    }


    public void createNotification(String titelAusfallendeVeranstaltung, String date, int notificationID) {



        //Erstellt trotz permanenter Aktulaisierung keine Notations, die bereits angezeigt werden
        StatusBarNotification[] AllActiveNotifications = notificationManager.getActiveNotifications();
        if(AllActiveNotifications!= null) {
            for(int r = 0; r<AllActiveNotifications.length; r++) {
                if (AllActiveNotifications[r].getNotification().tickerText.toString().equals(titelAusfallendeVeranstaltung)) {
                    return;
                }
            }
        }


        Intent notificationIntent = new Intent(context, AusfallendeActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);



        b.setSmallIcon(R.mipmap.uni_logo)

                .setContentText("Am " + date + " entfällt '" + titelAusfallendeVeranstaltung + "'")
                .setContentTitle("LSF4Android")
                .setSortKey(date)
                .setAutoCancel(true) // hide the notification after its selected
                .setVibrate(new long[] { 1000, 1000, 1000 })
                .setContentIntent(pIntent)
                .setColor(Color.BLUE)
                .setTicker(titelAusfallendeVeranstaltung)

        ;

        notificationManager.notify(notificationID, b.build());


    }

}
