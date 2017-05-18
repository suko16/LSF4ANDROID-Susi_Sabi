//fertig
//von Susi gebaut


package de.ur.mi.lsf4android;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ShareCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageView urLogo = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.urLogo);
        urLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUrLogoClicked();
            }
        });

        //Bei Activitywechseln von StartActivity auf MainActivity
        // wird zusätzlich richtiges Fragment durch Extra geladen.
        Intent intent = getIntent();
        Fragment fragment = null;
        Class fragmentClass = null;


        if (intent != null && intent.getBooleanExtra("open_eigene_fragment",false)){
            fragmentClass = EigeneFragment.class;
            setTitle(intent.getStringExtra("Button_Eigene"));
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_main, fragment).commit();

        Intent Service = new Intent(this, BackgroundService.class);
        startService(Service);

    }





    public void onArticleSelected(int position) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
    }

        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_ausfallende_V:
                startAusfallendeActivity();
                break;

            //TODO: es fehlt der start der AusfallendeActivity aus der Navigation mit den Fragments
            //TODO: mit dem intent öffnets zwar die klasse aber über AusfallendeFragment (sieht man wenn man zurück geht). Fällt dir noch was ein?

            case R.id.nav_eigene_V:
               startEigeneFragment();
                break;
            case R.id.nav_alle_V:
                startAlleActivity();
                break;
            default:
                startAusfallendeActivity();
        }

        item.setChecked(true);
        setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

   private void onUrLogoClicked(){
        Intent i = new Intent(this,StartActivity.class);
        startActivity(i);
    }

    private void startAusfallendeActivity(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat datumsformat = new SimpleDateFormat("dd.MM.yyyy");
        String date = datumsformat.format(calendar.getTime());
        Intent intentAusfallende = new Intent(this, AusfallendeActivity.class);
        intentAusfallende.putExtra("date", date);
        startActivity(intentAusfallende);
    }

    private void startEigeneFragment(){
        Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = EigeneFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_main, fragment).commit();
    }

    private void startAlleActivity(){
        Intent intentAlle = new Intent(this, BaumActivity.class);
        intentAlle.putExtra("HtmlExtra","https://lsf.uni-regensburg.de/qisserver/rds?state=wtree&search=1&trex=step&root120171=40852&P.vx=mittel");
        startActivity(intentAlle);
    }

}

