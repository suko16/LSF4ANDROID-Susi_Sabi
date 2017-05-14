//verglichen
//von Susi gebaut --> Baumstrukur erste Seite

// TODO: View view noch anpassen

package de.ur.mi.lsf4android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
    allefragmentInterface mCallback;
    TextView textView;
    ListView listView;


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
        View view = inflater.inflate(R.layout.fragment_alle, container, false);
        textView= (TextView) view.findViewById(R.id.header_Vorlesungsverzeichnis);
        listView = (ListView) view.findViewById(R.id.fragment_alle_listView);

        String[] url = new  String[1];
        url[0] = "https://lsf.uni-regensburg.de/qisserver/rds?state=wtree&search=1&trex=step&root120171=40852|40107|39734|37625|39743&P.vx=mittel";
        new DownloadHeadsTask().execute(url);

        // Inflate the layout for this fragment
        return view;
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

            TextView view = (TextView) getView().findViewById(R.id.header_Vorlesungsverzeichnis);
            view.setText(result.get(0)[0]);

            textView.setText(result.get(0)[0]);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, result.get(1));

            ListView listView = (ListView) getView().findViewById(R.id.fragment_alle_listView);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(),BaumActivity.class);
                    intent.putExtra("HtmlExtra",result.get(2)[i]);
                    startActivity(intent);
                }
            });
        }
    }




    //Fragment kommuniziert mit Activity über Interface


    public interface allefragmentInterface {
        public void onArticleSelected(int position);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (allefragmentInterface) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


}

