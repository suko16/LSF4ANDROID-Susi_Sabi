//fertig
//von Susi gebaut


package de.ur.mi.lsf4android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.navigation_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ImageView urLogo = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.urLogo);
        urLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUrLogoClicked();
            }
        });

        Intent Service = new Intent(this, BackgroundService.class);
        startService(Service);
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.navigation_drawer_layout);
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
        Intent i = new Intent(this,MainActivity.class);
        i.putExtra("open_eigene_fragment", true);
        i.putExtra("Button_Eigene", "Eigene Veranstaltungen");
        startActivity(i);
    }


    private void startAlleActivity(){
        Intent intentAlle = new Intent(this, BaumActivity.class);
        intentAlle.putExtra("HtmlExtra","https://lsf.uni-regensburg.de/qisserver/rds?state=wtree&search=1&trex=step&root120171=40852&P.vx=mittel");
        startActivity(intentAlle);
    }

}

