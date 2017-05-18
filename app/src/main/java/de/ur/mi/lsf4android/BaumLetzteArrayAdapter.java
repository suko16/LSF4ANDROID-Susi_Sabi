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

        textViewNumber.setText(number[position]);
        textViewName.setText(title[position]);
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

        //final Button zweiButton = (Button) button.findViewById(getPosition("button")+1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout vwParentRow = (LinearLayout)view.getParent();

                //holt sich die Zeile in dem das View liegt und sucht sich das passende Kindelement heraus
                Button buttonZwei = (Button)vwParentRow.getChildAt(2);

                buttonZwei.setBackgroundResource(R.mipmap.remove_button);

                //und so kannst du auf dem Namen der Veranstaltung zugreifen
                TextView veranstaltungTitel = (TextView) vwParentRow.getChildAt(1);
                CharSequence text = veranstaltungTitel.getText();
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                //TODO: Jetzt musst du "nur noch" den Button und den Text aus dem Textview mit der Datenbank verbinden, oder?

            }
        });

        return rowView;

    }




}
