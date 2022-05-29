package com.otec.crevatech.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.otec.crevatech.Adapater.Digits_Call;
import com.otec.crevatech.R;

public class Botmine extends Fragment {

    private RecyclerView digits_returned;
    private Button play;


    private Digits_Call  digits_call;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_botmine, container, false);
        digits_returned = v.findViewById(R.id.digits_returned);
        play = v.findViewById(R.id.play);

        RequestDigsits();
        return v;
    }

    private void RequestDigsits() {
    }
}