package com.otec.Tokcoin.Adapater;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.otec.Tokcoin.R;
import com.otec.Tokcoin.Retrofit_.Base_config;
import com.otec.Tokcoin.Retrofit_.Request;
import com.otec.Tokcoin.UI.Auto;
import com.otec.Tokcoin.utils.utilJava;
import com.otec.Tokcoin.utils.utilKotlin;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.otec.Tokcoin.utils.utilJava.FORMAT;

public class JoinGroupCall extends RecyclerView.Adapter<JoinGroupCall.Custom_adapter> {

    private List<Map<String, Object>> objList;
    private Context context;


    private int call, p = 0;
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


        p++;
        if (new utilJava().Change(p, holder.status) == 2)
            p = 0;


        holder.groupName.setText(" " + Upper(FORMAT("groupName", objList, position)));
        holder.members.setText("Users " + Arrays.asList(FORMAT("members_emails", objList, position).split(",")).size() + "/" + (int) Double.parseDouble(FORMAT("liquidator_size", objList, position)));


        if (call != 2) {
            new utilJava().Wigdet_Check(holder.groupIcon,holder.userIcon,objList,position);
            holder.miner_stake.setText(" Gas " + new utilKotlin().cast(FORMAT("miner_stake", objList, position)));
            holder.profit.setText(" Odd " + FORMAT("odd", objList, position));
            holder.status.setText("Live");
            Glide.with(holder.user_avatar.getContext()).load(new utilKotlin().cast(FORMAT("avatar", objList, position)) == 0 ? R.drawable.user : new utilKotlin().cast(FORMAT("avatar", objList, position))).into(holder.user_avatar);
            Glide.with(holder.guest_avatar.getContext()).load((new utilKotlin().cast(FORMAT("guest_avatar", objList, position)) == 0 ? R.drawable.user : new utilKotlin().cast(FORMAT("guest_avatar", objList, position)))).into(holder.guest_avatar);


            holder.self.setOnClickListener(e->{
                new utilKotlin().Message((Activity) e.getContext(), FORMAT("doc_id", objList, position));
            });

            holder.auto_ai.setOnClickListener(e -> {
                new utilJava().open_Fragment(new Auto(), "Auto", e, new utilJava().BUNDLE(1, FORMAT("doc_id", objList, position), FORMAT("email", objList, position)), R.id.frameLayout);
            });

        } else {

            new utilJava().Wigdet_Check(holder.groupIcons,holder.userIcons,objList,position);
            holder.loss.setText("loss -" + FORMAT("loss", objList, position));
            holder.profit.setText("Roi +" + FORMAT("profit", objList, position));
            holder.liquidity.setText(" Funds " + FORMAT("liquidity", objList, position));
            holder.status.setText("Pending");



            holder.btn.setOnClickListener(e -> {

            });


            holder.btn2.setOnClickListener(e -> {
                Request request_class = Base_config.getRetrofit().create(Request.class);
                Call<Map<String, Object>> obj = request_class.JOIN_REQUEST(new utilJava().sendrequest(new utilJava().GET_CACHED_MAP(e.getContext(), e.getContext().getString(R.string.SIGNED_IN_USER)), FORMAT("doc_id", objList, position), FORMAT("email", objList, position)));
                obj.enqueue(new Callback<Map<String, Object>>() {
                    @Override
                    public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                        new utilKotlin().message2(response.body().get("message").toString(), e.getContext());
                    }

                    @Override
                    public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                        new utilKotlin().message2(t.getLocalizedMessage(), holder.btn.getContext());
                    }
                });

            });
        }


    }



    private String Upper(String groupName) {
        return groupName.substring(0, 1).toUpperCase().concat(groupName.substring(1));
    }


    @Override
    public int getItemCount() {
        return objList.size();
    }


    class Custom_adapter extends RecyclerView.ViewHolder {

        private TextView groupName, profit, liquidity, miner_stake, members, loss, groupIcon,userIcon,self,userIcons,groupIcons;
        private Button btn, btn2, status;
        private RelativeLayout auto_ai;
        private CircleImageView user_avatar, guest_avatar;

        public Custom_adapter(@NonNull @NotNull View v) {
            super(v);
            groupName = v.findViewById(R.id.groupName);
            btn = v.findViewById(R.id.requestBtn);
            btn2 = v.findViewById(R.id.requestBtn2);
            profit = v.findViewById(R.id.profit);
            members = v.findViewById(R.id.members);
            loss = v.findViewById(R.id.loss);
            auto_ai = v.findViewById(R.id.auto_ai);
            status = v.findViewById(R.id.status);
            user_avatar = v.findViewById(R.id.userAvatar);
            guest_avatar = v.findViewById(R.id.guest_avatar);
            groupIcon = v.findViewById(R.id.group_icon);
            userIcon = v.findViewById(R.id.user_icon);
            userIcons = v.findViewById(R.id.user_icons);
            groupIcons = v.findViewById(R.id.group_icons);
            self = v.findViewById(R.id.self);

            if (call == 2) {
                btn2.setVisibility(View.VISIBLE);
                btn.setVisibility(View.INVISIBLE);
                liquidity = v.findViewById(R.id.liquidity);
            } else
                miner_stake = v.findViewById(R.id.miner_stake);
        }
    }
}

//Get first node in list
//Arrays.asList(FORMAT("members_emails", objList, position).split(",")).get(0))