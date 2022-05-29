package com.otec.crevatech.Adapater;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Digits_Call  extends   RecyclerView.Adapter<Digits_Call.Custom_adapter>  {

    private List<Integer> numbers;
    private Context context;
    private Button button;


    public Digits_Call(List<Integer> numbers, Context context, Button button) {
        this.numbers = numbers;
        this.context = context;
        this.button = button;
    }

    @NonNull
    @NotNull
    @Override
    public Digits_Call.Custom_adapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Digits_Call.Custom_adapter holder, int position) {

    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }


    class Custom_adapter extends RecyclerView.ViewHolder{

        public Custom_adapter(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
