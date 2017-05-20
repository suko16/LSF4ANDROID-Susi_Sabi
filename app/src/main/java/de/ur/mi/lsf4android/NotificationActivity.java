package de.ur.mi.lsf4android;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;

public class NotificationActivity extends Activity {



    private NotificationManager notificationManager;
    private NotificationCompat.Builder b;
    private Context context;

    public NotificationActivity(){
    }

    public NotificationActivity(Context context){
        this.context = context;
        notificationManager = (NotificationManager) context.getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        b = new NotificationCompat.Builder(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
    }

    public void createNotification(String titelAusfallendeVeranstaltung, String date, int notificationID) {

        //check if the notification comming from the service is already displayed
        StatusBarNotification[] AllActiveNotifications = notificationManager.getActiveNotifications();
        if(AllActiveNotifications!= null) {
            for(int r = 0; r<AllActiveNotifications.length; r++) {
                if (AllActiveNotifications[r].getNotification().tickerText.toString().equals(titelAusfallendeVeranstaltung)) {
                    return;
                }
            }
        }

        //calls the cancelledCoursesActivity if the notification is clicked
        Intent notificationIntent = new Intent(context, Cancelled_Courses_Activity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);


        //the builder creates the notification/-layout
        b.setSmallIcon(R.mipmap.uni_logo)
                .setContentText("Am " + date + " entfällt '" + titelAusfallendeVeranstaltung + "'")
                .setContentTitle("LSF4Android")
                .setSortKey(date)
                .setAutoCancel(true) // hide the notification after its selected
                .setVibrate(new long[] { 1000, 1000, 1000 })
                .setContentIntent(pIntent)
                .setColor(Color.BLUE)
                .setTicker(titelAusfallendeVeranstaltung);
        notificationManager.notify(notificationID, b.build());
    }
}
