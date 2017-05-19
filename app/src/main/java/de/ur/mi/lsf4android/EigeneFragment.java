//verglichen
//hat Sabi gebaut


package de.ur.mi.lsf4android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import android.widget.TextView;
import android.widget.Toast;


public class EigeneFragment extends android.support.v4.app.Fragment implements ListView.OnItemClickListener, ListView.OnItemLongClickListener {


    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private EigeneVeranstaltungenDataSource dataSource;
    public ListView VeranstaltngslisteListView;
    private TextView number;
    private TextView title;
    private ArrayList ArrayListAusfallendeVeranstaltungen;
    private ArrayList<String> arrayListAusfallende_Number;



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

            //Abgleich mit Downloadergebnis aus BackgroundService
            //Problem: result = null
          /* BackgroundService bS = new BackgroundService();
            if(bS.result != null) {
                arrayListAusfallende_Number = new ArrayList<>();
                for (int i = 1; i < bS.result.size(); i++) {
                    arrayListAusfallende_Number.add(bS.result.get(i));
                }
            }
            if(ArrayListAusfallendeVeranstaltungen!= null && VeranstaltungslisteDB != null){
                for(int i=0; i<VeranstaltungslisteDB.size(); i++){
                    for(int j = 0; j< arrayListAusfallende_Number.size(); j++){
                        if(VeranstaltungslisteDB.get(i).getNumber().equals(arrayListAusfallende_Number.get(j))){
                            VeranstaltngslisteListView.getChildAt(i).setBackgroundColor(Color.GREEN);
                        }
                    }
                }
            }*/

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

        Intent intent = new Intent(getActivity(), DetailActivityWithFragments.class);
        intent.putExtra("titel", titelSelectedVeranstaltung);
        intent.putExtra("html", selectedVeranstaltung.getHtml());
        intent.putExtra("open_detail_start",true);
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