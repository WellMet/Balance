package com.ua.ganoncannon.balance;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;


public class Dialog extends DialogFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private String mParam1;
    private Data data;
    private String week;
    private int weekNum;

    private FloatingActionButton fullButton;
    private ProgressBar fullProgress;
    private TextView fullPercent;
    private TextView fullWeek;

    private TextView col1;
    private TextView col2;

    private TextView speedBanner;
    private ProgressBar speedBar;
    private TextView attemptsBanner;
    private ProgressBar attemptsBar;
    private TextView diffBanner;
    private ProgressBar diffBar;
    private TextView timeBanner;
    private ProgressBar timeBar;

    View view;

    private DialogListener mListener;

    public Dialog() {
        // Required empty public constructor
    }

    public static Dialog newInstance(Profile param1, String param2, int param3) {
        Dialog fragment = new Dialog();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Profile p = (Profile) getArguments().getSerializable(ARG_PARAM1);
            data = p.getData();
            week = getArguments().getString(ARG_PARAM2);
            weekNum = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_dialog, container, false);

        // init GUI
        fullButton = (FloatingActionButton) view.findViewById(R.id.firstButton2);
        fullProgress = (ProgressBar) view.findViewById(R.id.firstProgress2);
        fullPercent = (TextView) view.findViewById(R.id.firstPercent2);
        fullWeek = (TextView) view.findViewById(R.id.firstWeek2);
        col1 = (TextView) view.findViewById(R.id.col1);
        col2 = (TextView) view.findViewById(R.id.col2);
        speedBanner = (TextView) view.findViewById(R.id.spweedBanner);
        speedBar = (ProgressBar) view.findViewById(R.id.spweedBar);
        diffBanner = (TextView) view.findViewById(R.id.diffBanner);
        diffBar = (ProgressBar) view.findViewById(R.id.diffBar);
        attemptsBanner = (TextView) view.findViewById(R.id.attemptBanner);
        attemptsBar = (ProgressBar) view.findViewById(R.id.attemptBar);
        timeBanner = (TextView) view.findViewById(R.id.timeBanner);
        timeBar = (ProgressBar) view.findViewById(R.id.timeBar);

        fullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        setupUI();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DialogListener) {
            mListener = (DialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setupUI() {
        Profile p = (Profile) getArguments().getSerializable(ARG_PARAM1);
        int colorId = 0;
        if (weekNum == 0) {
            colorId = R.color.colorAccent;
        } else if (weekNum == 1) {
            colorId = R.color.colorAcc2;
        } else if (weekNum == 2) {
            colorId = R.color.colorAcc3;
        }
        Drawable progressDrawable = fullProgress.getProgressDrawable().mutate();
        progressDrawable.setColorFilter(getResources().getColor(colorId), android.graphics.PorterDuff.Mode.SRC_IN);
        fullProgress.setProgressDrawable(progressDrawable);
        fullProgress.setProgress(data.getOverallProgress(week));
        fullPercent.setTextSize(1, (int)p.getSettings().get("textSize") - 3);
        fullPercent.setTextColor(getResources().getColor(colorId));

        fullPercent.setText(Integer.toString(data.getOverallProgress(week)));
        fullWeek.setTextSize(1, (int)p.getSettings().get("textSize") - 3);
        fullWeek.setText("Week " + week);
        fullWeek.setTextColor(getResources().getColor(colorId));

        col1.setTextColor(getResources().getColor(colorId));
        col1.setTextSize(1, (int)p.getSettings().get("textSize") - 3);
        col2.setTextColor(getResources().getColor(colorId));
        col2.setTextSize(1, (int)p.getSettings().get("textSize") - 3);

        attemptsBanner.setTextSize(1, (int)p.getSettings().get("textSize") - 5);
        progressDrawable = attemptsBar.getProgressDrawable().mutate();
        progressDrawable.setColorFilter(getResources().getColor(colorId), android.graphics.PorterDuff.Mode.SRC_IN);
        attemptsBar.setProgressDrawable(progressDrawable);
        attemptsBar.setMax(50);
        attemptsBar.setProgress(data.getProgressScore(week, "attempts"));
        System.out.println("attempts progres = " + data.getProgressScore(week, "attempts"));

        timeBanner.setTextSize(1, (int)p.getSettings().get("textSize") - 5);
        progressDrawable = timeBar.getProgressDrawable().mutate();
        progressDrawable.setColorFilter(getResources().getColor(colorId), android.graphics.PorterDuff.Mode.SRC_IN);
        timeBar.setProgressDrawable(progressDrawable);
        timeBar.setMax(25);
        timeBar.setProgress(data.getProgressScore(week, "time"));
        System.out.println("attempts time = " + data.getProgressScore(week, "time"));

        speedBanner.setTextSize(1, (int)p.getSettings().get("textSize") - 5);
        progressDrawable = speedBar.getProgressDrawable().mutate();
        progressDrawable.setColorFilter(getResources().getColor(colorId), android.graphics.PorterDuff.Mode.SRC_IN);
        speedBar.setProgressDrawable(progressDrawable);
        speedBar.setMax(15);
        speedBar.setProgress(data.getProgressScore(week, "speed"));

        diffBanner.setTextSize(1, (int)p.getSettings().get("textSize") - 5);
        progressDrawable = diffBar.getProgressDrawable().mutate();
        progressDrawable.setColorFilter(getResources().getColor(colorId), android.graphics.PorterDuff.Mode.SRC_IN);
        diffBar.setProgressDrawable(progressDrawable);
        diffBar.setMax(10);
        diffBar.setProgress(data.getProgressScore(week, "difficulty"));
    }

    public interface DialogListener {
        void onDialogInteraction(Uri uri);
    }
}
