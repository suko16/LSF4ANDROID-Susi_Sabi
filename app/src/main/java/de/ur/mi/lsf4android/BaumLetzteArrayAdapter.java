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
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Susanne on 16.05.2017.
 */

public class BaumLetzteArrayAdapter extends ArrayAdapter<String[]> {
    private final Context context;
    private final ArrayList<String[]> values;
    private final Button[] buttonArray;
    private TextView textViewNumber;
    private TextView textViewName;
    private Button button;
    private ImageView imageView;


    public BaumLetzteArrayAdapter(Context context, ArrayList<String[]> values, Button[] buttonArray) {
        super(context, R.layout.row_baum_letzte, values);
        this.context = context;
        this.values = values;
        this.buttonArray = buttonArray;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.row_baum_letzte, parent, false);
        textViewNumber = (TextView) rowView.findViewById(R.id.baum_letzte_nummer);
        textViewName = (TextView) rowView.findViewById(R.id.baum_letzte_name);
        buttonArray[position] = (Button) rowView.findViewById(R.id.button_baum_letzte);

        //imageView = (ImageView) rowView.findViewById(R.id.imageView_button);

        textViewNumber.setText(values.get(1)[position]);
        textViewName.setText(values.get(2)[position]);
        textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.TITEL_EXTRA, values.get(2)[position]);
                intent.putExtra(DetailActivity.HTML_EXTRA, values.get(3)[position]);
                context.startActivity(intent);
            }
        });

        buttonArray[position].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonArray[position].setBackgroundResource(R.mipmap.remove_button);
            }
        });

        return rowView;

    }




}
