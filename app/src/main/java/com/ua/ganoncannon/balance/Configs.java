package com.ua.ganoncannon.balance;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;


public class Configs extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private HashMap settings;
    private HashMap goals;

    private TextView sizeBanner;
    private SeekBar sizeBar;
    private TextView objBanner;
    private TextView obj1Banner;
    private TextView obj2Banner;
    private TextView obj3Banner;
    private EditText obj1Edit;
    private EditText obj2Edit;
    private EditText obj3Edit;
    private TextView volumeBanner;
    private SeekBar volumeBar;
    private TextView goalBanner;
    private TextView attemptsBanner;
    private SeekBar attemptsBar;
    private TextView attemptsD;
    private TextView timeBanner;
    private NumberPicker sMin;
    private NumberPicker sSec;
    private TextView timeD;
    private TextView difBanner;
    private ToggleButton difButton;
    private TextView difD;
    private TextView speedBanner;
    private SeekBar speedBar;
    private TextView speedD;
    private boolean loading = true;

    public View view;

    private OnFragmentInteractionListener mListener;

    public Configs() {
        // Required empty public constructor
    }

    public static Configs newInstance(HashMap param1) {
        Configs fragment = new Configs();
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
            settings = p.getSettings();
            goals = p.getGoals();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_settings, container, false);

        sizeBanner = (TextView) view.findViewById(R.id.fontSizeLabel);
        objBanner = (TextView) view.findViewById(R.id.objectMain);
        obj1Banner = (TextView) view.findViewById(R.id.obj1);
        obj2Banner = (TextView) view.findViewById(R.id.obj2);
        obj3Banner = (TextView) view.findViewById(R.id.obj3);
        volumeBanner = (TextView) view.findViewById(R.id.volumeLabel);
        obj1Edit = (EditText) view.findViewById(R.id.obj1Edit);
        obj2Edit = (EditText) view.findViewById(R.id.obj2Edit);
        obj3Edit = (EditText) view.findViewById(R.id.obj3Edit);
        sizeBar = (SeekBar) view.findViewById(R.id.fontSizeBar);
        volumeBar = (SeekBar) view.findViewById(R.id.volumeBar);
        goalBanner = (TextView) view.findViewById(R.id.goalLabel);
        attemptsBanner = (TextView) view.findViewById(R.id.freqBanner2);
        attemptsBar = (SeekBar) view.findViewById(R.id.fBar);
        attemptsD = (TextView) view.findViewById(R.id.freqDetails2);
        difBanner = (TextView) view.findViewById(R.id.textView9);
        difButton = (ToggleButton) view.findViewById(R.id.difToggle2);
        difD = (TextView) view.findViewById(R.id.textView10);
        timeBanner = (TextView) view.findViewById(R.id.textView15);
        sMin = (NumberPicker) view.findViewById(R.id.sMinute);
        sSec = (NumberPicker) view.findViewById(R.id.sSecond);
        timeD = (TextView) view.findViewById(R.id.textView16);
        speedBanner = (TextView) view.findViewById(R.id.textView17);
        speedBar = (SeekBar) view.findViewById(R.id.freqBar2);
        speedD = (TextView) view.findViewById(R.id.textView18);

        difButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!loading) {
                    HashMap h = new HashMap();
                    h.put("value", b);
                    h.put("id", difButton.getId());
                    mListener.onFragmentInteraction(h);
                }
            }
        });

        obj1Edit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!loading) {
                    HashMap h = new HashMap();
                    if (obj1Edit.getText().toString().equals(""))
                        h.put("object", obj1Edit.getHint().toString());
                    else
                        h.put("object", obj1Edit.getText().toString());
                    h.put("index", 0);
                    h.put("id", objBanner.getId());
                    mListener.onFragmentInteraction(h);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        obj2Edit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!loading) {
                    HashMap h = new HashMap();
                    if (obj2Edit.getText().toString().equals(""))
                        h.put("object", obj2Edit.getHint().toString());
                    else
                        h.put("object", obj2Edit.getText().toString());
                    h.put("index", 1);
                    h.put("id", objBanner.getId());
                    mListener.onFragmentInteraction(h);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        obj3Edit.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (!loading) {
                    HashMap h = new HashMap();
                    if (obj3Edit.getText().toString().equals(""))
                        h.put("object", obj3Edit.getHint().toString());
                    else
                        h.put("object", obj3Edit.getText().toString());
                    h.put("index", 2);
                    h.put("id", objBanner.getId());
                    mListener.onFragmentInteraction(h);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        attemptsBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (!loading) {
                    HashMap h = new HashMap();
                    h.put("value", i + 1);
                    h.put("id", attemptsBar.getId());
                    mListener.onFragmentInteraction(h);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if (!loading) {
                    HashMap h = new HashMap();
                    h.put("id", numberPicker.getId());
                    h.put("value", ((i1 * 60) + sSec.getValue()));
                    if (i1 == 15) {
                        sSec.setMaxValue(0);
                    } else {
                        sSec.setMaxValue(59);
                    }
                    mListener.onFragmentInteraction(h);
                }
            }
        });

        sSec.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if (!loading) {
                    HashMap h = new HashMap();
                    h.put("id", sMin.getId());
                    h.put("value", (i1 + (sMin.getValue() * 60)));
                    mListener.onFragmentInteraction(h);
                }
            }
        });

        speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (!loading) {
                    HashMap h = new HashMap();
                    h.put("id", seekBar.getId());
                    h.put("value", seekBar.getProgress());
                    mListener.onFragmentInteraction(h);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (!loading) {
                    HashMap h = new HashMap();
                    h.put("fontSize", i);
                    h.put("id", sizeBar.getId());
                    mListener.onFragmentInteraction(h);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setupUI();
            }
        });

        volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (!loading) {
                    HashMap h = new HashMap();
                    h.put("volume", i);
                    h.put("id", volumeBar.getId());
                    mListener.onFragmentInteraction(h);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

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

    private void setupUI() {
        ArrayList<String> objects = (ArrayList<String>) settings.get("objects");
        sizeBanner.setTextSize(1, (int)settings.get("textSize"));
        obj1Banner.setTextSize(1, (int)settings.get("textSize"));
        obj2Banner.setTextSize(1, (int)settings.get("textSize"));
        obj3Banner.setTextSize(1, (int)settings.get("textSize"));
        obj1Edit.setTextSize(1, (int)settings.get("textSize"));
        obj2Edit.setTextSize(1, (int)settings.get("textSize"));
        obj3Edit.setTextSize(1, (int)settings.get("textSize"));
        objBanner.setTextSize(1, (int)settings.get("textSize"));
        volumeBanner.setTextSize(1, (int)settings.get("textSize"));
        sizeBar.setProgress((int)settings.get("textSize") - 15);
        obj1Edit.setText(objects.get(0));
        obj2Edit.setText(objects.get(1));
        obj3Edit.setText(objects.get(2));
        volumeBar.setMax((int)settings.get("maxVolume"));
        volumeBar.setProgress((int)settings.get("volume"));

        goalBanner.setTextSize(1, (int)settings.get("textSize"));

        difBanner.setTextSize(1, (int)settings.get("textSize") - 2);
        difD.setTextSize(1, (int)settings.get("textSize") - 4);
        difButton.setTextSize(1, (int)settings.get("textSize"));
        boolean checked = false;
        if ((int)goals.get("difficulty") == 2) {
            checked = true;
        }
        difButton.setChecked(checked);

        attemptsBanner.setTextSize(1, (int)settings.get("textSize") - 2);
        attemptsD.setTextSize(1, (int)settings.get("textSize") - 4);
        attemptsBar.setMax(6);
        attemptsBar.setProgress((int)goals.get("attempts") - 1);

        timeBanner.setTextSize(1, (int)settings.get("textSize") - 2);
        timeD.setTextSize(1, (int)settings.get("textSize") - 4);
        int sec = (int)goals.get("time") % 60;
        int min = ((int)goals.get("time") - sec) / 60;
        System.out.println("Setup min: " + min + ", setup sec: " + sec);
        sMin.setMaxValue(15);
        sMin.setMinValue(5);
        sMin.setValue(min);
        sSec.setMaxValue(59);
        sSec.setMinValue(0);
        sSec.setValue(sec);

        speedBanner.setTextSize(1, (int)settings.get("textSize") - 2);
        speedD.setTextSize(1, (int)settings.get("textSize") - 4);
        speedBar.setMax(10);
        speedBar.setProgress((int)goals.get("speed"));

        loading = false;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(HashMap h);
    }
}
