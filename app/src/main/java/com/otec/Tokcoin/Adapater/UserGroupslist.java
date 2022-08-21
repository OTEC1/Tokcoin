package com.otec.Tokcoin.Adapater;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.otec.Tokcoin.R;
import com.otec.Tokcoin.Retrofit_.Base_config;
import com.otec.Tokcoin.Retrofit_.Request_class;
import com.otec.Tokcoin.utils.utilJava;
import com.otec.Tokcoin.utils.utilKotlin;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserGroupslist extends RecyclerView.Adapter<UserGroupslist.CustomerAdapter> {

    private List<Map<String, Object>> obj;
    private Context context;
    private AlertDialog alertDialog = null;
    private TextView groupName, profit, loss, liquidity, members;
    private ProgressBar progressBar;

    private int n;
    private String TAG = "UserGroupslist";

    public UserGroupslist(List<Map<String, Object>> obj, Context context, int n) {
        this.obj = obj;
        this.context = context;
        this.n = n;
    }

    @NonNull
    @NotNull
    @Override
    public CustomerAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(n == 1 ? R.layout.group_cards : R.layout.joined_group, parent, false);
        return new CustomerAdapter(v);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull CustomerAdapter holder, int position) {

        if (n == 1) {
            holder.button.setVisibility(View.VISIBLE);
            holder.groupName.setText(FORMAT("groupName", obj, position));
            holder.liquidity.setText("Funds " +new utilKotlin().Currency(FORMAT("liquidity", obj, position)));
            holder.loss.setText("loss -" + new utilKotlin().Currency(FORMAT("loss", obj, position)));
            holder.profit.setText("Roi +" + new utilKotlin().Currency(FORMAT("profit", obj, position)));
            holder.members.setText("Users " + Arrays.asList(FORMAT("members_ids", obj, position).split(",")).size() + "/" + (int) Double.parseDouble(FORMAT("liquidator_size", obj, position)));
        } else {
            holder.groupName.setText(FORMAT("groupName", obj, position));

            holder.group_stats.setOnClickListener(e -> {
                View v = LayoutInflater.from(e.getContext()).inflate(R.layout.dialog_promt, null);
                AlertDialog.Builder alBuilder = new AlertDialog.Builder(context);
                alBuilder.setView(v);
                alertDialog = alBuilder.create();
                alertDialog.show();
                RequestGroups(holder.group_stats.getContext(), v, FORMAT("doc_id", obj, position), Arrays.asList(FORMAT("members_ids", obj, position).split(",")).get(0).replace("[", ""));
            });


            holder.close_money.setOnClickListener(e -> {


            });

        }
    }


    @Override
    public int getItemCount() {
        return obj.size();
    }


    private void RequestGroups(Context context, View v, String doc_id, String found) {
        Log.d(TAG, "RequestGroups: " + found + " | " + doc_id);
        Request_class request = Base_config.getRetrofit().create(Request_class.class);
        Call<Map<String, Object>> list = request.GROUPS_STATUS(new utilJava().GET_GROUP_STATUS(new utilJava().GET_CACHED_MAP(context, context.getString(R.string.SIGNED_IN_USER)), doc_id, found));
        list.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                Map<String, Object> l1 = (Map<String, Object>) response.body().get("message");
                BuildView(l1, v);

            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), context);
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void BuildView(Map<String, Object> raw2, View v) {
        groupName = v.findViewById(R.id.groupName);
        profit = v.findViewById(R.id.profit);
        loss = v.findViewById(R.id.loss);
        liquidity = v.findViewById(R.id.liquidity);
        members = v.findViewById(R.id.members);
        progressBar = v.findViewById(R.id.spinBar);
        Map<String, Object> m = (Map<String, Object>) raw2.get("User");
        groupName.setText(m.get("groupName").toString());
        profit.setText(new utilKotlin().Currency(m.get("profit").toString()));
        liquidity.setText(new utilKotlin().Currency(m.get("liquidity").toString()));
        loss.setText(new utilKotlin().Currency(m.get("loss").toString()));
        members.setText("Users " + Arrays.asList(m.get("members_ids").toString().split(",")).size() + "/" + (int) Double.parseDouble(m.get("liquidator_size").toString()));
        progressBar.setVisibility(View.INVISIBLE);
    }


    private String FORMAT(String node, List<Map<String, Object>> obj, int p) {
        Map<String, Object> i = (Map<String, Object>) obj.get(p).get("User");
        return i.get(node).toString();
    }



    class CustomerAdapter extends RecyclerView.ViewHolder {
        private TextView groupName, profit, loss, liquidity, members;
        private Button button, close_money, group_stats;

        public CustomerAdapter(@NonNull @NotNull View view) {
            super(view);
            groupName = view.findViewById(R.id.groupName);
            profit = view.findViewById(R.id.profit);
            loss = view.findViewById(R.id.loss);
            liquidity = view.findViewById(R.id.liquidity);
            members = view.findViewById(R.id.members);
            button = view.findViewById(R.id.requestBtn);
            close_money = view.findViewById(R.id.close_money);
            group_stats = view.findViewById(R.id.group_stats);
        }
    }
}
