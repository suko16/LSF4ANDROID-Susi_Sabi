package de.ur.mi.lsf4android;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class BaumLetzteActivity extends NavigationActivity {

    public EigeneVeranstaltungenDataSource dataSource;
    public ArrayList<Button> buttonList;
    ListView listView;
    private TextView vstNr;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConstraintLayout contentConstraintLayout = (ConstraintLayout) findViewById(R.id.fragment_content_navigation); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.fragment_baum_letzte, contentConstraintLayout);

        Intent intent = getIntent();

        new DownloadLSFTask().execute(intent.getStringExtra("html"));
        listView = (ListView) findViewById(R.id.baum_letzte_stufe_fragment_listView);
        vstNr = (TextView) findViewById(R.id.textView_activity_baum_letzte_vst_Nr);
        title = (TextView) findViewById(R.id.textView_activity_baum_letzte_titel);

        this.setTitle(intent.getStringExtra("header"));
    }

    private class DownloadLSFTask extends AsyncTask<String, Integer, ArrayList<String[]>> {
        protected ArrayList<String[]> doInBackground(String... urls) {
            ArrayList<String[]> result = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(urls[0]).get();
                Elements table = doc.select("table[summary=Übersicht über alle Veranstaltungen]");
                Elements rows = table.select("tr");
                Elements header = rows.select("th");

                String[] headLine = new String[header.size()];
                String[] numbers = new String[rows.size() - 1];
                String[] titles = new String[rows.size() - 1];
                String[] html = new String[rows.size() - 1];


                for (int s = 0; s < rows.size(); s++) {
                    if (s == 0) {
                        headLine[0] = header.get(0).text();
                        headLine[1] = header.get(1).text();
                    } else {
                        numbers[s - 1] = rows.get(s).select("td").get(0).text();
                        titles[s - 1] = rows.get(s).select("td").get(1).select("a.regular").text();
                        html[s - 1] = rows.get(s).select("td").get(1).select("a").attr("href");
                    }
                }

                result.add(headLine);
                result.add(numbers);
                result.add(titles);
                result.add(html);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return result;
            }
        }


        protected void onPostExecute(ArrayList<String[]> result) {

            vstNr.setText(result.get(0)[0]);
            title.setText(result.get(0)[1]);

            BaumLetzteArrayAdapter adapter = new BaumLetzteArrayAdapter(BaumLetzteActivity.this, result.get(1), result.get(2), result.get(3));
            listView.setAdapter(adapter);

        }

    }
}





