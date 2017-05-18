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
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.TITEL_EXTRA, title[position]);
                intent.putExtra(DetailActivity.HTML_EXTRA, html[position]);
                context.startActivity(intent);
            }
        });





        long i = getItemId(position);


       /*
TODO: ich würde gerne die Buttons je nachdem ob der Kurs schon gespeichert ist grün oder rot machen, kann aber außerhalb einer onClick view.getParent nicht verwenden.
TODO: hast du ne idee wie ich das aufrufen könnte? wenn ich das weglasse überprüfts mir nur die letzte veranstaltung und passt den button entsprechend an.. also brauch ich vmtl auch das
TODO: mit dem Child und ParentRow.. bloß des View view hab ich ohne OnClick nicht zur verfügung und mit convertView und getView etc gehts nicht...


       //Überprüft vor dem erstellen welche Buttons für welche Veranstaltung


        LinearLayout vwParentRow = (ViewGroup)(LinearLayout)view.getParent();

        final TextView veranstaltungTitel = (TextView) vwParentRow.getChildAt(1);
        TextView veranstaltungsNumber = (TextView) vwParentRow.getChildAt(0);

        dataSource = new EigeneVeranstaltungenDataSource(getContext());
        dataSource.open();
        List<EigeneV_Objekt> Veranstaltungsliste = dataSource.getAllVeranstaltungen();
        final String number = veranstaltungsNumber.getText().toString();

        for(int j=0; j< Veranstaltungsliste.size(); j++) {
            if (Veranstaltungsliste.get(j).getNumber().equals(number)) {
                Button ButtonMinus = (Button) vwParentRow.getChildAt(2);

                ButtonMinus.setBackgroundResource(R.mipmap.remove_button);
                // ButtonMinus .setOnClickListener(delete);
            } else {

                Button ButtonPlus = (Button) vwParentRow.getChildAt(2);
                ButtonPlus.setBackgroundResource(R.mipmap.add_button);
                ButtonPlus.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        dataSource.createVeranstaltung(veranstaltungTitel.getText().toString(), number);
                        CharSequence text = veranstaltungTitel.getText().toString() + " wurde in Eigene Veranstaltungen gespeichert";
                        Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
                        toast.show();
                    }
                });
            }
        }


*/







        //final Button zweiButton = (Button) button.findViewById(getPosition("button")+1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout vwParentRow = (LinearLayout)view.getParent();

                //holt sich die Zeile in dem das View liegt und sucht sich das passende Kindelement heraus

               /* Button buttonZwei = (Button)vwParentRow.getChildAt(2);
                buttonZwei.setBackgroundResource(R.mipmap.remove_button);*/

               //TODO: das mit dem roten Button würde ich nur machen wenn s.oben geht :)

                //und so kannst du auf dem Namen der Veranstaltung zugreifen
                TextView veranstaltungTitel = (TextView) vwParentRow.getChildAt(1);
                TextView veranstaltungsNumber = (TextView) vwParentRow.getChildAt(0);


                //Bei Klick überprüfen ob Veranstaltung schon gespeichert, wenn nicht speichern in Datenbank


                dataSource = new EigeneVeranstaltungenDataSource(getContext());
                dataSource.open();
                List<EigeneV_Objekt> Veranstaltungsliste = dataSource.getAllVeranstaltungen();
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
        });
        return rowView;

    }




}
