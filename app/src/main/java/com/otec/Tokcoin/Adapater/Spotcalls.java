package com.otec.Tokcoin.Adapater;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.otec.Tokcoin.R;
import com.otec.Tokcoin.UI.Auto;
import com.otec.Tokcoin.utils.utilJava;
import com.otec.Tokcoin.utils.utilKotlin;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Spotcalls extends RecyclerView.Adapter<Spotcalls.Custom_adapter> {

    private List<Map<String, Object>> objList;
    private Context context;
    private String TAG = "JoinGroupCall";


    public Spotcalls(List<Map<String, Object>> objList, Context context) {
        this.objList = objList;
        this.context = context;

    }

    @NonNull
    @NotNull
    @Override
    public Custom_adapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.scards, parent, false);
        return new Custom_adapter(v);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull Custom_adapter holder, int position) {


          holder.spot_buy.setText(String.valueOf(new utilKotlin().cast(FORMAT("miner_stake", objList, position))));

            holder.spot_buy.setOnClickListener(e->{
                new utilJava().open_Fragment(new Auto(), "Auto", e,new utilJava().BUNDLE(2,FORMAT("doc_id", objList, position),FORMAT("email", objList, position)), R.id.frameLayout);
            });
    }




    @Override
    public int getItemCount() {
        return objList.size();
    }


    private String FORMAT(String node, List<Map<String, Object>> obj, int p) {
        Map<String, Object> i = (Map<String, Object>) obj.get(p).get("User");
        return i.get(node).toString();
    }


    class Custom_adapter extends RecyclerView.ViewHolder {

        private TextView spot_buy;
        private CircleImageView avater;


        public Custom_adapter(@NonNull @NotNull View v) {
            super(v);
            avater = v.findViewById(R.id.avater);
            spot_buy = v.findViewById(R.id.spot_buy);


        }
    }
}

