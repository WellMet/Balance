package com.ua.ganoncannon.balance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;


public class IntroExerciseActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.exercise_title), getResources().getString(R.string.exercise_string), R.drawable.shrunke, getResources().getColor(R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.exercise_dif), getResources().getString(R.string.edif_string), R.drawable.dif, getResources().getColor(R.color.colorAccent)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.exercise_time), getResources().getString(R.string.etime_string), R.drawable.timer, getResources().getColor(R.color.colorAcc2)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.exercise_speed), getResources().getString(R.string.espeed_string), R.drawable.speed, getResources().getColor(R.color.colorAcc3)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.exercising), getResources().getString(R.string.e_string), R.drawable.shrunke, getResources().getColor(R.color.colorAcc4)));

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
        //startActivity(new Intent(IntroExerciseActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        //startActivity(new Intent(IntroExerciseActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
