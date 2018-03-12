package com.ua.ganoncannon.balance;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class History extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private Data data;

    private OnFragmentInteractionListener mListener;

    private TextView firstWeek;
    private ProgressBar firstProgress;
    private TextView firstPercent;
    private FloatingActionButton firstButton;
    private TextView secondWeek;
    private ProgressBar secondProgress;
    private TextView secondPercent;
    private FloatingActionButton secondButton;
    private TextView thirdWeek;
    private ProgressBar thirdProgress;
    private TextView thirdPercent;
    private FloatingActionButton thirdButton;

    View view;

    public History() {
        // Required empty public constructor
    }

    public static History newInstance(Data param1) {
        History fragment = new History();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Profile p = (Profile) getArguments().getSerializable("data");
            data = p.getData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_history, container, false);

        firstWeek = (TextView) view.findViewById(R.id.firstWeek);
        firstProgress = (ProgressBar) view.findViewById(R.id.firstProgress);
        firstPercent = (TextView) view.findViewById(R.id.firstPercent);
        firstButton = (FloatingActionButton) view.findViewById(R.id.firstButton);
        secondWeek = (TextView) view.findViewById(R.id.secondWeek);
        secondProgress = (ProgressBar) view.findViewById(R.id.secondProgress);
        secondPercent = (TextView) view.findViewById(R.id.secondPercent);
        secondButton = (FloatingActionButton) view.findViewById(R.id.secondButton);
        thirdWeek = (TextView) view.findViewById(R.id.thirdWeek);
        thirdProgress = (ProgressBar) view.findViewById(R.id.thirdProgress);
        thirdPercent = (TextView) view.findViewById(R.id.thirdPercent);
        thirdButton = (FloatingActionButton) view.findViewById(R.id.thirdButton);

        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                Dialog dF = Dialog.newInstance((Profile) getArguments().getSerializable("data"), firstWeek.getText().toString().split(" ")[1], 0);
                dF.show(fm, "fragment_dialog");
            }
        });

        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                Dialog dF = Dialog.newInstance((Profile) getArguments().getSerializable("data"), firstWeek.getText().toString().split(" ")[1], 1);
                dF.show(fm, "fragment_dialog");
            }
        });

        thirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                Dialog dF = Dialog.newInstance((Profile) getArguments().getSerializable("data"), firstWeek.getText().toString().split(" ")[1], 2);
                dF.show(fm, "fragment_dialog");
            }
        });

        setupUI();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
        // Get the weeks
        ArrayList<String> weeks = new ArrayList<String>(data.getHistory().keySet());
        Collections.sort(weeks);
        String week1 = "N/A";
        String week2 = "N/A";
        String week3 = "N/A";
        int prog1 = 0;
        int prog2 = 0;
        int prog3 = 0;
        // Decide the values
        if (weeks.size() >= 3) {
            week1 = weeks.get(weeks.size() - 1);
            week2 = weeks.get(weeks.size() - 2);
            week3 = weeks.get(weeks.size() - 3);
            prog1 = data.getOverallProgress(week1);
            prog2 = data.getOverallProgress(week2);
            prog3 = data.getOverallProgress(week3);
            firstButton.setEnabled(true);
            secondButton.setEnabled(true);
            thirdButton.setEnabled(true);
        } else if (weeks.size() == 2) {
            week1 = weeks.get(weeks.size() - 1);
            week2 = weeks.get(weeks.size() - 2);
            prog1 = data.getOverallProgress(week1);
            prog2 = data.getOverallProgress(week2);
            firstButton.setEnabled(true);
            secondButton.setEnabled(true);
            thirdButton.setEnabled(false);
        } else if (weeks.size() <= 1) {
            week1 = weeks.get(weeks.size() - 1);
            prog1 = data.getOverallProgress(week1);
            firstButton.setEnabled(true);
            secondButton.setEnabled(false);
            thirdButton.setEnabled(false);
        }
        // Set GUI
        firstWeek.setText("Week " + week1);
        firstPercent.setText(Integer.toString(prog1));
        firstProgress.setProgress(prog1);
        secondWeek.setText("Week " + week2);
        secondPercent.setText(Integer.toString(prog2));
        secondProgress.setProgress(prog2);
        thirdWeek.setText("Week " + week3);
        thirdPercent.setText(Integer.toString(prog3));
        thirdProgress.setProgress(prog3);

        Profile p = (Profile) getArguments().getSerializable("data");
        firstWeek.setTextSize(1, (int)p.getSettings().get("textSize"));
        firstPercent.setTextSize(1, (int)p.getSettings().get("textSize"));
        secondWeek.setTextSize(1, (int)p.getSettings().get("textSize"));
        secondPercent.setTextSize(1, (int)p.getSettings().get("textSize"));
        thirdWeek.setTextSize(1, (int)p.getSettings().get("textSize"));
        thirdPercent.setTextSize(1, (int)p.getSettings().get("textSize"));
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(HashMap h);
    }
}
