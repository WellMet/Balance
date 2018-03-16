package com.ua.ganoncannon.balance;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;


public class IntroSettingsActivity extends AppIntro {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.s_title), getResources().getString(R.string.s_string), R.drawable.shrunks, getResources().getColor(R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.sfont_title), getResources().getString(R.string.sf_string), R.drawable.font, getResources().getColor(R.color.colorAccent)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.sobject_title), getResources().getString(R.string.so_string), R.drawable.objects, getResources().getColor(R.color.colorAcc2)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.svolume_title), getResources().getString(R.string.sv_string), R.drawable.volume, getResources().getColor(R.color.colorAcc3)));
        addSlide(AppIntroFragment.newInstance(getResources().getString(R.string.sgoals_title), getResources().getString(R.string.sg_string), R.drawable.week, getResources().getColor(R.color.colorAcc4)));


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
        //startActivity(new Intent(IntroSettingsActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        //startActivity(new Intent(IntroSettingsActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}
