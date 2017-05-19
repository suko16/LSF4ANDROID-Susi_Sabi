package de.ur.mi.lsf4android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


 /* Created by Susanne on 19.05.2017.
 */

public class GrunddatenFragment extends android.support.v4.app.Fragment{

        private ListView detailActivityListview;
        private DetailActivityArrayAdapter detailActivityArrayAdapter;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            View view = inflater.inflate(R.layout.fragment_detail, container, false);

            TextView header = (TextView) view.findViewById(R.id.detail_header);
            header.setText(getArguments().getString("titel"));
            header.setBackgroundColor(getResources().getColor(R.color.Biologie_VKLMedizin));

            detailActivityListview = (ListView) view.findViewById(R.id.detail_listView);

            new DownloadDetailsTask().execute(getArguments().getString("html"));

            return view;

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
                Context context = getActivity();
                if (context != null) {
                    detailActivityArrayAdapter = new DetailActivityArrayAdapter(context, result);
                    detailActivityListview.setAdapter(detailActivityArrayAdapter);
                }
            }
        }
    }






