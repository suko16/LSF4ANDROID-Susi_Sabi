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

/**
 * Created by Sabi on 07.05.2017.
 */

public class CreateNotificationActivity extends Activity {




    NotificationManager notificationManager;
    Notification noti;
    long[] vibrate = {0,100};
    NotificationCompat.Builder b;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_create);
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        createNotification();
    }

    public void createNotification() {
        // Prepare intent which is triggered if the
        // notification is selected
           // Build notification
           // Actions are just fake




        //   b.setAutoCancel(true)
              //
        b = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_menu_gallery)
                .setTicker("Deine Veranstaltung fällt aus")
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentText("Deine Veranstaltung fällt aus")
                .setContentTitle("LSF4Android");

        Intent intent = new Intent(this, NotificationReceiverActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

       // noti.vibrate = vibrate;

        notificationManager.notify(1, b.build());



      /*  noti = new Notification.Builder(this.getApplication())
                .setContentTitle("LSF4Android")
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setContentText("Deine Veranstaltung fällt aus").build();
*/





       /* noti = new Notification.Builder(this)
                   .setContentTitle("LSF4Android")
                   .setSmallIcon(R.drawable.ic_menu_camera)
                   .setContentText("Deine Veranstaltung fällt aus").build();

  //         noti.vibrate = vibrate;


           notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


           // hide the notification after its selected
           noti.flags |= Notification.FLAG_AUTO_CANCEL;

           notificationManager.notify(0, noti);

*/
    }
}
