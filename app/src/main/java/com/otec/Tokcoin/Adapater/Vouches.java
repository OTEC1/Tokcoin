package com.otec.Tokcoin.Adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.otec.Tokcoin.R;
import com.otec.Tokcoin.utils.utilJava;
import com.otec.Tokcoin.utils.utilKotlin;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class Vouches  extends RecyclerView.Adapter<Vouches.Custom_Adapter> {


    private List<Map<String,Object>> obj;
    private Context context;
    private int e;


    public Vouches(List<Map<String, Object>> obj, Context context,int e) {
        this.obj = obj;
        this.context = context;
        this.e = e;
    }

    @NonNull
    @NotNull
    @Override
    public Custom_Adapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate( e == 0 ? R.layout.voches : R.layout.vnodes ,parent,false);
        return new Custom_Adapter(v);
    }




    @Override
    public void onBindViewHolder(@NonNull @NotNull Custom_Adapter holder, int position) {
        if(e == 0){
            holder.amount.setText(new utilKotlin().Currency(obj.get(position).get("amount").toString()));
            holder.type.setText(obj.get(position).get("mode").toString());
            holder.buy.setOnClickListener(e->{

            });
        }else{
            holder.profit_.setText("+"+new utilKotlin().cast(new utilJava().FORMAT("profit",obj,position)));
            holder.loss_.setText("-"+new utilKotlin().cast(new utilJava().FORMAT("loss",obj,position)));
            holder.nodes_.setText(""+new utilKotlin().cast(new utilJava().FORMAT("bot_size",obj,position)));
            holder.cancel_.setOnClickListener(e-> {

            });
        }
    }



    @Override
    public int getItemCount() {
        return obj.size();
    }



    class Custom_Adapter extends  RecyclerView.ViewHolder{

        private TextView amount,type,buy,profit_,loss_,nodes_,cancel_;
        public Custom_Adapter(@NonNull @NotNull View v) {
            super(v);
            amount = v.findViewById(R.id.amount);
            type = v.findViewById(R.id.type);
            buy = v.findViewById(R.id.buy);
            profit_ = v.findViewById(R.id.profit_);
            loss_ = v.findViewById(R.id.loss_);
            nodes_ = v.findViewById(R.id.nodes_);
            cancel_ = v.findViewById(R.id.cancel_);
        }
    }
}
