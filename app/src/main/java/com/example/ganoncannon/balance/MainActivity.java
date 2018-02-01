package com.example.ganoncannon.balance;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.provider.Settings;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements
        Home.OnFragmentInteractionListener,
        Exercise.OnFragmentInteractionListener,
        History.OnFragmentInteractionListener,
        Configs.OnFragmentInteractionListener,
        Dialog.DialogListener {

    public Controller controller;
    public AudioManager audMgr;
    public int originalVolume;
    public int originalBrightness;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    item.setChecked(true);
                    loadFragment(new Home(), controller.getUser());
                    break;
                case R.id.navigation_exercise:
                    item.setChecked(true);
                    loadFragment(new Exercise(), controller.getUser());
                    break;
                case R.id.navigation_history:
                    item.setChecked(true);
                    loadFragment(new History(), controller.getUser());
                    break;
                case R.id.navigation_settings:
                    item.setChecked(true);
                    loadFragment(new Configs(), controller.getUser());
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Turn up max volume or load last volume, remember old version for when the app is closed, paused, etc...
        audMgr = (AudioManager) this.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        originalVolume = audMgr.getStreamVolume(AudioManager.STREAM_MUSIC);

        // Load backend
        controller = new Controller(this.getApplicationContext().getFilesDir().getPath(), audMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        controller.getUser().getVoice().setContext(this.getApplicationContext());
        audMgr.setStreamVolume(AudioManager.STREAM_MUSIC, (int)controller.getUser().getSettings().get("volume"), 0);

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
                    controller.setChosenTime((int)h.get("value") * 60);
                    break;
                case R.id.startButton:
                    controller.setState((String)h.get("state"), null);
                    break;
                case R.id.stopButton:
                    controller.setState((String)h.get("state"), (HashMap)h.get("data"));
                    break;
                case R.id.difToggle:
                    controller.setChosenDif((boolean)(h.get("value")) ? 2 : 1);
                    break;
                case R.id.speedSlider:
                    controller.setChosenSpeed((int)h.get("value"));
                    break;
                // History Screen Data Links
                    // Not needed, no controller action necessary
                // Configs Screen Data Links
                case R.id.fontSizeBar:
                    controller.getUser().setFontSize((int)h.get("fontSize"));
                    break;
                case R.id.objectMain:
                    controller.getUser().setObject((String)h.get("object"), (int)h.get("index"));
                    break;
                case R.id.volumeBar:
                    controller.getUser().setVolume((int)h.get("volume"));
                    audMgr.setStreamVolume(AudioManager.STREAM_MUSIC, (int)controller.getUser().getSettings().get("volume"), 0);
                    break;
            }
        }

    @Override
    public void onDialogInteraction(Uri uri) {

    }
}
