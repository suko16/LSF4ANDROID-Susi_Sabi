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


    public void createNotification(String titelAusfallendeVeranstaltung) {

     //   noti = new Notification();
/*

        Intent notificationIntent = new Intent(CreateNotificationActivity.this, AusfallendeFragment.class);
        //Fehler: java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String android.content.Context.getPackageName()' on a null object reference
        //Absturz
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
*/
     //   noti.vibrate = vibrate;

        b.setSmallIcon(R.drawable.ic_menu_gallery)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentText("Deine Veranstaltung" + titelAusfallendeVeranstaltung + "fällt aus")
                .setContentTitle("LSF4Android")
               // .setContentIntent(pIntent)
        ;

        notificationManager.notify(1, b.build());


          /*




           // hide the notification after its selected
           noti.flags |= Notification.FLAG_AUTO_CANCEL;

           notificationManager.notify(0, noti);

       */
    }
}
