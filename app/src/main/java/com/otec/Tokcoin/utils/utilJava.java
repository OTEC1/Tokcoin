package com.otec.Tokcoin.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.otec.Tokcoin.Adapater.Digits_Call;
import com.otec.Tokcoin.R;
import com.otec.Tokcoin.Retrofit_.Base_config;
import com.otec.Tokcoin.Retrofit_.Request;
import com.otec.Tokcoin.UI.MainActivity;
import com.otec.Tokcoin.model.models;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class utilJava {

    private SharedPreferences sp;
    private List<Map<String, Object>> o;


    private String TAG = "utilJava";

    public SharedPreferences init(Context view) {
        return sp = Objects.requireNonNull(view.getSharedPreferences(view.getString(R.string.app_name), Context.MODE_PRIVATE));
    }


    public List<?> C(Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray())
            list = Arrays.asList((Object[]) obj);
        else if (obj instanceof Collection)
            list = new ArrayList<>((Collection<?>) obj);
        return list;
    }


    public Object SET_CACHED_USER(Map<String, Object> obj, String tag, Context view) {
        Map<String, Object> user = Maps();
        user.put("email", obj.get("email"));
        user.put("IMEI", obj.get("IMEI"));
        user.put("user_id", obj.get("user_id"));
        user.put("avatar", obj.get("avatar"));
        SET_DATA_TO_CACHE(view, user, tag);
        view.startActivity(new Intent(view, MainActivity.class));
        return user;
    }

    public String SET_DATA_TO_CACHE(Context view, Object user, String tag) {
        SharedPreferences.Editor collection = init(view).edit();
        String area = new Gson().toJson(user);
        collection.putString(tag, area);
        collection.apply();
        return area;
    }

    public Map<String, Object> GET_CACHED_MAP(Context view, String tag) {
        String arrayListString = init(view).getString(tag, null);
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        return new Gson().fromJson(arrayListString, type);
    }


    public List<Map<String, Object>> GET_CACHED_LIST(Context view, String tag) {
        String arrayListString = init(view).getString(tag, null);
        Type type = new TypeToken<List<Map<String, Object>>>() {}.getType();
        return new Gson().fromJson(arrayListString, type);
    }


    //Open Fragment from  Adapter Class
    public void open_Fragment(Fragment fragments, String tag, View view, Bundle bundle, int d) {
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        Fragment myfrag = fragments;
        myfrag.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(d, myfrag, tag).addToBackStack(null).commit();
    }


    //Fragment Open from Activity
    public void openFragment(Fragment fragment, String my_fragment, int a, AppCompatActivity context) {
        FragmentTransaction fragmentTransaction = context.getSupportFragmentManager().beginTransaction();
        reuse_fragment(fragmentTransaction, fragment, my_fragment, BUNDLE(a, null, null), R.id.frameLayout);
    }


    //Fragment open from fragment
    public void openFrag(Fragment fragment, String my_fragment, Map<String, Object> i, FragmentActivity activity) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        reuse_fragment(fragmentTransaction, fragment, my_fragment, BUNDLE(0, null, null), R.id.frameLayout);
    }


    //Reuse component
    private void reuse_fragment(FragmentTransaction fragmentTransaction, Fragment fragment, String my_fragment, Bundle b, int frameLayout) {
        fragment.setArguments(b);
        fragmentTransaction.replace(frameLayout, fragment, my_fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public Bundle BUNDLE(int bundle, String docs, String email) {
        Bundle bund = new Bundle();
        bund.putInt("node", bundle);
        bund.putString("doc", docs);
        bund.putString("email", email);
        return bund;
    }


    public Map<String, Object> _DIGIT(Map<String, Object> user, Boolean isGroup, Boolean isUser, Boolean isBot, List<Double> creator, List<Integer> user_selected, String creator_id, String doc_id) {
        Map<String, Object> obj = Maps();
        obj.put("user_id", user.get("user_id"));
        obj.put("email", user.get("email"));
        obj.put("IMEI", user.get("IMEI"));
        obj.put("isGroup", isGroup);
        obj.put("isUser", isUser);
        obj.put("isBot", isBot);
        obj.put("creator", creator);
        obj.put("user_selected", user_selected);
        obj.put("creator_id", creator_id);
        obj.put("doc_id", doc_id);
        return Wrap(obj);
    }


    public Map<String, Object> GET_USER(Map<String, Object> obj, String category, List<Map<String, Object>> lists, int call) {
        Map<String, Object> user = Maps();
        user.put("user_id", obj.get("user_id"));
        user.put("IMEI", obj.get("IMEI"));
        user.put("email", obj.get("email"));
        user.put("category", category);
        if (lists != null)
            user.put("list", lists);
        return call == 2 ? Wrap(user) : user;
    }


    public Map<String, Object> GET_GROUP(Map<String, Object> obj) {
        Map<String, Object> user = Maps();
        user.put("user_id", obj.get("user_id"));
        user.put("IMEI", obj.get("IMEI"));
        user.put("email", obj.get("email"));
        return Wrap(user);
    }


    public Map<String, Object> sendrequest(Map<String, Object> obj, String doc_id, Object creator) {
        Map<String, Object> pack = Maps();
        pack.put("user_id", obj.get("user_id").toString());
        pack.put("email", obj.get("email").toString());
        pack.put("IMEI", obj.get("IMEI").toString());
        pack.put("doc_id", doc_id);
        pack.put("creator_email", creator.toString().replace("[", "").replace("]", ""));
        Log.d(TAG, "RequestSend: " + pack);
        return Wrap(pack);
    }


    public Map<String, Object> GET_GROUP(Map<String, Object> obj, EditText groupName, EditText amount, EditText liquidator_stake, EditText miner_stake, Double odd, int avatar) {
        Map<String, Object> user = Maps();
        user.put("user_id", obj.get("user_id"));
        user.put("IMEI", obj.get("IMEI"));
        user.put("email", obj.get("email"));
        user.put("groupName", groupName.getText().toString().trim());
        user.put("amount", Integer.parseInt(amount.getText().toString()));
        user.put("liquidator_size", Long.parseLong(liquidator_stake.getText().toString()));
        user.put("miner_stake", Long.parseLong(miner_stake.getText().toString()));
        user.put("active", false);
        user.put("odd", odd);
        user.put("avatar", avatar);
        user.put("profit", 0);
        user.put("loss", 0);
        user.put("liquidity", 0);
        return Wrap(user);
    }


    public void LoadInstance(TextView rt, Button play, ProgressBar progress1, Context e, List<Double> numbers, List<Integer> send_number, Boolean isGroup, Boolean isUser, Boolean isBot, String creator_id, String doc_id, RecyclerView digits_returned, Button button, ProgressBar progress, int i) {
        Request config = Base_config.getRetrofit().create(Request.class);
        Call<models> isFunded = config.DIGIT_BOT_REQUEST(new utilJava()._DIGIT(new utilJava().GET_CACHED_MAP(e, e.getString(R.string.SIGNED_IN_USER)), isGroup, isUser, isBot, numbers, send_number, creator_id, doc_id));
        isFunded.enqueue(new Callback<models>() {
            @Override
            public void onResponse(Call<models> call, Response<models> response) {
                List<Map<String, Object>> ms = (List<Map<String, Object>>) response.body().getMessage();
                rt.setText(ms.get(0).get("m1").toString() + " " + new utilKotlin().cast(ms.get(0).get("m2").toString()));
                numbers.clear();
                send_number.clear();
                if (i == 2 && play != null && progress1 != null) {
                    play.setVisibility(View.VISIBLE);
                    progress1.setVisibility(View.INVISIBLE);
                }
                if (i == 1)
                    Request_Digit(e, digits_returned, true, button, progress, rt);
            }

            @Override
            public void onFailure(Call<models> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), e);
            }
        });

    }


    public void Request_Digit(Context cn, RecyclerView digits_returned, boolean loader, Button play, ProgressBar progress, TextView rt) {

        Request config = Base_config.getRetrofit().create(Request.class);
        Call<Map<String, Object>> isFunded = config.DIGIT_BOT_REQUEST2(new utilJava()._DIGIT(new utilJava().GET_CACHED_MAP(cn, cn.getString(R.string.SIGNED_IN_USER)), false, false, true, new ArrayList<>(), new ArrayList<>(), null, null));
        isFunded.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {

                if (response.body().get("message").toString().contains("error")) {
                    o = (List<Map<String, Object>>) response.body().get("message");
                    new utilKotlin().message2(o.get(0).get("error").toString(), cn);
                } else if (response.body().get("message").toString().contains("m1")) {
                    o = (List<Map<String, Object>>) response.body().get("message");
                    new utilKotlin().message2(o.get(0).get("m1").toString(), cn);
                } else {
                    setlayout((List<Double>) response.body().get("message"), digits_returned, loader, cn, play, rt, progress);
                    rt.setText("");
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), cn);
            }
        });

    }


    public void debit_stake(Context cn, TextView rt, Integer value, Button btn, ProgressBar progressBar) {
        Map<String, Object> v = new HashMap<>();
        v.put("id", 1);
        v.put("amount", value);
        Request config = Base_config.getRetrofit().create(Request.class);
        Call<Map<String, Object>> isFunded = config._CA(Wrap(v));
        isFunded.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.code() == 200) {
                    rt.setText("Gas to be debited: " + response.body().get("message"));
                    btn.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), cn);
            }
        });

    }


    private void setlayout(List<Double> message, RecyclerView digits_returned, boolean loader, Context cn, Button play, TextView rt, ProgressBar progress) {
        Digits_Call digits_call = new Digits_Call(message, cn, play, rt, loader, digits_returned);
        GridLayoutManager manager = new GridLayoutManager(cn, 4);
        digits_returned.setLayoutManager(manager);
        digits_returned.setAdapter(digits_call);
        if (progress != null)
            progress.setVisibility(View.INVISIBLE);
    }


    public void routine(String email, String issue, Context c) {
        FirebaseFirestore.getInstance().collection(c.getString(R.string.ISSUE_REPORT)).document(email)
                .collection("ISSUES").document()
                .set(MAP(issue, email))
                .addOnCompleteListener(res -> {
                    if (res.isSuccessful())
                        new utilKotlin().message2("We have received your complain", c);
                    else
                        new utilKotlin().message2(res.getException().getLocalizedMessage(), c);
                });
    }

    private Object MAP(String issues, String email) {
        Map<String, Object> m = Maps();
        m.put("email", email);
        m.put("issue", issues);
        return m;
    }

    public Map<String, Object> LEFT_GROUP(Map<String, Object> map, String ref_id) {
        Map<String, Object> maps = Maps();
        maps.put("email", map.get("email"));
        maps.put("IMEI", map.get("IMEI"));
        maps.put("user_id", map.get("user_id"));
        maps.put("doc_id", ref_id);
        return Wrap(maps);
    }


    public Map<String, Object> MAPS() {
        Map<String, Object> m = new HashMap<>();
        m.put("zone", new Date().getTime());
        return m;
    }


    private Map<String, Object> Maps() {
        Map<String, Object> m = new HashMap<>();
        return m;
    }


    public Map<String, Object> MapsValues(List<String> u, Map<String, Object> obj, Bundle arguments) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("email", obj.get("email"));
        payload.put("user_id", obj.get("user_id"));
        payload.put("IMEI", obj.get("IMEI"));
        payload.put("group_id", arguments.getString("doc"));
        payload.put("group_mail", arguments.getString("email"));
        payload.put("bot_size", Integer.parseInt(u.get(0).substring(u.get(0).length() - 2).trim()));
        payload.put("hour", Integer.parseInt(u.get(1).substring(u.get(1).length() - 2).trim()));
        return Wrap(payload);
    }


    public Map<String, Object> Wrap(Map<String, Object> user) {
        Map<String, Object> pack = Maps();
        pack.put("User", user);
        return pack;
    }


    public static String FORMAT(String node, List<Map<String, Object>> obj, int p) {
        Map<String, Object> i = (Map<String, Object>) obj.get(p).get("User");
        return i.get(node).toString();
    }


    public int checkUi(ViewGroup parent) {
        Configuration config = parent.getResources().getConfiguration();
        Log.d(TAG, "checkUi: " + config.smallestScreenWidthDp);
        if (config.smallestScreenWidthDp <= 320)
            return 0;
        else
            return 1;
    }


    public int Change(int p, TextView v) {
        if (p == 1)
            v.setBackgroundResource(R.mipmap.blue_btn);
        else if (p == 2)
            v.setBackgroundResource(R.mipmap.green_btn);
        else if (p == 3)
            v.setBackgroundResource(R.mipmap.reb_btn);
        return p;
    }





    public Map<String, Object> UPDATES(Map<String, Object> user,int u) {
        Map<String,Object> obj = Maps();
        obj.put("email", user.get("email"));
        obj.put("user_id", user.get("user_id"));
        obj.put("IMEI", user.get("IMEI"));
        obj.put("avatar", u);
        return  Wrap(obj);
    }

}
