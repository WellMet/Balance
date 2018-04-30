package com.ua.ganoncannon.balance;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements
        Home.OnFragmentInteractionListener,
        Exercise.OnFragmentInteractionListener,
        History.OnFragmentInteractionListener,
        Configs.OnFragmentInteractionListener,
        Help.OnFragmentInteractionListener,
        Dialog.DialogListener {

    public Controller controller;
    public AudioManager audMgr;
    public int originalVolume;
    public int originalBrightness;
    public int curFrag = 0;
    private Toolbar toolbar;

    public boolean notifScheduled = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (controller.getState().equals("idle")) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean previouslyStarted = false;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        item.setChecked(true);
                        loadFragment(new Home(), controller.getUser());
                        curFrag = 0;
                        break;
                    case R.id.navigation_exercise:
                        previouslyStarted = prefs.getBoolean("first_time_exercise", false);
                        if(!previouslyStarted) {
                            SharedPreferences.Editor edit = prefs.edit();
                            edit.putBoolean("first_time_exercise", Boolean.TRUE);
                            edit.commit();

                            // Show first exercise activity
                            startActivity(new Intent(MainActivity.this, IntroExerciseActivity.class));
                        }
                        item.setChecked(true);
                        loadFragment(new Exercise(), controller.getUser());
                        curFrag = 1;
                        break;
                    case R.id.navigation_history:
                        previouslyStarted = prefs.getBoolean("first_time_history", false);
                        if(!previouslyStarted) {
                            SharedPreferences.Editor edit = prefs.edit();
                            edit.putBoolean("first_time_history", Boolean.TRUE);
                            edit.commit();

                            // Show first exercise activity
                            startActivity(new Intent(MainActivity.this, IntroHistoryActivity.class));
                        }
                        item.setChecked(true);
                        loadFragment(new History(), controller.getUser());
                        curFrag = 2;
                        break;
                    case R.id.navigation_settings:
                        previouslyStarted = prefs.getBoolean("first_time_settings", false);
                        if(!previouslyStarted) {
                            SharedPreferences.Editor edit = prefs.edit();
                            edit.putBoolean("first_time_settings", Boolean.TRUE);
                            edit.commit();

                            // Show first exercise activity
                            startActivity(new Intent(MainActivity.this, IntroSettingsActivity.class));
                        }
                        item.setChecked(true);
                        loadFragment(new Configs(), controller.getUser());
                        curFrag = 3;
                        break;
                }
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_help:
                // Help Popup
                System.out.println("help lol");
                FragmentManager fm = getSupportFragmentManager();
                Help help = Help.newInstance(controller.getUser(), "");
                help.show(fm, "fragment_help");
                break;
            // action with ID action_settings was selected
            case R.id.action_tutorial:
                switch(curFrag) {
                    case 0:
                        // Show home tutorial
                        startActivity(new Intent(MainActivity.this, IntroHomeActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(MainActivity.this, IntroExerciseActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(MainActivity.this, IntroHistoryActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(MainActivity.this, IntroSettingsActivity.class));
                        break;
                }
                break;
            default:
                break;
        }

        return true;
    }

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

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        scheduleNotification();
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("in resume");
        if(getIntent().getExtras() != null) {
            if (getIntent().getExtras().get("introGoals") != null) {
                System.out.println("extras yo");
                controller.getUser().setGoals((HashMap) getIntent().getExtras().get("introGoals"));
            }
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean previouslyStarted = prefs.getBoolean("first_time_main", false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = prefs.edit();
            edit.putBoolean("first_time_main", Boolean.TRUE);
            edit.commit();

            // Show first startup activity
            startActivity(new Intent(MainActivity.this, IntroActivity.class));
        }
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
            BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
            switch(rId) {
                // Home Screen Links
                case R.id.currentProgress:
                    mOnNavigationItemSelectedListener.onNavigationItemSelected(bottomNavigationView.getMenu().getItem(2));
                    break;
                case R.id.quickAction:
                    mOnNavigationItemSelectedListener.onNavigationItemSelected(bottomNavigationView.getMenu().getItem(1));
                    break;
                // Exercise Screen Data Links
                case R.id.minute:
                    controller.setChosenTime((int)h.get("value"));
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
                case R.id.fBar:
                    controller.getUser().setGoal("attempts", (int)h.get("value"));
                    break;
                case R.id.difToggle2:
                    controller.getUser().setGoal("difficulty", (boolean)h.get("value") ? 2 : 1);
                    break;
                case R.id.sMinute:
                    controller.getUser().setGoal("time", (int)h.get("value"));
                    break;
                case R.id.freqBar2:
                    controller.getUser().setGoal("speed", (int)h.get("value"));
                    break;
            }
        }


    @Override
    public void onDialogInteraction(Uri uri) {

    }



    // Notifications
    public void scheduleNotification() {
        Notification notification = buildNotification();
        // Only schedule notifications if attempts left
        if (notification != null) {
            Intent notificationIntent = new Intent(this, Notifier.class);
            notificationIntent.putExtra(Notifier.NOTIFICATION_ID, 1);
            notificationIntent.putExtra(Notifier.NOTIFICATION, notification);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

            // Find how many milliseconds until next noon
            Calendar cal = Calendar.getInstance(); // current date and time
            long curTime = cal.getTimeInMillis();
            cal.add(Calendar.DAY_OF_MONTH, 1);
            cal.set(Calendar.HOUR_OF_DAY, 12); // set to noon
            long nextTime = cal.getTimeInMillis();
            long delay = nextTime - curTime;

            long futureInMillis = SystemClock.elapsedRealtime() + delay;
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
        }
    }

    public Notification buildNotification() {
        // Check if there is any attempts left for them to complete until their goal
        ArrayList<String> weeks = new ArrayList<String>(controller.getUser().getData().getHistory().keySet());
        Collections.sort(weeks);
        ArrayList<HashMap<String, Integer>> attempts = controller.getUser().getData().getHistory().get(weeks.get(weeks.size() - 1));
        int attemptsLeft = (int)controller.getUser().getGoals().get("attempts") - attempts.size();
        if (attemptsLeft > 0) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setContentTitle("UA Balance Reminder");
            builder.setContentText("According to your goals, you have " + attemptsLeft + " balance sessions left this week.  Come practice!");
            builder.setSmallIcon(R.drawable.appicon);
            return builder.build();
        } else {
            return null;
        }
    }
}
