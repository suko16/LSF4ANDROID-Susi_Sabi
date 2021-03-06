package de.ur.mi.lsf4android;


import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.Button;
import android.widget.ListView;


public class Single_View_With_Fragments extends NavigationActivity implements Single_View_Fragment_Start.OpenOtherFragment {
    private ListView detailActivityListview;
    private Single_View_Basic_Data_ArrayAdapter detailActivityArrayAdapter;
    private Button button;
    private FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConstraintLayout contentConstraintLayout = (ConstraintLayout) findViewById(R.id.fragment_content_navigation); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.single_view_with_fragments, contentConstraintLayout);
        Intent intent = getIntent();
        if (savedInstanceState == null) {
            if (intent != null && intent.getBooleanExtra("open_detail_start", false)) {
                Fragment fragment = null;
                Class fragmentClass = null;
                fragmentClass = Single_View_Fragment_Start.class;
                try {
                    fragment = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                fragmentManager.beginTransaction().replace(R.id.fragment_content_navigation, fragment, "DETAIL_FRAGMENT").commit();
                setTitle("Detail");
            }
        }
    }

    public void getFragment(Class fragmentClass, String fragmentName) {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putString("titel", intent.getStringExtra("titel"));
        bundle.putString("html", intent.getStringExtra("html"));
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_content_navigation, fragment, fragmentName).commit();
        setTitle(fragmentName);
    }
}




