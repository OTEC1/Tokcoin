package com.otec.crevatech.Adapater;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.otec.crevatech.R;
import com.otec.crevatech.Retrofit_.Base_config;
import com.otec.crevatech.Retrofit_.Request_class;
import com.otec.crevatech.utils.utilJava;
import com.otec.crevatech.utils.utilKotlin;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Digits_Call extends RecyclerView.Adapter<Digits_Call.Custom_adapter> {

    private List<Double> numbers;
    private List<Integer> send_number = new ArrayList<>();
    private Iterator<Integer> send_num;
    private Context context;
    private Button button;
    private TextView rt;


    private String TAG = "Digits_Call";

    public Digits_Call(List<Double> numbers, Context context, Button button,TextView rt) {
        this.numbers = numbers;
        this.context = context;
        this.button = button;
        this.rt = rt;
    }

    @NonNull
    @NotNull
    @Override
    public Digits_Call.Custom_adapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.digits_, parent, false);
        return new Custom_adapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Digits_Call.Custom_adapter holder, int position) {
        holder.number.setText(String.valueOf(new utilKotlin().cast(numbers.get(position))));
        holder.number.setOnClickListener(e -> {
            if (!send_number.contains(new utilKotlin().cast(holder.number.getText())) && send_number.size() < 3)
                ANIMATE(1, holder);
            else if (send_number.size() >= 3)
                new utilKotlin().message2("You have reach your limit", e.getContext());
            else
                ANIMATE(2, holder);

        });


        button.setOnClickListener(e -> {
            if (send_number.size() >= 3)
                    new utilJava().LoadInstance(rt,e.getContext(),numbers,send_number,false,false,true,null,null);
                else
                    new utilKotlin().message2("Pls select at least 3 nodes", e.getContext());
            });
    }

    private void ANIMATE(int i, Custom_adapter holder) {
        if (i == 1)
            FONTS(R.drawable.inputlines1, Color.parseColor("#FFFFFF"), holder, 1);
        else {
            for (send_num = send_number.iterator(); send_num.hasNext(); ) {
                    if (send_num.next() == Integer.parseInt(holder.number.getText().toString())) {
                        FONTS(R.drawable.inputlines, Color.parseColor("#666666"), holder, 2);
                        send_num.remove();
                    }
            }
        }
    }

    private void FONTS(int input, int parseColor, Custom_adapter holder, int i) {
        if (i == 1)
            send_number.add(new utilKotlin().cast(holder.number.getText()));
          holder.number.setBackgroundResource(input);
        holder.number.setTextColor(parseColor);
    }


    @Override
    public int getItemCount() {
        return numbers.size();
    }


    class Custom_adapter extends RecyclerView.ViewHolder {

        private TextView number;

        public Custom_adapter(@NonNull @NotNull View itemView) {
            super(itemView);
            number = itemView.findViewById(R.id.number);
        }
    }
}
