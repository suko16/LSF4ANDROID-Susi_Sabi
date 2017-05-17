package de.ur.mi.lsf4android;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class BaumLetzteActivity extends Activity {

    public EigeneVeranstaltungenDataSource dataSource;
    public ArrayList<Button> buttonList;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_baum_letzte_stufe);
        new DownloadLSFTask().execute("https://lsf.uni-regensburg.de/qisserver/rds?state=wtree&search=1&trex=step&root120171=40852|40107|39734|37625|39743|37604&P.vx=mittel");
        listView = (ListView) this.findViewById(R.id.baum_letzte_stufe_fragment_listView);

    }


   /* private void callDetailActivity(String titel) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.TITEL_EXTRA, titel);
        startActivity(intent);
    }*/

    private class DownloadLSFTask extends AsyncTask<String, Integer, ArrayList<String[]>> {
        protected ArrayList<String[]> doInBackground(String... urls) {
            ArrayList<String[]> result = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(urls[0]).get();
                Elements table = doc.select("table[summary=Übersicht über alle Veranstaltungen]");
                Elements rows = table.select("tr");
                Elements header = rows.select("th");

                String[] numbers = new String[rows.size()];
                String[] titles = new String[rows.size()];
                String[] html = new String[rows.size()];

                for (int s = 0; s < rows.size(); s++) {
                    if (s == 0) {
                        numbers[s] = header.get(0).text();
                        titles[s] = header.get(1).text();
                        html[s] = "hate keine html";
                    } else {
                        numbers[s] = rows.get(s).select("td").get(0).text();
                        titles[s] = rows.get(s).select("td").get(1).text();
                        html[s] = rows.get(s).select("td").get(1).select("a[href]").html();
                    }
                }

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

            // BaumLetzteArrayAdapter adapter = new BaumLetzteArrayAdapter(getActivity(), result);

           /* BaumLetzteArrayAdapter adapter = new BaumLetzteArrayAdapter(this,result);

            ListView listView = (ListView) this.findViewById(R.id.baum_letzte_stufe_fragment_listView);
            listView.setAdapter(adapter);*/


            //setAdapter(result);

            BaumLetzteArrayAdapter adapter = new BaumLetzteArrayAdapter(BaumLetzteActivity.this, result);
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





