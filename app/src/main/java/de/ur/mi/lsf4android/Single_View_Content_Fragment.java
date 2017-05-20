package de.ur.mi.lsf4android;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Susanne on 19.05.2017.
 */

public class Single_View_Content_Fragment extends android.support.v4.app.Fragment {
    ListView inhalt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.single_view_fragment, container, false);
        TextView header = (TextView) view.findViewById(R.id.detail_header);
        inhalt = (ListView) view.findViewById(R.id.detail_listView);
        header.setText(getArguments().getString("titel"));

        new DownloadDetailsTask().execute(getArguments().getString("html"));
        return view;
    }

    private class DownloadDetailsTask extends AsyncTask<String, Integer, String[][]> {
        protected String[][] doInBackground(String... urls) {
            String[][] result = null;
            try {
                Document doc = Jsoup.connect(urls[0]).get();
                Elements tableEinrichtung = doc.select("table[summary='Weitere Angaben zur Veranstaltung']");
                Elements tbody = tableEinrichtung.select("tbody");
                Elements tableRow = tbody.select("tr");
                Elements th = tbody.select("th");
                Elements td = tableRow.select("td");

                result = new String[tableRow.size()][2];

                for (int i = 0; i < result.length; i++) {
                    result[i][0] = th.get(i).text();
                    result[i][1] = td.get(i).text();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                return result;

            }
        }

        protected void onPostExecute(String[][] result) {
            Context context = getActivity();
            if (context != null) {
                Single_View_Basic_Data_ArrayAdapter arrayAdapter = new Single_View_Basic_Data_ArrayAdapter(context, R.layout.single_view_content_row, result);
                inhalt.setAdapter(arrayAdapter);
            }
        }

    }

}
