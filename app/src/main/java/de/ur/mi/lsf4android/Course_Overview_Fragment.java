//verglichen
//von Susi gebaut --> Baumstrukur erste Seite

// TODO: View view noch anpassen

//TODO: kann man eigentlich löschen, wird nicht mehr verwendet. Ist jetzt Baum Activity.


package de.ur.mi.lsf4android;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Course_Overview_Fragment extends android.support.v4.app.Fragment {

   // allefragmentInterface mCallback;
    TextView textView;
    ListView listView;
    ArrayAdapter<String> adapter;


    public Course_Overview_Fragment() {
        // Required empty public constructor
    }

    public static Course_Overview_Fragment newInstance() {
        Course_Overview_Fragment fragment = new Course_Overview_Fragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.couse_overview_fragment, container, false);
        textView= (TextView) view.findViewById(R.id.Header_Course_Overview);
        listView = (ListView) view.findViewById(R.id.ListView_Course_Overview);

        String[] url = new  String[1];
        url[0] = "https://lsf.uni-regensburg.de/qisserver/rds?state=wtree&search=1&trex=step&root120171=40852&P.vx=mittel";
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

            textView.setText(result.get(0)[0]);

            final Context context = getActivity();

            if (context != null) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_list_item_1, result.get(1));

                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(context, Course_Overview_Path_Activity.class);
                        intent.putExtra("HtmlExtra", result.get(2)[i]);
                        startActivity(intent);
                    }
                });
            }
        }
    }




    //Fragment kommuniziert mit Activity über Interface


    /*public interface allefragmentInterface {
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
    }*/


}

