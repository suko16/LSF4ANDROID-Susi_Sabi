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
import org.jsoup.nodes.Element;
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
                Elements tables = doc.select("table");
                Elements vorlesungenTabelle = doc.select("table[summary='Übersicht über alle Veranstaltungen'");
                Elements table_header = doc.select("a.ueb");

                String[] zweigName_1 = new String[table_header.size()];
                String[] zweigUrl_2 = new String[table_header.size()];
                String[] headerString_0 = new String[1];
                String[] modul_3 = new String[2];

                headerString_0[0] = header.text();

                for (int i = 0; i < zweigName_1.length; i++) {
                    zweigName_1[i] = table_header.get(i).text();
                    zweigUrl_2[i] = table_header.get(i).attr("href");
                }


                if (tables.last() == vorlesungenTabelle.last() && vorlesungenTabelle.size() != 0){
                    modul_3[0] = table_header.last().text();
                    modul_3[1] = table_header.last().attr("href");
                }

                arrayList.add(headerString_0);
                arrayList.add(zweigName_1);
                arrayList.add(zweigUrl_2);
                arrayList.add(modul_3);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return arrayList;
        }

        protected void onPostExecute(final ArrayList<String[]> result) {
            writeHeader(result.get(0)[0]);
            buildList(result.get(1), result.get(2), result.get(3));



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

    private void buildList(String[] headers, final String[] headerHtmls, final String[] modulAuflistung ) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, headers);
        listView = (ListView) findViewById(R.id.fragment_alle_listView);
        listView.setAdapter(adapter);
        listView.setItemsCanFocus(true);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                handleClickOnListItem(headerHtmls,i, modulAuflistung);


                /*if (modulAuflistung[1] == null){
                    handleClickOnListItem(headerHtmls,i);
                }else{
                    Intent openDetailActivity = new Intent(BaumActivity.this, BaumLetzteActivity.class);
                    openDetailActivity.putExtra("header", modulAuflistung[0]);
                    openDetailActivity.putExtra("html", modulAuflistung[1]);
                    startActivity(openDetailActivity);
                }*/

            }
        });
    }

    private void handleClickOnListItem(String[] headers, int j, String[] modulAuflistung){

        if (modulAuflistung[1] == null){
           // ((TextView)view.findViewById(R.id.yourTextViewId)).getText();
            Intent intent = new Intent(this,BaumActivity.class);
            intent.putExtra("HtmlExtra", headers[j]);
            this.finish();
            startActivity(intent);

        }else{
            Intent openDetailActivity = new Intent(BaumActivity.this, BaumLetzteActivity.class);
            openDetailActivity.putExtra("header", modulAuflistung[0]);
            openDetailActivity.putExtra("html", modulAuflistung[1]);
            startActivity(openDetailActivity);
        }

    }

    //TODO: Man muss zweimal auf lettes Item klicken, um BaumLetzteActivity zu öffnen. ALso um die Veranstaltungen des Moduls anzeigen zu lassen.
    //TODO: Wenn man von BaumLetzte wieder zurück geht, kann man andere Zeilen nicht mehr drücken.
}
