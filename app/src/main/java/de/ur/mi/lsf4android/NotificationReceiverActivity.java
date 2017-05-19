//verglichen
//hat Sabi gebaut


package de.ur.mi.lsf4android;



import android.app.Activity;
import android.content.Intent;
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

    }

    @Override
    protected void onNewIntent(Intent intent){
        result.setText("Du hast die Notification aufgerufen");
    }

}
