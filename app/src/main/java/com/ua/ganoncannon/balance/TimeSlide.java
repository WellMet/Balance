package com.ua.ganoncannon.balance;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

public class TimeSlide extends Fragment {

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";
    private int layoutResId;
    private OnFragmentInteractionListener mListener;
    private NumberPicker min;
    private NumberPicker sec;
    View view;

    public static TimeSlide newInstance(int layoutResId) {
        TimeSlide WeekSlide = new TimeSlide();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        WeekSlide.setArguments(args);

        return WeekSlide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID)) {
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_time_slide, container, false);
        min = (NumberPicker) view.findViewById(R.id.gMinute);
        sec = (NumberPicker) view.findViewById(R.id.gSecond);

        min.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                if (newVal == 15) {
                    sec.setMaxValue(0);
                } else {
                    sec.setMaxValue(59);
                }
                mListener.onTimeInteraction(((newVal * 60) + sec.getValue()));
            }
        });

        sec.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                mListener.onTimeInteraction((i1 + (min.getValue() * 60)));
            }
        });

        min.setMaxValue(15);
        min.setMinValue(5);
        min.setValue(5);
        sec.setMaxValue(59);
        sec.setMinValue(0);
        sec.setValue(0);

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

    public interface OnFragmentInteractionListener {
        void onTimeInteraction(int i);
    }
}