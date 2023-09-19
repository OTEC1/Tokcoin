package com.otec.Tokcoin.UI;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.otec.Tokcoin.R;
import com.otec.Tokcoin.Retrofit_.Base_config;
import com.otec.Tokcoin.Retrofit_.Request;
import com.otec.Tokcoin.utils.utilJava;
import com.otec.Tokcoin.utils.utilKotlin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Group_creation extends Fragment {


    private Button group_create;
    private ImageView choose;
    private CircleImageView groups_icons;
    private EditText groupName, Amount, Liquidator_size, miner_stake;
    private ProgressBar spinners;
    private Spinner spinner;


    private ArrayAdapter array;
    private List<Object> odds;
    private String TAG = "Group_creation";
    private String odd;
    private boolean selected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_creation, container, false);
        group_create = view.findViewById(R.id.group_create);
        groupName = view.findViewById(R.id.groupNames);
        Amount = view.findViewById(R.id.Amount);
        Liquidator_size = view.findViewById(R.id.Liquidator_size);
        miner_stake = view.findViewById(R.id.miner_stake);
        spinners = view.findViewById(R.id.spinners);
        spinner = view.findViewById(R.id.odds);
        choose = view.findViewById(R.id.choose);
        groups_icons = view.findViewById(R.id.groups_icons);
        AddToSpinner(populateOdds());



        if(getArguments().getInt("node") != 0)
            groups_icons.setImageResource(getArguments().getInt("node"));

        choose.setOnClickListener(e -> {
            new utilJava().openFragment(new Avater(), "Avatar", 1, (AppCompatActivity) getContext());
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.parseColor("#D6D6D6"));
                odd = (parent.getItemAtPosition(position).toString());
                selected = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        group_create.setOnClickListener(e -> {
            if (Val(groupName, Amount, Liquidator_size, miner_stake) && selected) {
                if (odd != null)
                    if (!odd.trim().isEmpty() && !odd.equals("Select Odd"))
                         if(new utilJava().init(requireContext()).getInt("icons", 0) != 0)
                             Check();
                         else
                             new utilKotlin().message2("Pls select an avatar !", getContext());
                    else
                        new utilKotlin().message2("Pls fill select a valid odd !", getContext());
            } else
                new utilKotlin().message2("Pls fill out all fields !", getContext());
        });
        return view;
    }

    private void AddToSpinner(List<Object> body) {
        array = new ArrayAdapter(getContext(), R.layout.text_pad, body);
        array.setDropDownViewResource(R.layout.text);
        spinner.setAdapter(array);
    }

    private List<Object> populateOdds() {
        odds = new ArrayList<>();
        odds.add("Select Odd");
        odds.add(2.5);
        odds.add(3.5);
        odds.add(5.5);
        odds.add(7.5);
        odds.add(9.5);
        odds.add(12.5);
        return odds;
    }

    private boolean Val(EditText groupName, EditText threshold, EditText liquidator_stake, EditText miner_stake) {
        return groupName.getText().toString().trim().length() > 0 && threshold.getText().toString().trim().length() > 0 && liquidator_stake.getText().toString().trim().length() > 0 && miner_stake.getText().toString().trim().length() > 0;
    }

    private void Check() {
        spinners.setVisibility(View.VISIBLE);
        Request request_class = Base_config.getRetrofit().create(Request.class);
        Call<Map<String, Object>> isFunded = request_class.SendGroupRequest(new utilJava().GET_GROUP(new utilJava().GET_CACHED_MAP(getContext(), getString(R.string.SIGNED_IN_USER)), groupName, Amount, Liquidator_size, miner_stake, Double.parseDouble(odd),new utilJava().init(requireContext()).getInt("icons", 0)));
        isFunded.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                new utilKotlin().message2(response.body().get("message").toString(), getContext());
                  spinners.setVisibility(View.INVISIBLE);
                    if (response.code() == 200) {
                        groupName.setText("");
                        Amount.setText("");
                        Liquidator_size.setText("");
                        miner_stake.setText("");
                        new utilJava().init(requireContext()).edit().putInt("icons", 0).apply();
                    }
            }
            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), getContext());
                Log.d(TAG, "onFailure:  " + t.getMessage());
                spinners.setVisibility(View.INVISIBLE);
            }
        });

    }
}