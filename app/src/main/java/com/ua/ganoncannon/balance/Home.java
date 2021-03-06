package com.ua.ganoncannon.balance;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Home extends Fragment implements View.OnClickListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private Data data;

    private OnFragmentInteractionListener mListener;

    // UI Components
    private TextView banner;
    private FloatingActionButton currentProgress;
    private TextView progBanner;
    private ProgressBar progress;
    private TextView quickHeader;
    private FloatingActionButton quickAction;
    private BottomNavigationView navigation;

    View view;

    public Home() {
        // Required empty public constructor
    }

    public static Home newInstance(Data param1) {
        Home fragment = new Home();
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
        view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize the GUI Elements
        navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
        banner = (TextView) view.findViewById(R.id.welcomeBanner);
        progBanner = (TextView) view.findViewById(R.id.progText);
        currentProgress = (FloatingActionButton) view.findViewById(R.id.currentProgress);
        progress = (ProgressBar) view.findViewById(R.id.progressBar);
        quickHeader = (TextView) view.findViewById(R.id.textView);
        quickAction = (FloatingActionButton) view.findViewById(R.id.quickAction);
        currentProgress.setOnClickListener(this);
        quickAction.setOnClickListener(this);
        
        // Setup Elements to correct values based on profile (history, week #)
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

    @Override
    public void onClick(View view) {
        HashMap h = new HashMap();
        switch(view.getId()) {
            case R.id.currentProgress:
                h.put("id", R.id.currentProgress);
                mListener.onFragmentInteraction(h);
                break;
            case R.id.quickAction:
                h.put("id", R.id.quickAction);
                mListener.onFragmentInteraction(h);
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(HashMap h);
    }

    public void setupUI() {
        // Load current weeks progress
        ArrayList<String> weeks = new ArrayList<String>(data.getHistory().keySet());
        if (weeks.size() > 0) {
            Collections.sort(weeks);
            progress.setProgress(data.getOverallProgress(weeks.get(weeks.size() - 1)));
            banner.setText("Week " + weeks.get(weeks.size() - 1));
        } else {
            progress.setProgress(0);
        }
        progBanner.setText(Integer.toString(progress.getProgress()) + "%");
        Profile p = (Profile) getArguments().getSerializable("data");
        banner.setTextSize(1, (int)p.getSettings().get("textSize"));
        progBanner.setTextSize(1, (int)p.getSettings().get("textSize"));
        quickHeader.setTextSize(1, (int)p.getSettings().get("textSize"));
    }

    protected void loadFragment(Fragment fragment) {
        if (fragment == null)
            return;
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.replace(R.id.rootLayout, fragment);
                ft.commit();
            }
        }
    }
}
