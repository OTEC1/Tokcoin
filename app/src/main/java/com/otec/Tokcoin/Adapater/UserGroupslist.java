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
import com.otec.Tokcoin.Retrofit_.Request;
import com.otec.Tokcoin.model.models;
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


    private int n, p = 0;
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_cards, parent, false);
        return new CustomerAdapter(v);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull CustomerAdapter holder, int position) {
        p++;
        if (new utilJava().Change(p, holder.status) == 3)
            p = 0;
        holder.members.setText("Users " + Arrays.asList(FORMAT("members_emails", obj, position).split(",")).size() + "/" + (int) Double.parseDouble(FORMAT("liquidator_size", obj, position)));

        if(Arrays.asList(FORMAT("members_emails", obj, position).split(",")).size() == (int) Double.parseDouble(FORMAT("liquidator_size", obj, position)))
            holder.status.setText("Live");
        else
            holder.status.setText("Pending");

        if (n == 1) {
            holder.button.setVisibility(View.VISIBLE);
            holder.groupName.setText(FORMAT("groupName", obj, position));
            holder.liquidity.setText("Funds " + FORMAT("liquidity", obj, position));
            holder.loss.setText("loss -" + FORMAT("loss", obj, position));
            holder.profit.setText("Roi +" + FORMAT("profit", obj, position));
            holder.members.setText("Users " + Arrays.asList(FORMAT("members_emails", obj, position).split(",")).size() + "/" + (int) Double.parseDouble(FORMAT("liquidator_size", obj, position)));

            holder.button.setOnClickListener(e -> {
                request_leave(e.getContext(), FORMAT("doc_id", obj, position));
            });


        } else {
            holder.groupName.setText(FORMAT("groupName", obj, position));
            holder.liquidity.setText("Funds " + FORMAT("liquidity", obj, position));
            holder.profit.setText("Roi +" + FORMAT("profit", obj, position));
            holder.loss.setText("loss -" + FORMAT("loss", obj, position));

            holder.button.setOnClickListener(e -> {
                request_withdraw(e.getContext(), FORMAT("ref_id", obj, position));
            });
        }

    }




    @Override
    public int getItemCount() {
        return obj.size();
    }





    private void request_withdraw(Context context, String ref_id) {
        new utilKotlin().message2("Group dispatch"+ref_id, context);
    }






    private void request_leave(Context n, String ref_id) {
        new utilKotlin().message2("cancel dispatch"+ref_id, context);
//        Request config = Base_config.getRetrofit().create(Request.class);
//        Call<Map<String, Object>> isFunded = config.LEAVE(new utilJava().LEFT_GROUP(new utilJava().GET_CACHED_MAP(n, n.getString(R.string.SIGNED_IN_USER)), ref_id));
//        isFunded.enqueue(new Callback<Map<String, Object>>() {
//            @Override
//            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
//                if (response.body() != null)
//                    new utilKotlin().message2(response.body().get("message").toString(), n);
//                else
//                    new utilKotlin().message2("Error occurred ", n);
//            }
//
//            @Override
//            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
//                new utilKotlin().message2(t.getMessage(), n);
//            }
//        });
    }


    private String FORMAT(String node, List<Map<String, Object>> obj, int p) {
        Map<String, Object> i = (Map<String, Object>) obj.get(p).get("User");
        return i.get(node).toString();
    }


    class CustomerAdapter extends RecyclerView.ViewHolder {
        private TextView groupName, profit, loss, liquidity, members;
        private Button button, status;

        public CustomerAdapter(@NonNull @NotNull View view) {
            super(view);
            groupName = view.findViewById(R.id.groupName);
            profit = view.findViewById(R.id.profit);
            loss = view.findViewById(R.id.loss);
            liquidity = view.findViewById(R.id.liquidity);
            members = view.findViewById(R.id.members);
            button = view.findViewById(R.id.requestBtn);
            status = view.findViewById(R.id.status);
        }
    }
}
