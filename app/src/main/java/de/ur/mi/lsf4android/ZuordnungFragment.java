package de.ur.mi.lsf4android;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Susanne on 19.05.2017.
 */

public class ZuordnungFragment extends android.support.v4.app.Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        TextView header = (TextView) view.findViewById(R.id.detail_header);
        header.setText("ZuordnungFragment");

        return view;
    }

    private class DownloadDetailsTask extends AsyncTask<String, Integer, String[][]> {
        protected String[][] doInBackground(String... urls) {


            return null;
        }

        protected void onPostExecute(String[][] result) {
            Context context = getActivity();
        }

    }

}
