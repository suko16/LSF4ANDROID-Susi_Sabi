package de.ur.mi.lsf4android;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;



public class TermineFragment extends android.support.v4.app.Fragment {
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.single_view_time, container, false);
        listView = (ListView)view.findViewById(R.id.termine_listView);
        TextView header = (TextView) view.findViewById(R.id.termine_header);
        header.setText(getArguments().getString("titel"));

        new DownloadDetailsTask().execute(getArguments().getString("html"));
        return view;
    }

    private class DownloadDetailsTask extends AsyncTask<String, Integer, ArrayList<String[][]>> {
        protected ArrayList<String[][]> doInBackground(String... urls) {

            ArrayList<String[][]> result = new ArrayList<>();


            try {
                Document doc = Jsoup.connect(urls[0]).get();
                Elements tableGrunddaten = doc.select("table[summary='Übersicht über alle Veranstaltungstermine']");
                int group = tableGrunddaten.size();
                Elements tbody = tableGrunddaten.select("tbody");
                Elements tableRow = tbody.select("tr");
                Elements tableColums = tableRow.select("td");
                String[][] day = new String[group][tableRow.size()-1];
                String[][] time = new String[group][tableRow.size()-1];
                String[][] room = new String[group][tableRow.size()-1];
                String[][] teacher = new String[group][tableRow.size()-1];

                for (int j = 0; j < group; j++) {
                    for (int i = 0; i < tableRow.size()-1; i++) {
                        day[j][i] = tableColums.get(1).text();
                        time[j][i] = tableColums.get(2).text();
                        room[j][i] = tableColums.get(5).text();
                        teacher[j][i] = tableColums.get(7).text();
                    }
                }

                result.add(day);
                result.add(time);
                result.add(room);
                result.add(teacher);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return result;
            }
        }

        protected void onPostExecute(ArrayList<String[][]> result) {
            if (result.size() != 0) {
                final ArrayList<Course> courseArrayList = new ArrayList<Course>();
                for (int i = 0; i < result.get(0).length; i++) {
                    for (int s = 0; s < result.get(0)[0].length; s++){
                        courseArrayList.add(new Course(result.get(0)[i][s], result.get(1)[i][s], result.get(2)[i][s], result.get(3)[i][s],result.get(0).length));
                    }
                }
                Context context = getActivity();
                if (context != null) {
                ArrayAdapter adapter = new TermineArrayAdapter(context, courseArrayList);
                listView.setAdapter(adapter);
                }
            }
        }
    }
}
