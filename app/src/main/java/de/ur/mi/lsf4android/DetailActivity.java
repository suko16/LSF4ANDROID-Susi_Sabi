//verglichen
//hat Susi gebaut
//mit ArrayAdapter und Gridview umabuen


package de.ur.mi.lsf4android;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static de.ur.mi.lsf4android.R.id.fragment_ausfallende_tabelle;

public class DetailActivity extends AppCompatActivity {
    public static final String TITEL_EXTRA = "titel_extra";
    public static final String HTML_EXTRA = "html_extra";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String titel = intent.getStringExtra(TITEL_EXTRA);

        TextView tvTitel = (TextView) findViewById(R.id.veranstaltung_titel);
        tvTitel.setText(titel);

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

                for (int i = 0; i<result.length; i++) {
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

            TableLayout table = (TableLayout) findViewById(R.id.detail_tabelle);
            TableRow row;
            TextView head;
            TextView data;

            for (int j = 0; j < result.length; j++) {
                row = (TableRow) table.getChildAt(j);
                head = (TextView) row.getChildAt(0);
                data = (TextView) row.getChildAt(1);
                head.setText(result[j][0]);
                data.setText(result[j][1]);
            }
        }
    }
}




