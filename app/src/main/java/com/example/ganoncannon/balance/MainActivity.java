package com.example.ganoncannon.balance;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.io.Serializable;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements
        Home.OnFragmentInteractionListener,
        Exercise.OnFragmentInteractionListener,
        History.OnFragmentInteractionListener,
        Settings.OnFragmentInteractionListener {

    public Controller controller;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    item.setChecked(true);
                    loadFragment(new Home(), controller.getUser().getData());
                    break;
                case R.id.navigation_exercise:
                    item.setChecked(true);
                    loadFragment(new Exercise(), controller.getUser().getGoals());
                    break;
                case R.id.navigation_history:
                    item.setChecked(true);
                    loadFragment(new History(), controller.getUser().getData());
                    break;
                case R.id.navigation_settings:
                    item.setChecked(true);
                    loadFragment(new Settings(), controller.getUser().getSettings());
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller = new Controller();
        controller.getUser().getVoice().setContext(this.getApplicationContext());
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
    }

    protected void loadFragment(Fragment fragment, Serializable data) {
        if (fragment == null)
            return;
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();

            if (ft != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", data);
                fragment.setArguments(bundle);
                ft.replace(R.id.rootLayout, fragment);
                ft.commit();
            }
        }
    }

        @Override
        public void onFragmentInteraction(HashMap h) {
            int rId = (int) h.get("id");
            switch(rId) {
                // Home Screen Links
                case R.id.currentProgress:
                    mOnNavigationItemSelectedListener.onNavigationItemSelected((MenuItem)h.get("menu"));
                    break;
                case R.id.quickAction:
                    mOnNavigationItemSelectedListener.onNavigationItemSelected((MenuItem)h.get("menu"));
                    break;
                // Exercise Screen Data Links
                case R.id.timer:
                    controller.setChosenTime((int)h.get("value"));
                    break;
                case R.id.startButton:
                    controller.setState((String)h.get("state"));
                    break;
                case R.id.difToggle:
                    controller.setChosenDif((boolean)(h.get("value")) ? 1 : 0);
                    break;
                case R.id.speedSlider:
                    controller.setChosenSpeed((int)h.get("value"));
                    break;
                // History Screen Data Links
                    // Not needed, no controller action necessary
                // Settings Screen Data Links
            }
        }
}
