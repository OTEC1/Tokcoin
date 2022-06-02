package com.otec.crevatech.Adapater;

import android.content.Context;
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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinGroupCall extends RecyclerView.Adapter<JoinGroupCall.Custom_adapter> {

    private List<Map<String, Object>> objList;
    private Context context;


    private int call;
    private String TAG = "JoinGroupCall";


    public JoinGroupCall(List<Map<String, Object>> objList, Context context, int call) {
        this.objList = objList;
        this.context = context;
        this.call = call;
    }

    @NonNull
    @NotNull
    @Override
    public Custom_adapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(call == 1 ? R.layout.gcards : R.layout.group_cards, parent, false);
        return new Custom_adapter(v);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull Custom_adapter holder, int position) {

        holder.groupName.setText(" " + Upper(FORMAT("groupName", objList, position)));

        if (call != 2) {
            holder.miner_stake.setText(" Gas " + new utilKotlin().cast(FORMAT("miner_stake", objList, position)));
            holder.profit.setText(" Odd " + FORMAT("odd", objList, position));;
        }else {
            holder.members.setText(Arrays.asList(FORMAT("members_ids", objList, position).split(",")).size() + "/" + (int) Double.parseDouble(FORMAT("liquidator_size", objList, position)));
            holder.loss.setText("loss -" + FORMAT("loss", objList, position));
            holder.profit.setText("Roi +" + FORMAT("profit", objList, position));
            holder.liquidity.setText(" Funds " + FORMAT("liquidity", objList, position));
        }

        if (call == 2) {
            holder.btn.setOnClickListener(e -> {

            });

            holder.btn2.setOnClickListener(e -> {
                Request_class request_class = Base_config.getRetrofit().create(Request_class.class);
                Call<Map<String, Object>> obj = request_class.JOIN_REQUEST(new utilJava().RequestSend(new utilJava().GET_CACHED_MAP(e.getContext(), e.getContext().getString(R.string.SIGNED_IN_USER)), FORMAT("doc_id", objList, position), Arrays.asList(FORMAT("members_ids", objList, position).split(",")).get(0)));
                obj.enqueue(new Callback<Map<String, Object>>() {
                    @Override
                    public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                        new utilKotlin().message2(response.body().get("message").toString(), e.getContext());
                    }

                    @Override
                    public void onFailure(Call<Map<String, Object>> call, Throwable t) {

                    }
                });

            });
        }


    }

    private String Upper(String groupName) {
        return  groupName.substring(0,1).toUpperCase().concat(groupName.substring(1));
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

        private TextView groupName, profit, liquidity, miner_stake, members,loss;
        private Button btn, btn2;

        public Custom_adapter(@NonNull @NotNull View v) {
            super(v);
            groupName = v.findViewById(R.id.groupName);
            btn = v.findViewById(R.id.requestBtn);
            btn2 = v.findViewById(R.id.requestBtn2);
            profit = v.findViewById(R.id.profit);
            members = v.findViewById(R.id.members);
            loss = v.findViewById(R.id.loss);

            if (call == 2) {
                btn2.setVisibility(View.VISIBLE);
                btn.setVisibility(View.INVISIBLE);
                liquidity = v.findViewById(R.id.liquidity);
            } else
                miner_stake = v.findViewById(R.id.miner_stake);
        }
    }
}
