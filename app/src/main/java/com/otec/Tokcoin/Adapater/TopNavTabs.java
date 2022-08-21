package com.otec.Tokcoin.Adapater;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.otec.Tokcoin.R;
import com.otec.Tokcoin.UI.Auto;
import com.otec.Tokcoin.UI.Digits;
import com.otec.Tokcoin.UI.GroupsLists;
import com.otec.Tokcoin.UI.Home;
import com.otec.Tokcoin.UI.Shop;
import com.otec.Tokcoin.UI.User;
import com.otec.Tokcoin.utils.utilJava;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class TopNavTabs extends RecyclerView.Adapter<TopNavTabs.CustomAdapater> {

    private List<String> tabs;
    private Context context;


    private String TAG = "TopNavTabs";
    private int i = -1;

    public TopNavTabs(List<String> tabs, Context context) {
        this.tabs = tabs;
        tabs.add(0, "Home");
        tabs.add(1, "Exchange");
        tabs.add(2, "Groups");
        tabs.add(3, "Auto");
        tabs.add(4, "Digits");
        tabs.add(tabs.size(), "User");
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public CustomAdapater onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false);
        return new CustomAdapater(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CustomAdapater holder, int position) {


        holder.tabs.setOnClickListener(e -> {
            i = position;
            notifyDataSetChanged();
            if (holder.tabs.getText().toString().trim().equalsIgnoreCase("home") && FirebaseAuth.getInstance().getCurrentUser() != null)
                new utilJava().open_Fragment(new Home(), "Home", e, new Bundle(), R.id.frameLayout);
            else
            if (holder.tabs.getText().toString().trim().equalsIgnoreCase("user") && FirebaseAuth.getInstance().getCurrentUser() != null)
                new utilJava().open_Fragment(new User(), "User", e, null, R.id.frameLayout);
            else
              if (holder.tabs.getText().toString().trim().equalsIgnoreCase("groups") && FirebaseAuth.getInstance().getCurrentUser() != null)
                new utilJava().open_Fragment(new GroupsLists(), "Groups", e, new Bundle(), R.id.frameLayout);
           else
             if (holder.tabs.getText().toString().trim().equalsIgnoreCase("digits") && FirebaseAuth.getInstance().getCurrentUser() != null)
                new utilJava().open_Fragment(new Digits(), "Digits", e, new Bundle(), R.id.frameLayout);
            else
                if(holder.tabs.getText().toString().trim().toLowerCase().equalsIgnoreCase("Exchange") && FirebaseAuth.getInstance().getCurrentUser() != null)
                        holder.tabs.getContext().startActivity(new Intent(holder.tabs.getContext(),Shop.class));
                else
                   if (holder.tabs.getText().toString().trim().equalsIgnoreCase("auto") && FirebaseAuth.getInstance().getCurrentUser() != null)
                    new utilJava().open_Fragment(new Auto(), "Auto", e, new Bundle(), R.id.frameLayout);
        });

        if (i == position)
            holder.tabs.setBackgroundResource(R.drawable.inputlines1);
        else
            holder.tabs.setBackgroundResource(R.drawable.inputlines);

        if (tabs.get(position).equals("Home") || tabs.get(position).equals("User") || tabs.get(position).equals("Groups"))
            holder.tabs.setCompoundDrawablesWithIntrinsicBounds(Drawables(holder.tabs, tabs.get(position)), null, null, null);
        else
            holder.tabs.setCompoundDrawablesWithIntrinsicBounds(Drawables(holder.tabs, ""), null, null, null);

        holder.tabs.setText(" " + tabs.get(position));


    }

    private Bundle BUNDLE(String s) {
        Bundle b = new Bundle();
        b.putString("category", s);
        return b;
    }

    private Drawable Drawables(TextView tabs, String p) {
        Drawable img;
        switch (p) {
            case "Home":
                img = tabs.getContext().getResources().getDrawable(R.drawable.ic_baseline_home_24);
                break;
            case "User":
                img = tabs.getContext().getResources().getDrawable(R.drawable.user2);
                break;
            case "Groups":
                img = tabs.getContext().getResources().getDrawable(R.drawable.group_24);
                break;
            case "Auto":
                img = tabs.getContext().getResources().getDrawable(R.drawable.ic_baseline_lens_24);
                break;
            case "Exchange":
                img = tabs.getContext().getResources().getDrawable(R.drawable.ic_baseline_storefront_24);
                break;
            default:
                img = tabs.getContext().getResources().getDrawable(R.drawable.ic_baseline_games_24);
                break;
        }

        return img;
    }

    @Override
    public int getItemCount() {
        return tabs.size();
    }


    class CustomAdapater extends RecyclerView.ViewHolder {
        private Button tabs;

        public CustomAdapater(View v) {
            super(v);
            tabs = v.findViewById(R.id.tab);

        }
    }
}
