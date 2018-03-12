package com.ua.ganoncannon.balance;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class Help extends DialogFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Profile prof;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View view;

    private GifImageView sitGif;
    private TextView helpBanner;
    private TextView setupBanner;
    private TextView setupText;
    private TextView setupPicBanner;
    private TextView sittingBanner;
    private TextView setupText2;
    private TextView gifBanner;
    private Button closeButton;

    public Help() {
        // Required empty public constructor
    }

    public static Help newInstance(Profile param1, String param2) {
        Help fragment = new Help();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            prof = (Profile) getArguments().getSerializable(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_help, container, false);
        helpBanner = (TextView) view.findViewById(R.id.helpBanner);
        setupBanner = (TextView) view.findViewById(R.id.setupBanner);
        setupText = (TextView) view.findViewById(R.id.setupText);
        setupPicBanner = (TextView) view.findViewById(R.id.setupPicBanner);
        sittingBanner = (TextView) view.findViewById(R.id.sittingBanner);
        setupText2 = (TextView) view.findViewById(R.id.setupText2);
        gifBanner = (TextView) view.findViewById(R.id.gifBanner);
        closeButton = (Button) view.findViewById(R.id.button);
        sitGif = (GifImageView) view.findViewById(R.id.gifImageView);

        helpBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        setupUI();

        // Inflate the layout for this fragment
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
        sitGif.setGifImageResource(R.drawable.giphy);

        helpBanner.setTextSize(1, (int)prof.getSettings().get("textSize"));
        setupBanner.setTextSize(1, (int)prof.getSettings().get("textSize") - 2);
        setupText.setTextSize(1, (int)prof.getSettings().get("textSize") - 5);
        setupPicBanner.setTextSize(1, (int)prof.getSettings().get("textSize") - 6);
        sittingBanner.setTextSize(1, (int)prof.getSettings().get("textSize") - 2);
        setupText2.setTextSize(1, (int)prof.getSettings().get("textSize") - 5);
        gifBanner.setTextSize(1, (int)prof.getSettings().get("textSize") - 6);
        closeButton.setTextSize(1, (int)prof.getSettings().get("textSize") - 2);
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(HashMap h);
    }
}
