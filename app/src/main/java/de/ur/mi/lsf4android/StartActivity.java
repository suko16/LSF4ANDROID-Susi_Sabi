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

    private Button ausfallendeVButton;
    private Button eigeneVButton;
    private Button vorVerzeichnisButton;
    private Button buttonLast;
    private String vorlesungsverzeichnis_html = "https://lsf.uni-regensburg.de/qisserver/rds?state=wtree&search=1&trex=step&root120171=40852&P.vx=mittel";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ausfallendeVButton = (Button)findViewById(R.id.ausfallende_v_button);
        eigeneVButton = (Button)findViewById(R.id.eigene_v_button);
        vorVerzeichnisButton = (Button)findViewById(R.id.alle_v_button);

        ausfallendeVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAusfallend();
            }
        });

        eigeneVButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEigene();
            }
        });

        vorVerzeichnisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAlle();
            }
        });

        buttonLast = (Button) findViewById(R.id.button_last);

        buttonLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBaumLetzte();
            }
        });


        Intent Service = new Intent(this, BackgroundService.class);
        startService(Service);
    }

    private void clickAusfallend (){
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat datumsformat = new SimpleDateFormat("dd.MM.yyyy");
        String date = datumsformat.format(calendar.getTime());

        Intent i = new Intent(this,AusfallendeActivity.class);
        i.putExtra("date", date);
        startActivity(i);
    }

    private void clickEigene (){
        Intent i = new Intent(this,MainActivity.class);
        i.putExtra("open_eigene_fragment", true);
        i.putExtra("Button_Eigene", eigeneVButton.getText());
        startActivity(i);
    }

    private void clickAlle(){
        Intent i = new Intent(this,BaumActivity.class);
        i.putExtra("HtmlExtra", vorlesungsverzeichnis_html);
        i.putExtra("Button_VorVerzeichnis", vorVerzeichnisButton.getText());
        startActivity(i);
    }

    private void clickBaumLetzte () {
        Intent intent = new Intent(this,BaumLetzteActivity.class);
        intent.putExtra("html", "https://lsf.uni-regensburg.de/qisserver/rds?state=wtree&search=1&trex=step&root120171=40852|40107|39734|37625|39743|37688&P.vx=mittel");
        intent.putExtra("header", "Beispiel");
        startActivity(intent);
    }


}



