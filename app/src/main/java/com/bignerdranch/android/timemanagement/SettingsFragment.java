package com.bignerdranch.android.timemanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by mitchellhayward on 11/10/17.
 */

public class SettingsFragment extends Fragment {

    private static final String ARG_SETTINGS_ID = "settings_id";

    private Time mTime;

    private EditText mNameField;
    private EditText mIdField;
    private EditText mEmailField;
    private EditText mCommentField;

    private Spinner mSpinner;

    private Button mFinish;

    public static SettingsFragment newInstance(UUID timeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_SETTINGS_ID, timeId);

        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        UUID timeId = (UUID) getArguments().getSerializable(ARG_SETTINGS_ID);

        mTime = TimeLab.get(getActivity()).getSettings(timeId);
    }

    @Override
    public void onPause(){
        super.onPause();
        TimeLab.get(getActivity()).updateSettings(mTime);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.settings_fragment, container, false);

        mNameField = (EditText)v.findViewById(R.id.fragment_settings_name);
        mNameField.setText(mTime.getName());
        mNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTime.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mIdField = (EditText)v.findViewById(R.id.settings_id);
        mIdField.setText(mTime.getIdentifier());
        mIdField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTime.setIdentifier(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEmailField = (EditText)v.findViewById(R.id.settings_email);
        mEmailField.setText(mTime.getEmail());
        mEmailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTime.setEmail(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mSpinner = (Spinner)v.findViewById(R.id.settings_spinner);

        mSpinner.setSelection(mTime.getSettingsSpinner());

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                mTime.setSettingsSpinner(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mCommentField = (EditText)v.findViewById(R.id.settings_comment);
        mCommentField.setText(mTime.getSettingsComment());
        mCommentField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTime.setSettingsComment(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mFinish = (Button)v.findViewById(R.id.settings_done);
        mFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast completeToast = Toast.makeText(getActivity(), "Saving Your Settings...", Toast.LENGTH_SHORT);
                completeToast.show();
                getActivity().finish();
            }
        });

        return v;

    }

}
