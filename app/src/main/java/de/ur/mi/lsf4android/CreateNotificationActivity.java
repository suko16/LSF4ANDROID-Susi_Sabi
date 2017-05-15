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
    Context context = this;




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

        Intent intent = new Intent(this, NotificationReceiverActivity.class);
        intent.putExtra("titel",titelAusfallendeVeranstaltung);
        PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);


        b.setSmallIcon(R.drawable.ic_menu_gallery)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setContentText("Deine Veranstaltung" + titelAusfallendeVeranstaltung + "fällt aus")
                .setContentTitle("LSF4Android")
                .setContentIntent(pIntent);

        notificationManager.notify(1, b.build());


          /*  noti.vibrate = vibrate;







           // hide the notification after its selected
           noti.flags |= Notification.FLAG_AUTO_CANCEL;

           notificationManager.notify(0, noti);

       */
    }
}
