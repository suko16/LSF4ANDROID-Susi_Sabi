package de.ur.mi.lsf4android;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class BaumActivity extends AppCompatActivity {

    private Intent extra;
    private ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_alle);

        extra = getIntent();

        new DownloadHeadsTask().execute(extra.getStringExtra("HtmlExtra"));

    }

    private class DownloadHeadsTask extends AsyncTask<String, Integer, ArrayList<String[]>> {
        protected ArrayList<String[]> doInBackground(String... urls) {

            ArrayList<String[]> arrayList = new ArrayList<>();

            try {
                Document doc = Jsoup.connect(urls[0]).get();
                Elements header = doc.select("h1");

                Elements table_header = doc.select("a.ueb");

                String[] zweigName = new String[table_header.size()];
                String[] zweigUrl = new String[table_header.size()];
                String[] headerString = new String[1];

                headerString[0] = header.text();

                for (int i = 0; i < zweigName.length; i++) {
                    zweigName[i] = table_header.get(i).text();
                    zweigUrl[i] = table_header.get(i).attr("href");
                }

                arrayList.add(headerString);
                arrayList.add(zweigName);
                arrayList.add(zweigUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return arrayList;
        }

        protected void onPostExecute(final ArrayList<String[]> result) {
            writeHeader(result.get(0)[0]);
            buildList(result.get(1), result.get(2));



           /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(,
                    android.R.layout.simple_list_item_1, result.get(1));
            ListView listView = (ListView) findViewById(R.id.fragment_alle_listView);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(this,BaumActivity.class);
                    intent.putExtra("HtmlExtra",result.get(2)[i]);
                    startActivity(intent);
                }
            });*/
        }
    }

    private void writeHeader(String totalHeader){
        TextView view = (TextView) findViewById(R.id.header_Vorlesungsverzeichnis);
        view.setText(totalHeader);
    }

    private void buildList(String[] headers, final String[] headerHtmls ) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, headers);
        listView = (ListView) findViewById(R.id.fragment_alle_listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                handleClickOnListItem(headerHtmls,i);
            }
        });
    }

    private void handleClickOnListItem(String[] headers, int j){
        Intent intent = new Intent(this,BaumActivity.class);
        intent.putExtra("HtmlExtra",headers[j]);
        this.finish();
        startActivity(intent);
    }
}
