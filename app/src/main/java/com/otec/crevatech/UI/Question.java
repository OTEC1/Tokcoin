package com.otec.crevatech.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.protobuf.Any;
import com.otec.crevatech.R;
import com.otec.crevatech.Retrofit_.Base_config;
import com.otec.crevatech.Retrofit_.Request_class;
import com.otec.crevatech.model.models;
import com.otec.crevatech.utils.utilJava;
import com.otec.crevatech.utils.utilKotlin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Question extends Fragment {


    private List<Integer> list = new ArrayList<>();
    private List<Map<String,Object>> alist = new ArrayList<>();
    private TextView a1, a2, a3, a4, question, questionIndex, timer;
    private Timer time;
    private RelativeLayout question_layout;
    private ProgressBar progressBar;

    private boolean reset = true;
    private String TAG = "Question";
    private int n = 0, p = 5, count = 9,id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question, container, false);
        a1 = v.findViewById(R.id.a1);
        a2 = v.findViewById(R.id.a2);
        a3 = v.findViewById(R.id.a3);
        a4 = v.findViewById(R.id.a4);
        question = v.findViewById(R.id.question);
        questionIndex = v.findViewById(R.id.questionIndex);
        progressBar = v.findViewById(R.id.progressBar);
        question_layout = v.findViewById(R.id.question_layout);
        timer = v.findViewById(R.id.timer);
        Bundle b = getArguments();
        IsFunded(b);
        a1.setOnClickListener(e -> {
            SEND_ANSWER(a1.getText().toString(), TRIM(b));
        });
        a2.setOnClickListener(e -> {
            SEND_ANSWER(a2.getText().toString(), TRIM(b));
        });
        a3.setOnClickListener(e -> {
            SEND_ANSWER(a3.getText().toString(), TRIM(b));
        });
        a4.setOnClickListener(e -> {
            SEND_ANSWER(a4.getText().toString(), TRIM(b));
        });

        return v;
    }

    private String TRIM(Bundle b) {
        return  b.get("category").toString().trim().replace(" ","");
    }


    private void IsFunded(Bundle b) {
        Request_class request_class = Base_config.getRetrofit().create(Request_class.class);
        Call<Map<String,Object>> isFunded = request_class.isFunded(new utilJava().GET_USER(new utilJava().GET_CACHED_MAP(getContext(),getString(R.string.SIGNED_IN_USER)),b.get("category").toString(),null,0,2));
        isFunded.enqueue(new Callback<Map<String,Object>>() {
            @Override
            public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> response) {
                Log.d(TAG, "onResponse: "+response.body().get("message"));
                if(Boolean.parseBoolean(response.body().get("message").toString()))
                       CheckLoad(b);
                else if(response.body().get("message").toString().equals("Unauthorized Request !"))
                           new utilKotlin().message2(response.body().get("message").toString(), getContext());
                    else
                       new utilKotlin().message2("Insufficient funds pls add funds !", getContext());
            }
            @Override
            public void onFailure(Call<Map<String,Object>> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), getContext());
                Log.d(TAG, "onFailure: bool " + t.getMessage());
            }
        });

    }


    private void CheckLoad(Bundle b) {
        String cat = TRIM(b);
        if (Check(getString(R.string.QuestionID), cat, 1))
            LoadQuestion(getString(R.string.QuestionID), cat, UUID.randomUUID().toString());
        else if (Check(getString(R.string.QuestionID), cat, 2))
            WaitThenRemove(getString(R.string.QuestionID), cat);
        else
            InitQuestion(cat, UUID.randomUUID().toString(), getString(R.string.QuestionID));
    }


    private void WaitThenRemove(String tag, String b) {
        new utilKotlin().message2("Pls wait we setting up the questions", getContext());
        // if timer Count down
        List<Map<String, Object>> array = new utilJava().GET_CACHED_LIST(getContext(), tag);
        Map<String, Object> o = new HashMap<>();
        o.put(b, new ArrayList<>());
        array.remove(o);
        String c = new utilJava().SET_DATA_TO_CACHE(getContext(), array, tag);
        Log.d(TAG, "WaitThenRemove: " + c);
        InitQuestion(b,"",tag);

    }


    private boolean Check(String tag, String category, int caller) {
        List<String> categories = new ArrayList<>();
        List<Map<String, Object>> array = new utilJava().GET_CACHED_LIST(getContext(), tag);
        if (array != null) {
            for (int y = 0; y < array.size(); y++)
                for (Map.Entry<String, Object> obj : array.get(y).entrySet())
                    if (caller == 1) {
                        if (C(obj.getValue()).size() > 0)
                            categories.add(obj.getKey().trim());
                    } else
                        categories.add(obj.getKey().trim());

            return categories.contains(category.trim());
        }
        else
            return false;
    }


    private void SEND_ANSWER(String answer, String b) {
        count = 9;
        UpdateTimer();
        question_layout.setVisibility(View.INVISIBLE);
         Map<String,Object> user_answers = new HashMap<>();
         user_answers.put("answer_selected",answer);
         user_answers.put("category",b);
         user_answers.put("session_id",new utilJava().GET_CACHED_MAP(getContext(), getString(R.string.SESSION_ID)).get("uuid_stamp").toString());
         user_answers.put("question_id",id);
         alist.add(user_answers);
            if (p > 0) {
                new utilKotlin().message2(p + " more to go", getActivity());
                LoadQuestion(getString(R.string.QuestionID), b, UUID.randomUUID().toString());
            } else
                 if (n >= 5 && p == 0)
                    LoadUserBalance(b,1,2);

    }


    private void InitQuestion(String b, String uuid, String tag) {
        list.clear();
        Map<String,Object> uid = new HashMap<>();
        uid.put("uuid_stamp",uuid);
        new utilJava().SET_DATA_TO_CACHE(getContext(),uid,getString(R.string.SESSION_ID));
        Request_class request = Base_config.getRetrofit().create(Request_class.class);
        Call<Map<String,Object>> list = request.getPostSize(new utilJava().GET_USER(new utilJava().GET_CACHED_MAP(getContext(), getString(R.string.SIGNED_IN_USER)), b, null, 0,2));
        list.enqueue(new Callback<Map<String,Object>>() {
            @Override
            public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> response) {
                    GenerateRandomValue(b, uuid, tag, (int) (Double.parseDouble(response.body().get("message").toString())));
            }
            @Override
            public void onFailure(Call<Map<String,Object>> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), getContext());
                Log.d(TAG, "onFailure: here" + t.getMessage());
            }
        });
    }


    private void GenerateRandomValue(String b, String uuid, String tag, int size) {
        Map<String, Object> obj = new HashMap<>();
        obj.put(b, psvm(0, size));
        List<Map<String, Object>> arrayList = new utilJava().GET_CACHED_LIST(getContext(), tag);
        if (arrayList == null)
            arrayList = new ArrayList<>();
        arrayList.add(obj);
        new utilJava().SET_DATA_TO_CACHE(getContext(), arrayList, tag);
        LoadQuestion(tag, b, uuid);
    }


    private void LoadQuestion(String tag, String b, String uuid) {
        List<Map<String, Object>> array = new utilJava().GET_CACHED_LIST(getContext(), tag);
        progressBar.setVisibility(View.VISIBLE);
        reset = true;
        if (array != null)
            for (int y = 0; y < array.size(); y++)
                for (Map.Entry<String, Object> obj : array.get(y).entrySet())
                    if (obj.getKey().equals(b))
                        if (C(obj.getValue()).size() > 0)
                            Request_Question(C(obj.getValue()).get(C(obj.getValue()).size() - 1), b, uuid, array, tag);
                        else
                            WaitThenRemove(getString(R.string.QuestionID), b);
    }


    private List<?> C(Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray())
            list = Arrays.asList((Object[]) obj);
         else if (obj instanceof Collection)
            list = new ArrayList<>((Collection<?>) obj);
        return list;
    }



    private void Request_Question(Object o, String obj2, String uid, List<Map<String, Object>> array, String tag) {
        Map<String, Object> user = new utilJava().GET_USER(new utilJava().GET_CACHED_MAP(getContext(), getString(R.string.SIGNED_IN_USER)), obj2, null, (int) Double.parseDouble(o.toString()),2);
        Request_class request = Base_config.getRetrofit().create(Request_class.class);
        Call<models> list = request.getPostList(user);
        list.enqueue(new Callback<models>() {
            @Override
            public void onResponse(Call<models> call, Response<models> response) {
                        question_layout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        if (n > 5) {
                            n = 0;
                            p = 5;
                        }
                        if (p != 0)
                            p--;
                        n++;
                                List<Map<String, Object>> map = response.body().getMessage();
                                for (Map<String, Object> i : map) {
                                    Map<String, Object> x = (Map<String, Object>) i.get("Q");
                                    List<?> a = getRandomElement(C(x.get("answers")), C(x.get("answers")).size());
                                    id = (int) Double.parseDouble(o.toString());
                                    if (C(x.get("answers")).size() == 2) {
                                        Hide(a3, a4, 1);
                                        for (int v = 0; v < a.size(); v++) {
                                            if (v == 0)
                                                a1.setText(a.get(v).toString());
                                            if (v == 1)
                                                a2.setText(a.get(v).toString());
                                        }
                                    } else {
                                        Hide(a3, a4, 2);
                                        for (int v = 0; v < a.size(); v++) {
                                            if (v == 0)
                                                a1.setText(a.get(v).toString());
                                            if (v == 1)
                                                a2.setText(a.get(v).toString());
                                            if (v == 2)
                                                a3.setText(a.get(v).toString());
                                            if (v == 3)
                                                a4.setText(a.get(v).toString());
                                           }
                                      }
                                    question.setText(x.get("question").toString());
                                    questionIndex.setText("Question " + n + "/5");
                                    StartTimer();
                                    }
                                    RemoveLast(array, obj2, tag);
              }
            @Override
            public void onFailure(Call<models> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), getContext());
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }



    private void StartTimer() {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(() -> {
                        timer.setText(count + " sec");
                        if (count == 0 && reset) {
                            question_layout.setVisibility(View.INVISIBLE);
                            LoadReset("Sorry time's up !","");
                            LoadUserBalance("",1,2);
                            reset = false;
                        }
                      });
                        if (count != 0)
                            count--;
                }
            };
            time = new Timer();
            time.schedule(task, 0, 1000);
    }


    public void UpdateTimer() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("reset timer");
            }
        };
        time.cancel();
        time = new Timer();
        time.schedule(timerTask, 2000);
    }


    private void LoadReset(String s,String b) {
        if(Boolean.parseBoolean(new utilJava().GET_CACHED_MAP(getContext(),getString(R.string.APP_STATE)).get("app_state").toString())) {
            new utilKotlin().message2(s, getActivity());
            new utilJava().openFrag(new Home(), "Home", null, getActivity());
        }
    }




    private void LoadUserBalance(String string,int id, int call) {
        progressBar.setVisibility(View.VISIBLE);
        Request_class config = Base_config.getRetrofit().create(Request_class.class);
        Call<Map<String, Object>> isFunded = config.isFunded_Active(new utilJava().GET_USER(new utilJava().GET_CACHED_MAP(getContext(), getString(R.string.SIGNED_IN_USER)),string,alist, id,call));
        isFunded.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                 new utilKotlin().message2(response.body().get("message").toString(),getContext());
                new utilJava().openFrag(new User(), "User", null, getActivity());
                progressBar.setVisibility(View.INVISIBLE);
             alist.clear();
            }
            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                new utilKotlin().message2(t.getMessage(),getContext());
            }
        });
    }

    private void Hide(TextView a1, TextView a2, int x) {
        if (x == 1) {
            a1.setVisibility(View.INVISIBLE);
            a2.setVisibility(View.INVISIBLE);
        } else {
            a1.setVisibility(View.VISIBLE);
            a2.setVisibility(View.VISIBLE);
        }
    }

    private void RemoveLast(List<Map<String, Object>> array, String cat, String tag) {
        List<Object> list;
        Map<String, Object> o = new HashMap<>();
        for (int y = 0; y < array.size(); y++)
            for (Map.Entry<String, Object> obj : array.get(y).entrySet())
                if (obj.getKey().equals(cat)) {
                    list = (List<Object>) C(obj.getValue());
                    list.remove(list.size() - 1);
                    o.put(obj.getKey(), list);
                    array.remove(y);
                    array.add(o);
                    new utilJava().SET_DATA_TO_CACHE(getContext(), array, tag);
                }


    }

    public List<Integer> psvm(int min, int max) {
        int x;
        while (true) {
            x = min + (int) (Math.random() * ((max - min) + 1));
            list.add(x == 0 ? x + 1 : x);
            List<Integer> deDupStringList = new ArrayList<>(new HashSet<>(list));
            if (deDupStringList.size() == max)
                return deDupStringList;


        }
    }

    public List<Object> getRandomElement(List list, int totalItems) {
        Random rand = new Random();
        List<Object> newList = new ArrayList<>();
        for (int i = 0; i < totalItems; i++) {
            int randomIndex = rand.nextInt(list.size());
            newList.add(list.get(randomIndex));
            list.remove(randomIndex);
        }
        return newList;
    }
}
