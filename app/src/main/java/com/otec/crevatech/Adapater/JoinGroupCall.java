package com.otec.crevatech.Adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.otec.crevatech.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class JoinGroupCall extends RecyclerView.Adapter<JoinGroupCall.CustomAdapater> {

    private List<Map<String,Object>> objList;
    private Context context;


    public JoinGroupCall(List<Map<String, Object>> objList, Context context) {
        this.objList = objList;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public JoinGroupCall.CustomAdapater onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.gcards,parent,false);
        return new CustomAdapater(v);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull JoinGroupCall.CustomAdapater holder, int position) {

    }


    @Override
    public int getItemCount() {
        return objList.size();
    }


    class CustomAdapater extends  RecyclerView.ViewHolder{

        private TextView groupName,profit,loss,liquidity,members;
        private Button btn;
        public CustomAdapater(@NonNull @NotNull View v) {
            super(v);
             groupName = v.findViewById(R.id.groupName);
             btn = v.findViewById(R.id.requestBtn);
             profit = v.findViewById(R.id.profit);
             loss = v.findViewById(R.id.loss);
             liquidity = v.findViewById(R.id.liquidity);
             members = v.findViewById(R.id.members);
        }
    }
}
