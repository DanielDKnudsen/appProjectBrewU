package com.danieldk.brewuappassignment2.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.danieldk.brewuappassignment2.BrewService;
import com.danieldk.brewuappassignment2.Fragments.AllBrews;
import com.danieldk.brewuappassignment2.Fragments.CreateBrew;
import com.danieldk.brewuappassignment2.Fragments.DetailedBrew;
import com.danieldk.brewuappassignment2.Fragments.MyBrews;
import com.danieldk.brewuappassignment2.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class NavigationMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private Configuration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        config = getResources().getConfiguration();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        fragmentManager = this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        Fragment myBrews = new MyBrews();

        if(!checkSize())
        {
            if(fragments.isEmpty()) {
                transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.fragmentContainer, myBrews);
                transaction.commit();
            }
        }
        else{
            transaction = fragmentManager.beginTransaction();
            if (fragments.isEmpty()) {
                if(checkLandscape())
                    transaction.add(R.id.listcontainer,myBrews);
                else transaction.add(R.id.fragmentContainer,myBrews);
            }
            else{
                if(checkLandscape())
                {
                    for (Fragment frag: fragments
                    ) {
                        fragmentManager.beginTransaction().remove(frag).commit();
                        fragmentManager.executePendingTransactions();
                    }
                    fragmentManager.beginTransaction().add(R.id.listcontainer, myBrews).commit();
                }
                else{
                    for (Fragment frag: fragments
                    ) {
                    fragmentManager.beginTransaction().remove(frag).commit();
                    fragmentManager.executePendingTransactions();
                    }
                    fragmentManager.beginTransaction().add(R.id.fragmentContainer, myBrews).commit();

                }
            }
            transaction.commit();
        }
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView menuUserName = headerView.findViewById(R.id.txtMenuUserName);
        menuUserName.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        startService(new Intent(this, BrewService.class));
    }

    private Fragment recreateFragment(Fragment f)
    {
        try {
            Fragment.SavedState savedState = fragmentManager.saveFragmentInstanceState(f);

            Fragment newInstance = f.getClass().newInstance();
            newInstance.setInitialSavedState(savedState);

            return newInstance;
        }
        catch (Exception e) // InstantiationException, IllegalAccessException
        {
            throw new RuntimeException("Cannot reinstantiate fragment " + f.getClass().getName(), e);
        }
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
        for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
            fragmentManager.popBackStack();
        }
        transaction = fragmentManager.beginTransaction();
        for (Fragment fragment:getSupportFragmentManager().getFragments()) {
                transaction.remove(fragment);
        }

        if (id == R.id.nav_myBrews) {
            Fragment myBrews = new MyBrews();
            if(checkSize()&&checkSize())
                transaction.replace(R.id.listcontainer,myBrews);
            else
                 transaction.replace(R.id.fragmentContainer, myBrews);

        } else if (id == R.id.nav_allBrews) {
            Fragment allBrews = new AllBrews();
            if(checkSize()&&checkLandscape())
                transaction.replace(R.id.listcontainer,allBrews);
            else
                transaction.replace(R.id.fragmentContainer, allBrews);

        } else if (id == R.id.nav_createBrew) {
            Fragment createBrew = new CreateBrew();
            transaction.replace(R.id.fragmentContainer, createBrew);

        } else if (id == R.id.nav_logOut) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this,Login.class));
        }
        transaction.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean checkSize()
    {
        if ((config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE)
            return true;
        return false;
    }

    public boolean checkLandscape()
    {
     if(config. orientation == Configuration.ORIENTATION_LANDSCAPE)
         return true;
     else return false;
    }

}
