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
import android.widget.TextView;

import java.io.Serializable;

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
        public void onFragmentInteraction(int rId) {
            switch(rId) {
                // Home Screen Links
                case R.id.currentProgress:
                    break;
                case R.id.quickAction:
                    break;
                // Exercise Screen Links
                case R.id.timer:
                    System.out.println("got to timer yay");
                    break;
                case R.id.startButton:
                    System.out.println("mega poop");
                    break;
                case R.id.difToggle:
                    break;
                case R.id.speedSlider:
                    break;
                // History Screen Links

            }
        }
}
