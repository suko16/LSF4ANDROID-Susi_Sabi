//verglichen
//hat Sabi gebaut


package de.ur.mi.lsf4android;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;
import android.widget.Toast;

//TODO: IDS ausblenden


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


        List<EigeneV_Objekt> Veranstaltungsliste = dataSource.getAllVeranstaltungen();

        ArrayAdapter<EigeneV_Objekt> veranstaltungArrayAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                Veranstaltungsliste
        );




        VeranstaltngslisteListView.setAdapter(veranstaltungArrayAdapter);

        // veranstaltungArrayAdapter.notifyDataSetChanged();

    }


    @Override
    public void onResume() {
        super.onResume();

        Log.d(LOG_TAG, "Die Datenquelle wird geöffnet.");
        dataSource.open();

        Log.d(LOG_TAG, "Folgende Einträge sind in der Datenbank vorhanden:");
        showAllListEntries();
    }


    @Override
    public void onPause() {
        super.onPause();

        Log.d(LOG_TAG, "Die Datenquelle wird geschlossen.");
        dataSource.close();
    }





    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        int positionListView = (int)id;

        Log.d(LOG_TAG, "Position " + positionListView);

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
    /*


    private void initializeContextualActionBar() {
        final ListView VeranstaltngslisteListView = (ListView) getView().findViewById(R.id.veranstaltungsliste);
        VeranstaltngslisteListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        VeranstaltngslisteListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                getActivity().getMenuInflater().inflate(R.menu.menu_contextual_action_bar, menu); //getActivity?
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.cab_delete:
                        SparseBooleanArray touchedVeranstaltungPositions = VeranstaltngslisteListView.getCheckedItemPositions();
                        for (int i = 0; i < touchedVeranstaltungPositions.size(); i++) {
                            boolean isChecked = touchedVeranstaltungPositions.valueAt(i);
                            if (isChecked) {
                                int postitionInListView = touchedVeranstaltungPositions.keyAt(i);
                                EigeneV_Objekt veranstaltung = (EigeneV_Objekt) VeranstaltngslisteListView.getItemAtPosition(postitionInListView);
                                Log.d(LOG_TAG, "Position im ListView: " + postitionInListView + " Inhalt: " + veranstaltung.toString());
                                dataSource.deleteVeranstaltung(veranstaltung);
                            }
                        }
                        showAllListEntries();
                        mode.finish();
                        return true;

                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });


    }

//ende db

*/



}
