package com.ua.ganoncannon.balance;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import java.util.HashMap;


public class IntroHomeActivity extends AppIntro {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.home_title), getResources().getString(R.string.home_string), R.drawable.home, getResources().getColor(R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.welcome_title), getResources().getString(R.string.intro_string), R.drawable.navbar, getResources().getColor(R.color.colorAccent)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.tutorial_title), getResources().getString(R.string.action_string), R.drawable.tutorially, getResources().getColor(R.color.colorAcc2)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.help_title), getResources().getString(R.string.help_string), R.drawable.helper, getResources().getColor(R.color.colorAcc3)));

        // OPTIONAL METHODS
        // Override bar/separator color.
        //setBarColor(Color.parseColor("#3F51B5"));
        //setSeparatorColor(Color.parseColor("#2196F3"));

        // Hide Skip/Done button.
        showSkipButton(true);
        setProgressButtonEnabled(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent newIntent = new Intent(IntroHomeActivity.this, MainActivity.class);
        startActivity(newIntent);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent newIntent = new Intent(IntroHomeActivity.this, MainActivity.class);
        startActivity(newIntent);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
