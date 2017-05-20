package de.ur.mi.lsf4android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Susanne on 17.05.2017.
 */

public class GrunddatenArrayAdapter extends ArrayAdapter<String[]> {
    private final Context context;
    private final String[][] values;
    private final int layoutRow;

    public GrunddatenArrayAdapter(Context context, int layoutRow, String[][] values) {
        super(context, layoutRow, values);
        this.context = context;
        this.values = values;
        this.layoutRow = layoutRow;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layoutRow, parent, false);
        TextView identifier = (TextView) rowView.findViewById(R.id.row_detail_activity_identifier);
        TextView context = (TextView) rowView.findViewById(R.id.row_detail_activity_context);

        identifier.setText(values[position][0]);
        context.setText(values[position][1]);
        return rowView;

    }
}
