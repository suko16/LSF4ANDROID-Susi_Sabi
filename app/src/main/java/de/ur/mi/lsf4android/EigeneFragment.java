//verglichen
//hat Sabi gebaut


package de.ur.mi.lsf4android;

import android.content.Context;
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
import android.widget.Toast;

//TODO: IDS ausblenden

//TODO: schöne Ansicht


public class EigeneFragment extends android.support.v4.app.Fragment implements ListView.OnItemLongClickListener {


    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private EigeneVeranstaltungenDataSource dataSource;
    public ListView VeranstaltngslisteListView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_eigene, container, false);
        VeranstaltngslisteListView = (ListView) view.findViewById(R.id.eigene_veranstaltungsliste);
        dataSource = new EigeneVeranstaltungenDataSource(getActivity());
        VeranstaltngslisteListView.setOnItemLongClickListener(this);
        return view;
    }


    public void showAllListEntries() {

        Context context = getActivity();
        if (context != null) {
            ArrayList<EigeneV_Objekt> VeranstaltungslisteDB = dataSource.getAllVeranstaltungen();
            EigeneFragmentArrayAdapter adapter2 = new EigeneFragmentArrayAdapter(context, VeranstaltungslisteDB);
            VeranstaltngslisteListView.setAdapter(adapter2);
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
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        int positionListView = (int)id;

        EigeneV_Objekt selectedVeranstaltung = (EigeneV_Objekt) VeranstaltngslisteListView.getItemAtPosition(positionListView);
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
