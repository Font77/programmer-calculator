package com.annushkaproject.programmerscalculator.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


import com.annushkaproject.programmerscalculator.R;
import com.annushkaproject.programmerscalculator.fragments.ProgrammerFragment;
import com.annushkaproject.programmerscalculator.fragments.StandardFragment;
import com.annushkaproject.programmerscalculator.fragments.ThemesFragment;
import com.annushkaproject.programmerscalculator.fragments.langs_fragment;
import com.annushkaproject.programmerscalculator.model.ThemeSetting;
import com.annushkaproject.programmerscalculator.utils.SharedPreferencesUtil;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override protected void onCreate(Bundle savedInstanceState) {
        SharedPreferencesUtil prefUtil = new SharedPreferencesUtil(this);
        setTheme(ThemeSetting.getThemeStyleByThemeSetting(prefUtil.loadThemeSetting()));
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        if (savedInstanceState == null) {
//            //Open standard fragment when start the app for the first time.
//            StandardFragment standardFragment = new StandardFragment();
//            standardFragment.setupFragment(getPackageName());
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, standardFragment).commit();
//            navigationView.setCheckedItem(R.id.nav_standard_mode);
//        }
        if (savedInstanceState == null) {
            //Open programmer fragment when start the app for the first time.
            ProgrammerFragment programmer_fragment = new ProgrammerFragment();
            programmer_fragment.setupFragment(getPackageName());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, programmer_fragment).commit();
            navigationView.setCheckedItem(R.id.nav_programmer_mode);
        }
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_standard_mode) {
            StandardFragment standardFragment = new StandardFragment();
            standardFragment.setupFragment(getPackageName());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, standardFragment).commit();
        } else if (id == R.id.nav_programmer_mode) {
            ProgrammerFragment programmerFragment = new ProgrammerFragment();
            programmerFragment.setupFragment(getPackageName());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, programmerFragment).commit();
        } else if (id == R.id.nav_themes) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ThemesFragment()).commit();
        } else if (id == R.id.nav_languages) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new langs_fragment()).commit();
        }  else if (id == R.id.nav_share) {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            String shareBody = getString(R.string.share_website);
            String shareSub = getString(R.string.share_subject);
            myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
            myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(myIntent, getString(R.string.share_using)));
        } else if (id == R.id.nav_about) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setCancelable(true);
            builder.setTitle(getString(R.string.about_title));
            builder.setMessage(getString(R.string.about_program));
            builder.setPositiveButton(getString(R.string.button_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
