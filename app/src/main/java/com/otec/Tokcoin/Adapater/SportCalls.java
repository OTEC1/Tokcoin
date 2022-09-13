package com.otec.Tokcoin.Adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportCalls extends RecyclerView.Adapter<SportCalls.Custom_adapter> {

    private List<Map<String, Object>> objList;
    private Context context;
    private String TAG = "JoinGroupCall";


    public SportCalls(List<Map<String, Object>> objList, Context context) {
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

        holder.groupName.setText(" " + Upper(FORMAT("groupName", objList, position)));
          holder.stake.setText(" Gas " + new utilKotlin().cast(FORMAT("miner_stake", objList, position)));
            holder.groupName.setOnClickListener(e -> {

            });
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

        private TextView groupName,stake;
        private CircleImageView avater;


        public Custom_adapter(@NonNull @NotNull View v) {
            super(v);
            groupName = v.findViewById(R.id.groupName);
            avater = v.findViewById(R.id.cards);
            stake = v.findViewById(R.id.stake);


        }
    }
}

//Get first node in list
//Arrays.asList(FORMAT("members_ids", objList, position).split(",")).get(0))