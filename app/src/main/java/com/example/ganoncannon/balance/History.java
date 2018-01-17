package com.example.ganoncannon.balance;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class History extends Fragment implements View.OnClickListener {
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
            data = (Data) getArguments().getSerializable("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

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
        } else if (weeks.size() == 2) {
            week1 = weeks.get(weeks.size() - 1);
            week2 = weeks.get(weeks.size() - 2);
            prog1 = data.getOverallProgress(week1);
            prog2 = data.getOverallProgress(week2);
        } else if (weeks.size() <= 1) {
            week1 = weeks.get(weeks.size() - 1);
            prog1 = data.getOverallProgress(week1);
        }
        // Set GUI
        firstWeek.setText(week1);
        firstPercent.setText(Integer.toString(prog1));
        firstProgress.setProgress(prog1);
        secondWeek.setText(week2);
        secondPercent.setText(Integer.toString(prog2));
        secondProgress.setProgress(prog2);
        thirdWeek.setText(week3);
        thirdPercent.setText(Integer.toString(prog3));
    }

    @Override
    public void onClick(View view) {
        // TODO: Create another view that shows progress bubbles for each category
        // (runs, dif, speed, time)
        switch (view.getId()) {
            case (R.id.firstButton):
                System.out.println("gothere1");
                break;
            case (R.id.secondButton):
                System.out.println("gothere2");
                break;
            case (R.id.thirdButton):
                System.out.println("gothere3");
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int rId);
    }
}
