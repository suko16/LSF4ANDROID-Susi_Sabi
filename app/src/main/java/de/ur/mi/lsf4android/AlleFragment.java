package de.ur.mi.lsf4android;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

public class AlleFragment extends android.support.v4.app.Fragment {

    TableLayout alleVorlesungenTabelle;
    String url;


    public AlleFragment() {
        // Required empty public constructor
    }

    public static AlleFragment newInstance() {
        AlleFragment fragment = new AlleFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        String[] url = new  String[1];
        url[0] = "https://lsf.uni-regensburg.de/qisserver/rds?state=wtree&search=1&trex=step&root120171=40852|40107|39734|37625|39743&P.vx=mittel";
        new DownloadHeadsTask().execute(url);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_alle, container, false);
    }

    private class DownloadHeadsTask extends AsyncTask<String, Integer, ArrayList<String[]>> {
        protected ArrayList<String[]> doInBackground(String... urls) {

            ArrayList<String[]> arrayList = new ArrayList<>();

            try {
                Document doc = Jsoup.connect(urls[0]).get();
                Elements header = doc.select("h1");

                Elements table_header = doc.select("a.ueb");

                String[] zweigName = new String[table_header.size()+1];
                String[] zweigUrl = new String[table_header.size()+1];

                zweigName[0]= header.text();
                zweigUrl[0]= null;

                for (int i = 1; i < zweigName.length; i++) {
                    zweigName[i] = table_header.get(i-1).text();
                    zweigUrl[i] = table_header.get(i-1).attr("href");
                }

                arrayList.add(zweigName);
                arrayList.add(zweigUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return arrayList;
        }

        protected void onPostExecute(ArrayList<String[]> result) {
            TextView view = (TextView) getView().findViewById(R.id.header_Vorlesungsverzeichnis);
            view.setText(result.get(0)[0]);

            TextView tableHeaderView = (TextView) getView().findViewById(R.id.vl2);
            tableHeaderView.setText(result.get(1)[4]);

            alleVorlesungenTabelle = (TableLayout) getView().findViewById(R.id.fragment_alle_tabelle);
            for (int s = 1; s < result.get(0).length; s++) {
                alleVorlesungenTabelle.addView(addRow(result.get(0)[s], s), new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT
                        )
                );
            }
        }

        private TableRow addRow (String zweigName, int idCount){
            TableRow zweig = new TableRow(getActivity());
            zweig.setId(idCount);
            zweig.setLayoutParams(
                    new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT
                    )
            );

            TextView zweigInhalt = new TextView(getActivity());
            zweigInhalt.setId(idCount);
            zweigInhalt.setText(zweigName);

           zweigInhalt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   // url = "https://lsf.uni-regensburg.de/qisserver/rds?state=wtree&search=1&trex=step&root120171=40852|40107|39734|37625|39743&P.vx=mittel";
                    //DownloadHeadsTask.execute(url);
                    alleVorlesungenTabelle.setBackgroundColor(Color.GREEN);
                }
            });

            zweig.addView(zweigInhalt);

            return zweig;
        }
    }
    

}

