package com.otec.crevatech.Adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.otec.crevatech.R;
import com.otec.crevatech.utils.utilKotlin;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class Vouches  extends RecyclerView.Adapter<Vouches.Custom_Adapter> {


    private List<Map<String,Object>> obj;
    private Context context;


    public Vouches(List<Map<String, Object>> obj, Context context) {
        this.obj = obj;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public Custom_Adapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.voches,parent,false);
        return new Custom_Adapter(v);
    }




    @Override
    public void onBindViewHolder(@NonNull @NotNull Custom_Adapter holder, int position) {
        holder.amount.setText(new utilKotlin().Currency(obj.get(position).get("amount").toString()));
        holder.type.setText(obj.get(position).get("mode").toString());


        holder.buy.setOnClickListener(e->{

        });
    }



    @Override
    public int getItemCount() {
        return obj.size();
    }



    class Custom_Adapter extends  RecyclerView.ViewHolder{

        private TextView amount,type,buy;
        public Custom_Adapter(@NonNull @NotNull View v) {
            super(v);
            amount = v.findViewById(R.id.amount);
            type = v.findViewById(R.id.type);
            buy = v.findViewById(R.id.buy);
        }
    }
}
