//verglichen
//hat Sabi gebaut


package de.ur.mi.lsf4android;

/**
 * Created by Sabi on 07.05.2017.
 */


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationReceiverActivity extends Activity {
    // prepare intent which is triggered if the
// notification is selected
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_result);
        result = (TextView) findViewById(R.id.notification_result);
        result.setText("Du hast die Notification aufgerufen");
    }


}
