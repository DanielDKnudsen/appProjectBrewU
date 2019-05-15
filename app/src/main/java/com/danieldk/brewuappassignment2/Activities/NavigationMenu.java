package com.danieldk.brewuappassignment2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.danieldk.brewuappassignment2.BrewService;
import com.danieldk.brewuappassignment2.Fragments.AllBrews;
import com.danieldk.brewuappassignment2.Fragments.DetailedBrew;
import com.danieldk.brewuappassignment2.Fragments.MyBrews;
import com.danieldk.brewuappassignment2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NavigationMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        fragmentManager = this.getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        Fragment myBrews = new MyBrews();
        transaction.add(R.id.fragmentContainer, myBrews);
        transaction.commit();
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView menuUserName = headerView.findViewById(R.id.txtMenuUserName);
        menuUserName.setText(user.getDisplayName());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        startService(new Intent(this, BrewService.class));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        transaction = fragmentManager.beginTransaction();

        if (id == R.id.nav_myBrews) {
            Fragment myBrews = new MyBrews();
            transaction.replace(R.id.fragmentContainer, myBrews);

        } else if (id == R.id.nav_allBrews) {
            Fragment allBrews = new AllBrews();
            transaction.replace(R.id.fragmentContainer, allBrews);

        } else if (id == R.id.nav_logOut) {

            //TODO: kode hertil
        }
        transaction.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
