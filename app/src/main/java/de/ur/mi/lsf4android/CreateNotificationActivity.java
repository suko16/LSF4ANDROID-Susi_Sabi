//verglichen
//hat Sabi gebaut


package de.ur.mi.lsf4android;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Sabi on 07.05.2017.
 */

public class CreateNotificationActivity extends Activity {




    NotificationManager notificationManager;
    Notification noti;
    long[] vibrate = {0,100};
    NotificationCompat.Builder b;
    Context context = this;
    public static final String LOG_TAG = MainActivity.class.getSimpleName();


    public CreateNotificationActivity(){
    }

    public CreateNotificationActivity(Context context){
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
        for(int r = 0; r<AllActiveNotifications.length; r++){
            if(AllActiveNotifications[r].getNotification().getSortKey().equals(titelAusfallendeVeranstaltung)){

                return;
            }
        }


/*

        Intent notificationIntent = new Intent(CreateNotificationActivity.this, AusfallendeFragment.class);
        //Fehler: java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String android.content.Context.getPackageName()' on a null object reference
        //Absturz
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
*/
     //   noti.vibrate = vibrate;



        b.setSmallIcon(R.mipmap.uni_logo)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentText("Am " + date + " entfällt '" + titelAusfallendeVeranstaltung + "'")
                .setContentTitle("LSF4Android")
                .setSortKey(titelAusfallendeVeranstaltung)

               // .setContentIntent(pIntent)
        ;

        notificationManager.notify(notificationID, b.build());

    

         // hide the notification after its selected
          /* noti.flags |= Notification.FLAG_AUTO_CANCEL;

           notificationManager.notify(0, noti);
*/

    }

}
