package com.example.ganoncannon.balance;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.HashMap;

public class Exercise extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, NumberPicker.OnValueChangeListener, SeekBar.OnSeekBarChangeListener {
    private static final String ARG_PARAM1 = "param1";
    private HashMap<String, Integer> goals;

    private OnFragmentInteractionListener mListener;

    //UI Components
    private TextView difBanner;
    private TextView speedBanner;
    private TextView startBanner;
    private ToggleButton difToggle;
    private NumberPicker timer;
    private FloatingActionButton startButton;
    private ProgressBar progressBar;
    private SeekBar speedSlider;

    View view;

    public Exercise() {
        // Required empty public constructor
    }

    public static Exercise newInstance(HashMap<String, Integer> param1) {
        Exercise fragment = new Exercise();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            goals = (HashMap<String, Integer>) getArguments().getSerializable("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_exercise, container, false);

        // Init UI
        difBanner = (TextView) view.findViewById(R.id.difBanner);
        difToggle = (ToggleButton) view.findViewById(R.id.difToggle);
        timer = (NumberPicker) view.findViewById(R.id.timer);
        startButton = (FloatingActionButton) view.findViewById(R.id.startButton);
        startBanner = (TextView) view.findViewById(R.id.startBanner);
        progressBar = (ProgressBar) view.findViewById(R.id.exerciseProgress);
        speedBanner = (TextView) view.findViewById(R.id.speedBanner);
        speedSlider = (SeekBar) view.findViewById(R.id.speedSlider);

        // Initialize
        setupUI();

        difToggle.setOnCheckedChangeListener(this);
        startButton.setOnClickListener(this);
        timer.setOnValueChangedListener(this);
        speedSlider.setOnSeekBarChangeListener(this);

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

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }

    @Override
    public void onClick(View view) {
        // User clicked the Start/Stop toggle button.  Check timer value and adjust behavior
        //if (time > 0) && startBanner.getText().equals("Start"), startBanner.setText("Stop") etc...
        mListener.onFragmentInteraction(R.id.startButton);
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
        mListener.onFragmentInteraction(numberPicker.getId());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int rId);
    }

    public void setupUI() {
        difBanner.setText(R.string.dif_banner);
        if (goals.get("difficulty") == 1) {
            difToggle.setChecked(false);
        }
        else if (goals.get("difficulty") == 2) {
            difToggle.setChecked(true);
        }
        timer.setValue(goals.get("time") / 60);
        timer.setMaxValue(15);
        timer.setMinValue(5);
        startBanner.setText(R.string.start);
        progressBar.setProgress(0);
        progressBar.setMax(timer.getValue()*60); // Progress bar tracks seconds
        speedSlider.setMax(10);
        speedSlider.setProgress(goals.get("speed"));
    }
}
