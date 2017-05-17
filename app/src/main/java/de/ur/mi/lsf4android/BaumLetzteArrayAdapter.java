package de.ur.mi.lsf4android;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Susanne on 16.05.2017.
 */

public class BaumLetzteArrayAdapter extends ArrayAdapter<String[]> {
    private final Context context;
    private final ArrayList<String[]> values;

    public BaumLetzteArrayAdapter(Context context, ArrayList<String[]> values) {
        super(context, R.layout.baum_letzte_row, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.baum_letzte_row, parent, false);
        TextView textViewNumber = (TextView) rowView.findViewById(R.id.baum_letzte_nummer);
        TextView textViewName = (TextView) rowView.findViewById(R.id.baum_letzte_name);
        Button button = (Button) rowView.findViewById(R.id.baum_letzte_button);

        textViewNumber.setText(values.get(0)[position]);
        textViewName.setText(values.get(1)[position]);
        button.setText("Add");

        return rowView;

    }
}
