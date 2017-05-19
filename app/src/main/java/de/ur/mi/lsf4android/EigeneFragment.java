//verglichen
//hat Sabi gebaut


package de.ur.mi.lsf4android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class EigeneFragment extends android.support.v4.app.Fragment implements ListView.OnItemClickListener, ListView.OnItemLongClickListener {


    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private EigeneVeranstaltungenDataSource dataSource;
    public ListView VeranstaltngslisteListView;
    private TextView number;
    private TextView title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_eigene, container, false);
        number = (TextView) view.findViewById(R.id.textView_eigeneFragment_number);
        title = (TextView) view.findViewById(R.id.textView_eigeneFragment_title);
        VeranstaltngslisteListView = (ListView) view.findViewById(R.id.eigene_veranstaltungsliste);
        dataSource = new EigeneVeranstaltungenDataSource(getActivity());
        VeranstaltngslisteListView.setOnItemClickListener(this);
        VeranstaltngslisteListView.setOnItemLongClickListener(this);
        return view;
    }


    public void showAllListEntries() {

        number.setText("Nr.");
        title.setText("Titel");
        Context context = getActivity();
        if (context != null) {
            ArrayList<Veranstaltung> VeranstaltungslisteDB = dataSource.getAllVeranstaltungen();
            EigeneFragmentArrayAdapter adapter = new EigeneFragmentArrayAdapter(context, VeranstaltungslisteDB);
            VeranstaltngslisteListView.setAdapter(adapter);

            //TODO: für den ClickListener mit Aufruf der Detailactivity kann ich ich keine HTML mitübergeben, weil wir ja hier keinen DownloadTask haben.. ne idee wie man das machen könnte?


        }
    }


    @Override
    public void onResume() {
        super.onResume();
        dataSource.open();
        showAllListEntries();
    }


    @Override
    public void onPause() {
        super.onPause();
        dataSource.close();
    }




    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        int positionListView = (int)id;

        Veranstaltung selectedVeranstaltung = (Veranstaltung) VeranstaltngslisteListView.getItemAtPosition(positionListView);
        String titelSelectedVeranstaltung = selectedVeranstaltung.getTitel();

        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.TITEL_EXTRA, titelSelectedVeranstaltung);
        intent.putExtra(DetailActivity.HTML_EXTRA, selectedVeranstaltung.getHtml());
        startActivity(intent);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        int positionListView = (int)id;

        Veranstaltung selectedVeranstaltung = (Veranstaltung) VeranstaltngslisteListView.getItemAtPosition(positionListView);
        dataSource.deleteVeranstaltung(selectedVeranstaltung);

        CharSequence text = selectedVeranstaltung.getTitel() + " wurde gelöscht";

        Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_LONG);
        toast.show();

        showAllListEntries();
        return true;

    };


    public EigeneFragment() {
        // Required empty public constructor
    }

    public static EigeneFragment newInstance() {
        EigeneFragment fragment = new EigeneFragment();
        return fragment;
    }
}