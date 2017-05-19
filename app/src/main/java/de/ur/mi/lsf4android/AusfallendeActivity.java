package de.ur.mi.lsf4android;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
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
import java.util.Calendar;
import java.util.List;

public class AusfallendeActivity extends NavigationActivity {

    public Veranstaltung veranstaltung;
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
    private TextView dateTextView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConstraintLayout contentConstraintLayout = (ConstraintLayout) findViewById(R.id.fragment_content_navigation); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_ausfallende, contentConstraintLayout);

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");

        String url = "https://lsf.uni-regensburg.de/qisserver/rds?state=currentLectures&type=1&next=CurrentLectures.vm&nextdir=ressourcenManager&navigationPosition=lectures%2CcanceledLectures&breadcrumb=canceledLectures&topitem=lectures&subitem=canceledLectures&&HISCalendar_Date=" + date + "&asi=";
        new DownloadLSFTask().execute(url);

        list = (ListView) findViewById(R.id.ausfallendeActivity_ListView);

        //dateEditText = (EditText) view.findViewById(R.id.date);
        datePickerButton = (Button) findViewById(R.id.button_date_picker);
        dateTextView = (TextView) findViewById(R.id.textView_date);
        dateTextView.setText(date);
        title = (TextView) findViewById(R.id.textView_ausfallendeActivity_title);
        begin = (TextView) findViewById(R.id.textView_ausfallendeActivity_beginn);
        number = (TextView) findViewById(R.id.textView_ausfallendeActivity_number);
        ende = (TextView) findViewById(R.id.textView_ausfallendeActivity_ende);



        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(AusfallendeActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                datePickerButton.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                               String newDate = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
                                Intent reloadIntent = new Intent(AusfallendeActivity.this,AusfallendeActivity.class);
                                reloadIntent.putExtra("date", newDate);
                                AusfallendeActivity.this.finish();
                                startActivity(reloadIntent);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
    }

    private void callDetailActivity(String titel, int j) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.TITEL_EXTRA, titel);
        intent.putExtra(DetailActivity.HTML_EXTRA, htmlList.get(j));
        startActivity(intent);
    }


    @Override
    public void onPause() {
        super.onPause();
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
                    String[] string_row = new String[5];
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
                                string_row[4] = link.attr("href");
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

        protected void onPostExecute(ArrayList<String[]> result) {
            // Tabelle in fragment_ausfallende.xml bauen und mit result befüllen
            //String titel = result.get(1)[2];
            //callDetailActivity(titel);

            begin.setText("Beginn");
            ende.setText("Ende");
            number.setText("Nr.");
            title.setText("Titel");

            if (result.size() != 0) {
                final ArrayList<Veranstaltung> veranstaltungen = new ArrayList<Veranstaltung>();
                //  veranstaltungen.add(new Veranstaltung("Beginn", "Ende", "Nummer", "Titel"));
                for (int i = 1; i < result.size(); i++) {
                    veranstaltungen.add(new Veranstaltung(result.get(i)[0], result.get(i)[1], result.get(i)[2], result.get(i)[3], result.get(i)[4]));
                }
                Context context = AusfallendeActivity.this;
                if (context != null) {
                    adapter = new AusfallendeActivityArrayAdapter(context, veranstaltungen);
                    list.setAdapter(adapter);

                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            if (i != 0) {
                                callDetailActivity(veranstaltungen.get(i).getTitel(), i - 1);
                            }
                        }
                    });

                }


            }
        }
        }


    }
