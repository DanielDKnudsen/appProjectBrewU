package com.danieldk.brewu.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.danieldk.brewu.Models.Brew;
import com.danieldk.brewu.R;
import com.danieldk.brewu.ViewModels.BrewViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class MyBrews extends AppCompatActivity {

    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_brews);

        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout);
        nvDrawer = findViewById(R.id.nvView);


        setupDrawerContent(nvDrawer);

        BrewViewModel model = ViewModelProviders.of(this).get(BrewViewModel.class);
        model.getBrews();

        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Brew brew = new Brew();
        brew.setAvgRating(3);
        brew.setBeerType("IPA");
        brew.setCreationDate(new Date());
        brew.setId("1");
        brew.setLink("hej med dig");
        brew.setNumberOfRatings(30);
        brew.setUserRating(5);
        brew.setUsername("Line");
        brew.setUserId("2");
        //brew.setUsername(user.getDisplayName());
        //brew.setUserId(user.getUid());

        //model.createBrew(brew);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }


    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new intent and navigate to it when clicked
        Intent intent = null;
        switch(menuItem.getItemId()) {
            case R.id.nav_test1:
                intent = new Intent(this, AllBrews.class);
                break;
            case R.id.nav_test2:
                intent = new Intent(this, MyBrews.class);
                break;
            case R.id.nav_test3:
                intent = new Intent(this, Login.class);
                break;
            default:
                intent = new Intent(this, Login.class);
        }

        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }


}
