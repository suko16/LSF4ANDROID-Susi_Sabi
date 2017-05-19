package de.ur.mi.lsf4android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Susanne on 16.05.2017.
 */

public class BaumLetzteArrayAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] number;
    private final String[] title;
    private final String[] html;
    private TextView textViewNumber;
    private TextView textViewName;
    private Button button;
    private ImageView imageView;
    public EigeneVeranstaltungenDataSource dataSource;


    public BaumLetzteArrayAdapter(Context context, String[] number, String[] title, String[] html) {
        super(context, R.layout.row_baum_letzte, number);
        this.context = context;
        this.number = number;
        this.title = title;
        this.html = html;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_baum_letzte, parent, false);
        textViewNumber = (TextView) rowView.findViewById(R.id.baum_letzte_nummer);
        textViewName = (TextView) rowView.findViewById(R.id.baum_letzte_name);
        button = (Button) rowView.findViewById(R.id.button_baum_letzte);
        button.setId(position);
        //imageView = (ImageView) rowView.findViewById(R.id.imageView_button);

//TODO Textsize hier anpassen?
        textViewNumber.setText(number[position]);
        textViewNumber.setTextColor(Color.BLACK);
        textViewName.setText(title[position]);
        textViewName.setTextColor(Color.BLACK);
        textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivityWithFragments.class);
                intent.putExtra("titel", title[position]);
                intent.putExtra("html", html[position]);
                intent.putExtra("open_detail_start",true);
                context.startActivity(intent);
            }
        });





       //Überprüft vor dem erstellen welche Buttons für welche Veranstaltung
        //dementsprechender ClickListener gesetzt


        dataSource = new EigeneVeranstaltungenDataSource(getContext());
        dataSource.open();
        final List<Veranstaltung> VeranstaltungslisteDB = dataSource.getAllVeranstaltungen();
        String current = textViewNumber.getText().toString();
        for(int j=0; j< VeranstaltungslisteDB.size(); j++) {
            if (VeranstaltungslisteDB.get(j).getNumber().equals(current)) {
                final int temp = j;

                button.setBackgroundResource(R.mipmap.remove_button);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view){

                        LinearLayout vwParentRow = (LinearLayout)view.getParent();
                        Button buttonView = (Button) vwParentRow.getChildAt(2);
                        dataSource.deleteVeranstaltung(VeranstaltungslisteDB.get(temp));
                        CharSequence text = VeranstaltungslisteDB.get(temp).getTitel() + " wurde gelöscht";

                        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                        toast.show();
                        buttonView.setBackgroundResource(R.mipmap.add_button);
                    }
                });

                break;
            } else {

              button.setBackgroundResource(R.mipmap.add_button);
              button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        LinearLayout vwParentRow = (LinearLayout)view.getParent();
                        TextView tempTitle = (TextView) vwParentRow.getChildAt(1);
                        TextView tempNumber = (TextView) vwParentRow.getChildAt(0);
                        Button buttonView = (Button) vwParentRow.getChildAt(2);
                        dataSource.createVeranstaltung(tempTitle.getText().toString(), tempNumber.getText().toString(), html[position]);
                        CharSequence text = tempTitle.getText().toString() + " wurde in Eigene Veranstaltungen gespeichert";
                        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                        toast.show();
                        buttonView.setBackgroundResource(R.mipmap.remove_button);
                    }
                });
            }
        }









/*

        /final Button zweiButton = (Button) button.findViewById(getPosition("button")+1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout vwParentRow = (LinearLayout)view.getParent();

                //holt sich die Zeile in dem das View liegt und sucht sich das passende Kindelement heraus

               *//**//* Button buttonZwei = (Button)vwParentRow.getChildAt(2);
                buttonZwei.setBackgroundResource(R.mipmap.remove_button);*//**//*



                //und so kannst du auf dem Namen der Veranstaltung zugreifen
                TextView veranstaltungTitel = (TextView) vwParentRow.getChildAt(1);
                TextView veranstaltungsNumber = (TextView) vwParentRow.getChildAt(0);



                //Bei Klick überprüfen ob Veranstaltung schon gespeichert, wenn nicht speichern in Datenbank


                dataSource = new EigeneVeranstaltungenDataSource(getContext());
                dataSource.open();
                List<Veranstaltung> Veranstaltungsliste = dataSource.getAllVeranstaltungen();
                String number = veranstaltungsNumber.getText().toString();

                for(int i=0; i< Veranstaltungsliste.size(); i++){
                    if(Veranstaltungsliste.get(i).getNumber().equals(number)){
                        CharSequence text = veranstaltungTitel.getText() + " ist bereits in den Eigenen Veranstaltungen gespeichert";
                        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }
                }

                dataSource.createVeranstaltung(veranstaltungTitel.getText().toString(), number, html[position]);
                CharSequence text = veranstaltungTitel.getText().toString() + " wurde in Eigene Veranstaltungen gespeichert";
                Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                toast.show();





            }
        });*/


        return rowView;

    }




}
