package de.ur.mi.lsf4android;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Susanne on 19.05.2017.
 */

public class DetailFragment extends android.support.v4.app.Fragment  {
    OpenOtherFragment mCallback;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_detail_start, container, false);

        final Button[] buttonArray = new Button[6];
        buttonArray[0] = (Button) view.findViewById(R.id.grunddaten_button);
        buttonArray[1] = (Button) view.findViewById(R.id.termine_button);
        buttonArray[2] = (Button) view.findViewById(R.id.zuordnung_button);
        buttonArray[3] = (Button) view.findViewById(R.id.pruefung_button);
        buttonArray[4] = (Button) view.findViewById(R.id.inhalt_button);
        buttonArray[5] = (Button) view.findViewById(R.id.uebung_button);

        //final Button button = (Button) view.findViewById(R.id.termine_button);
        final Class fragmentClass = null;

        for (int i = 0; i < buttonArray.length; i++) {
            final int counter = i;
            buttonArray[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleClickOnButton(counter);

                }
            });

        }
            return view;
    }


    // Container Activity must implement this interface
    public interface OpenOtherFragment {
        public void getFragment(Class fragmentClass, String fragmentName);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OpenOtherFragment) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    private void handleClickOnButton (int counter){
        Class fragmentClass;
        String fragmentName;
        switch (counter) {
            case 0:
                fragmentClass = GrunddatenFragment.class;
                fragmentName = "GRUNDDATEN";
                break;
            case 1:
                fragmentClass = TermineFragment.class;
                fragmentName = "TERMINE";
                break;
            case 2:
                fragmentClass = ZuordnungFragment.class;
                fragmentName = "ZUORDNUNG";
                break;
            case 3:
                fragmentClass = PruefungFragment.class;
                fragmentName = "PRUEFUNG";
                break;
            case 4:
                fragmentClass = InhaltFragment.class;
                fragmentName = "INHALT";
                break;
            case 5:
                fragmentClass = UebungFragment.class;
                fragmentName = "UEBUNG";
                break;
            default:
                fragmentClass = GrunddatenFragment.class;
                fragmentName = "GRUNDDATEN";
                break;
        }

        mCallback.getFragment(fragmentClass, fragmentName);


        /*try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_content_navigation, fragment).commit();*/
    }



}
