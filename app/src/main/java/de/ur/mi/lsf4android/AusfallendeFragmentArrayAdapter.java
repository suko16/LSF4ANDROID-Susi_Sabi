package de.ur.mi.lsf4android;

import android.content.Context;
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

public class AusfallendeFragmentArrayAdapter extends ArrayAdapter<Veranstaltung> {
    private final Context context;
    private final ArrayList<Veranstaltung> veranstaltungen;

    public AusfallendeFragmentArrayAdapter(@NonNull Context context, ArrayList<Veranstaltung> veranstaltungen) {
        super(context, -1, veranstaltungen);
        this.context = context;
        this.veranstaltungen = veranstaltungen;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row_ausfallende, parent, false);
        TextView beginn = (TextView) rowView.findViewById(R.id.beginn);
        TextView ende = (TextView) rowView.findViewById(R.id.ende);
        TextView nummer = (TextView) rowView.findViewById(R.id.nummer);
        TextView titel = (TextView) rowView.findViewById(R.id.titel);
        beginn.setText(veranstaltungen.get(position).getBeginn());
        ende.setText(veranstaltungen.get(position).getEnde());
        nummer.setText(veranstaltungen.get(position).getNumber());
        titel.setText(veranstaltungen.get(position).getTitel());
        return rowView;
    }

    @Override
    public int getCount() {
        return veranstaltungen.size();
    }
}
