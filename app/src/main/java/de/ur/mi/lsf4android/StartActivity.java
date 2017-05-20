//fertig
//von Susi gebaut


package de.ur.mi.lsf4android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class StartActivity extends AppCompatActivity {

    private Button cancelled_courses_button;
    private Button own_courses_button;
    private Button course_overview_button;
    private String course_overview_html = "https://lsf.uni-regensburg.de/qisserver/rds?state=wtree&search=1&trex=step&root120171=40852&P.vx=mittel";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        cancelled_courses_button = (Button)findViewById(R.id.ausfallende_v_button);
        own_courses_button = (Button)findViewById(R.id.eigene_v_button);
        course_overview_button = (Button)findViewById(R.id.alle_v_button);

        //clickListener for all buttons
        cancelled_courses_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAusfallend();
            }
        });
        own_courses_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEigene();
            }
        });

        course_overview_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAlle();
            }
        });
        //start the Service in the background
        Intent Service = new Intent(this, BackgroundService.class);
        startService(Service);
    }

    private void clickAusfallend (){
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat datumsformat = new SimpleDateFormat("dd.MM.yyyy");
        String date = datumsformat.format(calendar.getTime());

        Intent i = new Intent(this,Cancelled_Courses_Activity.class);
        i.putExtra("date", date);
        startActivity(i);
    }

    private void clickEigene (){
        Intent i = new Intent(this,MainActivity.class);
        i.putExtra("open_eigene_fragment", true);
        i.putExtra("Button_Eigene", own_courses_button.getText());
        startActivity(i);
    }

    private void clickAlle(){
        Intent i = new Intent(this,Course_Overview_Path_Activity.class);
        i.putExtra("HtmlExtra", course_overview_html);
        i.putExtra("Button_VorVerzeichnis", course_overview_button.getText());
        startActivity(i);
    }


    @Override
    public void onPause(){
        super.onPause();
        Intent Service = new Intent(this, BackgroundService.class);
        startService(Service);
    }
}



