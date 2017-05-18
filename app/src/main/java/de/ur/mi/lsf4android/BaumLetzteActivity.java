package de.ur.mi.lsf4android;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class BaumLetzteActivity extends AppCompatActivity {

    public EigeneVeranstaltungenDataSource dataSource;
    public ArrayList<Button> buttonList;
    ListView listView;
    private TextView vstNr;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_baum_letzte);

        Intent intent = getIntent();

        new DownloadLSFTask().execute(intent.getStringExtra("html"));
        listView = (ListView) findViewById(R.id.baum_letzte_stufe_fragment_listView);
        vstNr = (TextView) findViewById(R.id.textView_activity_baum_letzte_vst_Nr);
        title = (TextView) findViewById(R.id.textView_activity_baum_letzte_titel);

       this.setTitle(intent.getStringExtra("header"));
    }

    private class DownloadLSFTask extends AsyncTask<String, Integer, ArrayList<String[]>> {
        protected ArrayList<String[]> doInBackground(String... urls) {
            ArrayList<String[]> result = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(urls[0]).get();
                Elements table = doc.select("table[summary=Übersicht über alle Veranstaltungen]");
                Elements rows = table.select("tr");
                Elements header = rows.select("th");

                String[] headLine = new String[header.size()];
                String[] numbers = new String[rows.size()-1];
                String[] titles = new String[rows.size()-1];
                String[] html = new String[rows.size()-1];


                for (int s = 0; s < rows.size(); s++) {
                    if (s == 0) {
                        headLine[0] = header.get(0).text();
                        headLine[1] = header.get(1).text();
                    } else {
                        numbers[s-1] = rows.get(s).select("td").get(0).text();
                        titles[s-1] = rows.get(s).select("td").get(1).select("a.regular").text();
                        html[s-1] = rows.get(s).select("td").get(1).select("a").attr("href");
                    }
                }

                result.add(headLine);
                result.add(numbers);
                result.add(titles);
                result.add(html);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return result;
            }
        }







            /*    for (int k = 0; k<3; k++) {
                    //String[] numbers = new String[3];
                    //String[] titles = new String [rows.size()];
                    Elements columns = rows.select("td");
                    int i = 0;
                    rowCountForReading++;
                    for (int j = 0; j<rows.size();j++) {
                        switch (i) {
                            case 0:
                                numbers[j] = columns.text();
                                break;
                            case 1:
                                titles[j] = columns.text();
                                break;
                            case 3:
                                html[j] = columns.select("a").text();
                                break;
                        }
                        i++;
                    }
                    result.add(numbers);
                    result.add(titles);
                    result.add(html);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return result;
            }
        }*/

        protected void onPostExecute(ArrayList<String[]> result) {

           vstNr.setText(result.get(0)[0]);
           title.setText(result.get(0)[1]);

            BaumLetzteArrayAdapter adapter = new BaumLetzteArrayAdapter(BaumLetzteActivity.this, result.get(1), result.get(2), result.get(3));
            listView.setAdapter(adapter);




         /*   addRow("Number", "Titel");
            for (int i = 1; i < rowCountForReading; i++) {
                addRow(result.get(i)[0], result.get(i)[1]);

            }
        }

        private void addRow (final String number_text, final String title_text){

            row = new TableRow(getActivity());
            row.setId(rowCountForWriting);
            row.setLayoutParams(
                    new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT
                    )
            );

            number = new TextView(getActivity());
            number.setId(rowCountForWriting+10);
            number.setText(number_text);
            number.setLayoutParams(
                    new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT
                    )
            );

            row.addView(number);

            title = new TextView(getActivity());
            title.setId(rowCountForWriting+20);
            title.setText(title_text);
            title.setLayoutParams(
                    new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT
                    )
            );

            row.addView(title);


            final String ueberschrift = title_text;
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callDetailActivity(ueberschrift);
                }
            });

            //Buttonliste erstellen - Erste Reihe mit Überschriften ohne Button
            if(rowCountForWriting >0) {
                buttonList.add(new Button(getActivity()));
                row.addView(buttonList.get(rowCountForWriting-1));

                final String nummer = number_text;

                buttonList.get(rowCountForWriting-1).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Bei Klick überprüfen ob Veranstaltung schon gespeichert, wenn nicht speichern in eigene
                        dataSource.open();
                        List<EigeneV_Objekt> Veranstaltungsliste = dataSource.getAllVeranstaltungen();
                        for(int i=0; i< Veranstaltungsliste.size(); i++){
                            if(Veranstaltungsliste.get(i).getNumber().equals(nummer)){
                                CharSequence text = ueberschrift + " ist bereits in den Eigenen Veranstaltungen gespeichert";
                                Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_LONG);
                                toast.show();
                                return;
                            }
                        }

                        dataSource.createVeranstaltung(ueberschrift, nummer);
                        CharSequence text = ueberschrift + " wurde in Eigene Veranstaltungen gespeichert";
                        Toast toast = Toast.makeText(getActivity(), text, Toast.LENGTH_LONG);
                        toast.show();

                    }
                });
            }

            rowCountForWriting++;

            table.addView(row, new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT
                    )
            );

        }*/

        }


       /* private void setAdapter(ArrayList<String[]> result) {
           // BaumLetzteArrayAdapter adapter = new BaumLetzteArrayAdapter(BaumLetzteActivity.this, R.layout.baum_letzte_row, result);
            //listView.setAdapter(adapter);

            ArrayAdapter arrayAdapter = new ArrayAdapter(BaumLetzteActivity.this, android.R.layout.simple_list_item_1, result.get(0));
            ListView listView = (ListView) findViewById(R.id.baum_letzte_stufe_fragment_listView);
            listView.setAdapter(arrayAdapter);
        }*/
    }

}





