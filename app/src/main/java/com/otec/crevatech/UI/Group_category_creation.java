package com.otec.crevatech.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.otec.crevatech.R;
import com.otec.crevatech.utils.utilKotlin;

import java.util.ArrayList;
import java.util.List;


public class Group_category_creation extends Fragment {

    private Button sports, music, Vehicles, politics, history, science, religion, general, digit, golive;
    private ProgressBar spinnerload;


    private List<String> listOfCategory;
    private String TAG = "Group_category_creation";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_group_category_creation, container, false);


        sports = v.findViewById(R.id.sport);
        music = v.findViewById(R.id.music);
        Vehicles = v.findViewById(R.id.Vehicles);
        politics = v.findViewById(R.id.politics);
        history = v.findViewById(R.id.history);
        science = v.findViewById(R.id.science);
        religion = v.findViewById(R.id.religion);
        general = v.findViewById(R.id.general);
        digit = v.findViewById(R.id.digit);
        golive = v.findViewById(R.id.golive);
        spinnerload = v.findViewById(R.id.spinnerload);
        listOfCategory = new ArrayList<>();
        sports.setOnClickListener(e -> {
            if (!listOfCategory.contains(sports.getText().toString())) {
                listOfCategory.add(sports.getText().toString());
                sports.setBackgroundResource(R.drawable.inputlines1);
            }
        });
        music.setOnClickListener(e -> {
            if (!listOfCategory.contains(music.getText().toString())) {
                listOfCategory.add(music.getText().toString());
                music.setBackgroundResource(R.drawable.inputlines1);
            }
        });
        Vehicles.setOnClickListener(e -> {
            if (!listOfCategory.contains(Vehicles.getText().toString())) {
                listOfCategory.add(Vehicles.getText().toString());
                Vehicles.setBackgroundResource(R.drawable.inputlines1);
            }
        });
        politics.setOnClickListener(e -> {
            if (!listOfCategory.contains(politics.getText().toString())) {
                listOfCategory.add(politics.getText().toString());
                politics.setBackgroundResource(R.drawable.inputlines1);
            }
        });
        history.setOnClickListener(e -> {
            if (!listOfCategory.contains(history.getText().toString())) {
                listOfCategory.add(history.getText().toString());
                history.setBackgroundResource(R.drawable.inputlines1);
            }
        });
        science.setOnClickListener(e -> {
            if (!listOfCategory.contains(science.getText().toString())) {
                listOfCategory.add(science.getText().toString());
                science.setBackgroundResource(R.drawable.inputlines1);
            }
        });
        religion.setOnClickListener(e -> {
            if (!listOfCategory.contains(religion.getText().toString())) {
                listOfCategory.add(religion.getText().toString());
                religion.setBackgroundResource(R.drawable.inputlines1);
            }
        });
        general.setOnClickListener(e -> {
            if (!listOfCategory.contains(general.getText().toString())) {
                listOfCategory.add(general.getText().toString());
                general.setBackgroundResource(R.drawable.inputlines1);
            }
        });
        digit.setOnClickListener(e -> {
            if (!listOfCategory.contains(digit.getText().toString())) {
                listOfCategory.add(digit.getText().toString());
                digit.setBackgroundResource(R.drawable.inputlines1);
            }
        });


        golive.setOnClickListener(e -> {
            if (listOfCategory.size() <= 3)
                new utilKotlin().message2("Pls select at least 3 category  ", getActivity());
            else
                //Check for null in documents call
                Next();
        });

        return v;
    }

    private void Next() {
    }

}