package com.ua.ganoncannon.balance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.*;

import java.util.HashMap;


public class IntroActivity extends AppIntro implements
        WeekSlide.OnFragmentInteractionListener,
        TimeSlide.OnFragmentInteractionListener,
        SpeedSlide.OnFragmentInteractionListener,
        DifSlide.OnFragmentInteractionListener{
    public HashMap<String, Integer> introGoals;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String introString = "You can use the bottom navigation bar to switch screens";
        addSlide(AppIntroFragment.newInstance("Welcome to UA Balance", introString, R.drawable.navbar, getResources().getColor(R.color.colorPrimary)));

        String homeString = "The home screen shows you your weekly goal progress, and a button to the exercise screen";
        addSlide(AppIntroFragment.newInstance("Home Screen", homeString, R.drawable.home, getResources().getColor(R.color.colorAccent)));

        // Goal slides
        String goalString = "Set your initial goals here!";
        //addSlide(AppIntroFragment.newInstance("First Goals", goalString, R.drawable.home, getResources().getColor(R.color.colorAcc2)));
        addSlide(WeekSlide.newInstance(R.layout.fragment_week_slide));
        addSlide(TimeSlide.newInstance(R.layout.fragment_time_slide));
        addSlide(SpeedSlide.newInstance(R.layout.fragment_speed_slide));
        addSlide(DifSlide.newInstance(R.layout.fragment_dif_slide));

        // OPTIONAL METHODS
        // Override bar/separator color.
        //setBarColor(Color.parseColor("#3F51B5"));
        //setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);

        introGoals = new HashMap<>();
        introGoals.put("attempts", 3);
        introGoals.put("time", 300);
        introGoals.put("speed", 5);
        introGoals.put("difficulty", 1);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent newIntent = new Intent(IntroActivity.this, MainActivity.class).putExtra("introGoals", introGoals);
        startActivity(newIntent);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent newIntent = new Intent(IntroActivity.this, MainActivity.class).putExtra("introGoals", introGoals);
        startActivity(newIntent);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }

    @Override
    public void onWeekInteraction(int i) {
        introGoals.put("attempts", i);
    }

    @Override
    public void onTimeInteraction(int i) {
        introGoals.put("time", i);
    }

    @Override
    public void onSpeedInteraction(int i) {
        introGoals.put("speed", i);
    }

    @Override
    public void onDifInteraction(int i) {
        introGoals.put("difficulty", i);
    }
}
