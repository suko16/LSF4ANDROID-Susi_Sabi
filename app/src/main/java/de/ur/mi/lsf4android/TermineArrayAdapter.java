package de.ur.mi.lsf4android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sabi on 17.05.2017.
 */

public class TermineArrayAdapter extends ArrayAdapter<Veranstaltung> {
    private final Context context;
    private final ArrayList<Veranstaltung> veranstaltungen;

    public TermineArrayAdapter(@NonNull Context context, ArrayList<Veranstaltung> veranstaltungen) {
        super(context, -1, veranstaltungen);
        this.context = context;
        this.veranstaltungen = veranstaltungen;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_termin, parent, false);
        TextView group = (TextView) rowView.findViewById(R.id.group);
        TextView day = (TextView) rowView.findViewById(R.id.day);
        TextView time = (TextView) rowView.findViewById(R.id.time);
        TextView room = (TextView) rowView.findViewById(R.id.room);
        TextView teacher = (TextView) rowView.findViewById(R.id.teacher);
        group.setText("" + veranstaltungen.get(position).getGroup());
        day.setText(veranstaltungen.get(position).getTitel());
        time.setText(veranstaltungen.get(position).getBeginn());
        room.setText(veranstaltungen.get(position).getEnde());
        teacher.setText(veranstaltungen.get(position).getNumber());

        return rowView;
    }

    @Override
    public int getCount() {
        return veranstaltungen.size();
    }

}


