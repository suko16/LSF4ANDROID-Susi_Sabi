package de.ur.mi.lsf4android;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Susanne on 20.05.2017.
 */

public class Course_Overview_Path_ArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] string;


    public Course_Overview_Path_ArrayAdapter (Context context, String[] string) {
        super(context,R.layout.simple_row, string);

        this.string = string;
        this.context = context;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.simple_row, parent, false);
        TextView view = (TextView) rowView.findViewById(R.id.text1);
        view.setText(string[position]);
        return rowView;
    }


}
