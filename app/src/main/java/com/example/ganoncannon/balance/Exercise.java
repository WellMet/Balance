package com.example.ganoncannon.balance;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

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
    private Button stopButton;
    private ProgressBar progressBar;
    private SeekBar speedSlider;
    private CountDownTimer countDownTimer;
    private int remainingTime;
    private int lastTime;
    private int seconds;
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
            Profile p = (Profile) getArguments().getSerializable("data");
            goals = p.getGoals();
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
        stopButton = (Button) view.findViewById(R.id.stopButton);
        progressBar = (ProgressBar) view.findViewById(R.id.exerciseProgress);
        speedBanner = (TextView) view.findViewById(R.id.speedBanner);
        speedSlider = (SeekBar) view.findViewById(R.id.speedSlider);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickStop();
            }
        });

        // Initialize GUI
        setupUI();
        remainingTime = 0;
        lastTime = 0;
        seconds = 0;

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
        HashMap h = new HashMap();
        h.put("id", difToggle.getId());
        h.put("value", b);
        mListener.onFragmentInteraction(h);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.startButton:
                String state = "";
                HashMap h = new HashMap();
                h.put("id", startButton.getId());
                if (startBanner.getText().equals("Start")) {
                    // begin countdown sequence
                    difToggle.setEnabled(false);
                    timer.setEnabled(false);
                    speedSlider.setEnabled(false);
                    startButton.setEnabled(false);
                    seconds = 3;
                    new CountDownTimer(4000, 1200) {
                        HashMap h = new HashMap();
                        public void onTick(long millisUntilFinished) {
                            startBanner.setText(Integer.toString(seconds));
                            seconds--;
                            h.put("id", startButton.getId());
                            h.put("state", "intro");
                            mListener.onFragmentInteraction(h);
                        }

                        public void onFinish() {
                            // Begin exercise sequence
                            startBanner.setText("Pause");
                            startButton.setEnabled(true);
                            h.put("id", startButton.getId());
                            h.put("state", "commands");
                            mListener.onFragmentInteraction(h);
                            lastTime = timer.getValue() * 60000;
                            exerciseSequence(lastTime);
                        }
                    }.start();
                } else if (startBanner.getText().equals("Pause")) {
                    h.put("state", "paused");
                    startBanner.setText("Resume");
                    stopButton.setEnabled(true);
                    stopButton.setVisibility(View.VISIBLE);
                    if (countDownTimer != null) {
                        countDownTimer.cancel();
                    }
                } else if (startBanner.getText().equals("Resume")) {
                    // Resume exercise sequence
                    startBanner.setText("Pause");
                    exerciseSequence(remainingTime);
                }
                break;
        }
    }

    public void onClickStop() {
        difToggle.setEnabled(true);
        timer.setEnabled(true);
        speedSlider.setEnabled(true);
        startButton.setEnabled(true);
        startBanner.setText("Start");
        stopButton.setEnabled(false);
        stopButton.setVisibility(View.INVISIBLE);
        if (progressBar.getProgress() > 24) {
            HashMap s = new HashMap();
            HashMap data = new HashMap();
            s.put("id", stopButton.getId());
            s.put("state", "stopped");
            data.put("time", (int) ((double)((timer.getValue() * 60000) - remainingTime) / 1000));
            data.put("difficulty", difToggle.isChecked() ? 2 : 1);
            data.put("speed", speedSlider.getProgress());
            s.put("data", data);
            remainingTime = 0;
            lastTime = 0;
            seconds = 0;
            mListener.onFragmentInteraction(s);
        }
        progressBar.setProgress(0);
    }

    @Override
    public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
        HashMap h = new HashMap();
        h.put("id", numberPicker.getId());
        h.put("value", newVal);
        mListener.onFragmentInteraction(h);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        HashMap h = new HashMap();
        h.put("id", seekBar.getId());
        h.put("value", seekBar.getProgress());
        mListener.onFragmentInteraction(h);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(HashMap h);
    }

    public void setupUI() {
        difBanner.setText(R.string.dif_banner);
        if (goals.get("difficulty") == 1) {
            difToggle.setChecked(false);
        }
        else if (goals.get("difficulty") == 2) {
            difToggle.setChecked(true);
        }
        timer.setMaxValue(15);
        timer.setMinValue(5);
        timer.setValue(goals.get("time") / 60);
        startBanner.setText(R.string.start);
        progressBar.setProgress(0);
        progressBar.setMax(100); // Progress bar tracks seconds
        speedSlider.setMax(10);
        speedSlider.setProgress(goals.get("speed"));

        Profile p = (Profile) getArguments().getSerializable("data");
        startBanner.setTextSize(1, (int)p.getSettings().get("textSize"));
        difBanner.setTextSize(1, (int)p.getSettings().get("textSize"));
        startBanner.setTextSize(1, (int)p.getSettings().get("textSize"));
        speedBanner.setTextSize(1, (int)p.getSettings().get("textSize"));
        difToggle.setTextSize(1, (int)p.getSettings().get("textSize"));
    }

    public void exerciseSequence(int mill) {
        countDownTimer = new CountDownTimer(mill, 1000) {
            HashMap h = new HashMap();
            int fullTime = timer.getValue() * 60000;
            double voiceSteps = (int)(4000 + 4000 * ((5 - (double)(speedSlider.getProgress())) / 10));
            double progress = 0;
            public void onTick(long millisUntilFinished) {
                progress = (((double) millisUntilFinished - (double) fullTime) / fullTime) * (-100);
                remainingTime = (int) millisUntilFinished;
                progressBar.setProgress((int)progress);
                if (lastTime - millisUntilFinished >= voiceSteps) {
                    h.put("id", startButton.getId());
                    h.put("state", "commands");
                    mListener.onFragmentInteraction(h);
                    lastTime = (int) millisUntilFinished;
                }
            }
            public void onFinish() {
                // finished exercise
                difToggle.setEnabled(true);
                timer.setEnabled(true);
                speedSlider.setEnabled(true);
                startButton.setEnabled(true);
                startBanner.setText("Start");
                HashMap h = new HashMap();
                h.put("id", startButton.getId());
                h.put("state", "outro");
                remainingTime = 0;
                lastTime = 0;
                seconds = 0;
            }
        }.start();
    }
}
