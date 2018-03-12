package com.ua.ganoncannon.balance;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class Configs extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private HashMap settings;

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

        sizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                HashMap h = new HashMap();
                h.put("fontSize", i);
                h.put("id", sizeBar.getId());
                mListener.onFragmentInteraction(h);
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
        loading = false;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(HashMap h);
    }
}
