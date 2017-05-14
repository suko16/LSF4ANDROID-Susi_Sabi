package de.ur.mi.lsf4android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class BaumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baum);

        TextView htmlView = (TextView) findViewById(R.id.htmlView);
        Intent extra = getIntent();
        htmlView.setText(extra.getStringExtra("HtmlExtra"));
    }
}
