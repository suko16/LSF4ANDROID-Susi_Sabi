//verglichen
//von Susi gebaut, von Sabi angepasst
//muss noch mit ArrayAdapter und ListView umgebaut werden


package de.ur.mi.lsf4android;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AusfallendeFragment extends android.support.v4.app.Fragment {

  /*  public Veranstaltung veranstaltung;
    private TextView title;
    private TextView begin;
    private TextView ende;
    private TextView number;
    public ListView list;
    public AusfallendeActivityArrayAdapter adapter;
    public TableLayout table;
    public TableRow row;
    private int rowCount = 0;
    private ArrayList<String> htmlList;
    private EigeneVeranstaltungenDataSource dataSource;
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public ArrayList<String[]> result;
    //EditText dateEditText;
    private DatePickerDialog datePickerDialog;
    private Button datePickerButton;
    private String date;



    public AusfallendeFragment() {
        // Required empty public constructor
    }

    public static AusfallendeFragment newInstance() {
        AusfallendeFragment fragment = new AusfallendeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ausfallende, container, false);
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat datumsformat = new SimpleDateFormat("dd.MM.yyyy");
        date = datumsformat.format(calendar.getTime());


        String url = "https://lsf.uni-regensburg.de/qisserver/rds?state=currentLectures&type=1&next=CurrentLectures.vm&nextdir=ressourcenManager&navigationPosition=lectures%2CcanceledLectures&breadcrumb=canceledLectures&topitem=lectures&subitem=canceledLectures&&HISCalendar_Date=24.05.2017&&HISCalendar_Date=23.05.2017&&HISCalendar_Date=13.06.2017&&HISCalendar_Date=" + date +"&&HISCalendar_Date=" + date + "&asi=";
        new DownloadLSFTask().execute(url);

        list = (ListView) view.findViewById(R.id.list);

        //dateEditText = (EditText) view.findViewById(R.id.date);
        datePickerButton = (Button) view.findViewById(R.id.button_date_picker);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                datePickerButton.setText(dayOfMonth + "."
                                        + (monthOfYear + 1) + "." + year);
                                date = dayOfMonth + "."
                                        + (monthOfYear + 1) + "." + year;
                                reloadFragment();
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        return view;
    }

    private void reloadFragment(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }



    private class DownloadLSFTask extends AsyncTask<String, Integer, ArrayList<String[]>> {
        protected ArrayList<String[]> doInBackground(String... urls) {
            result = new ArrayList<>();
                try {
                    Document doc = Jsoup.connect(urls[0]).get();
                Element table = doc.select("table").last();
                Elements rows = table.select("tr");
                htmlList = new ArrayList<>();
                for (Element row : rows) {
                    String[] string_row = new String[4];
                    Elements columns = row.select("td");
                    int i = 0;
                    rowCount++;
                    for (Element column : columns) {
                        switch (i) {
                            case 0:
                                string_row[0] = column.text();
                                break;
                            case 1:
                                string_row[1] = column.text();
                                break;
                            case 2:
                                string_row[2] = column.text();
                                break;
                            case 3:
                                string_row[3] = column.select("a").text();

                                //get URL from titel
                                Element link = column.select("a").first();
                                htmlList.add(link.attr("href"));
                                break;
                        }
                        i++;
                    }
                    result.add(string_row);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return result;
            }
        }

        private void callDetailActivity(String titel, int j) {
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra(DetailActivity.TITEL_EXTRA, titel);
            intent.putExtra(DetailActivity.HTML_EXTRA, htmlList.get(j));
            startActivity(intent);
        }

        protected void onPostExecute(ArrayList<String[]> result) {
            // Tabelle in fragment_ausfallende.xml bauen und mit result befüllen
            //String titel = result.get(1)[2];
            //callDetailActivity(titel);
            if(result.size() != 0) {
                final ArrayList<Veranstaltung> veranstaltungen = new ArrayList<Veranstaltung>();
                veranstaltungen.add(new Veranstaltung("Beginn", "Ende", "Nummer", "Titel"));
                for (int i = 1; i < result.size(); i++) {
                    veranstaltungen.add(new Veranstaltung(result.get(i)[0], result.get(i)[1], result.get(i)[2], result.get(i)[3]));
                }
                Context context = getActivity();
                if (context != null) {
                    adapter = new AusfallendeActivityArrayAdapter(context, veranstaltungen);
                    list.setAdapter(adapter);

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if(i!=0){
                            callDetailActivity(veranstaltungen.get(i).getTitel(),i-1);
                            }
                        }
                    });



                    //Überprüft auf Übereinstimmungen zwischen Datenbank und Ausfallenden

                    dataSource = new EigeneVeranstaltungenDataSource(getActivity());
                    dataSource.open();
                    List<EigeneV_Objekt> VeranstaltungslisteDB = dataSource.getAllVeranstaltungen();

                    for (int j = 0; j < VeranstaltungslisteDB.size(); j++){
                        for (int i=0; i<veranstaltungen.size(); i++){
                            if(VeranstaltungslisteDB.get(j).getNumber().equals(veranstaltungen.get(i).getNumber())){
                               veranstaltungen.get(i).setTitel(veranstaltungen.get(i).getTitel().toUpperCase());

                        }
                        }
                    }


                }
            }
        }
/*

            //Fügt in der Tabelle die Zeilen mit den entsprechenden Werten hinzu
        private void addRow(String begin_text, String end_text, String number_text, String title_text, int k) {

        //int Wert braucht man, um über Clicklistener richtige URL zu öffnen.

        int counter = 0;
        counter++;

        row = new TableRow(getActivity());

        row.setId(counter);
        row.setLayoutParams(
                new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                )
        );

        begin = new TextView(getActivity());
        begin.setId(counter);
        begin.setText(begin_text);
        begin.setLayoutParams(
                new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                )
        );

        row.addView(begin);

        ende = new TextView(getActivity());
        ende.setId(counter + 200);
        ende.setText(end_text);
        ende.setLayoutParams(
                new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT

                )
        );

        row.addView(ende);


            number = new TextView(getActivity());
            number.setId(counter+300);
            number.setText(number_text);
            number.setLayoutParams(
                    new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT
                    )
            );

            row.addView(number);


        title = new TextView(getActivity());
        title.setId(counter + 100);
        title.setText(title_text);
        title.setLayoutParams(
                new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                )
        );

        final String header = title_text;
        final int rowCountHtml = k - 1; //richtige Stelle von ArrayList htmlList in CallDetailActivity ansprechen

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callDetailActivity(header, rowCountHtml);
            }
        });




        row.addView(title);

        table.addView(row, new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT
                )
        );
    }


    }*/

}
