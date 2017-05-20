package de.ur.mi.lsf4android;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class Single_View_Activity extends NavigationActivity {
    private static final String TITEL_EXTRA = "titel_extra";
    private static final String HTML_EXTRA = "html_extra";
    private ListView detailActivityListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstraintLayout contentConstraintLayout = (ConstraintLayout) findViewById(R.id.fragment_content_navigation); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.single_view_activity, contentConstraintLayout);
        Intent intent = getIntent();
        String titel = intent.getStringExtra(TITEL_EXTRA);
        TextView tvTitel = (TextView) findViewById(R.id.singleView_title);
        tvTitel.setText(titel);
        detailActivityListview = (ListView) findViewById(R.id.singleView_listView);
        new DownloadDetailsTask().execute(intent.getStringExtra(HTML_EXTRA));
    }

    private class DownloadDetailsTask extends AsyncTask<String, Integer, String[][]> {
        protected String[][] doInBackground(String... urls) {
            String[][] result = new String[14][2];
            try {
                Document doc = Jsoup.connect(urls[0]).get();
                Elements tableGrunddaten = doc.select("table[summary='Grunddaten zur Veranstaltung']");
                Elements rowsGrunddaten = tableGrunddaten.select("tr");
                Elements tableData = rowsGrunddaten.select("td[headers]");
                Elements tableHeader = rowsGrunddaten.select("th");
                for (int i = 0; i < result.length; i++) {
                    Element head = tableHeader.get(i);
                    Element text = tableData.get(i);
                    result[i][0] = head.text();
                    result[i][1] = text.text();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return result;
            }
        }

        protected void onPostExecute(String[][] result) {
            Single_View_Basic_Data_ArrayAdapter single_View_ArrayAdapter = new Single_View_Basic_Data_ArrayAdapter(Single_View_Activity.this, R.layout.single_view_basic_data_row, result);
            detailActivityListview.setAdapter(single_View_ArrayAdapter);
        }
    }
}




