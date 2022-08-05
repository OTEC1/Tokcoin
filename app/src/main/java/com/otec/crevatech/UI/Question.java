package com.otec.crevatech.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    private List<Map<String, Object>> alist = new ArrayList<>();
    private TextView a1, a2, a3, a4, question, questionIndex, timer;
    private Button question_reset;
    private Timer time;
    private RelativeLayout question_layout;
    private ProgressBar progressBar;

    private boolean reset = true, stated = false, cancel = true;
    private String TAG = "Question";
    private int n = 0, p = 5, count = 9,ip;


    @Override
    public void onStop() {
        super.onStop();
        if (stated) {
            cancel = false;
            LoadUserBalance("",  2);
            Log.d(TAG, "onStop:");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question, container, false);
        a1 = v.findViewById(R.id.a1);
        a2 = v.findViewById(R.id.a2);
        a3 = v.findViewById(R.id.a3);
        a4 = v.findViewById(R.id.a4);
        question_reset = v.findViewById(R.id.question_reset);
        question = v.findViewById(R.id.question);
        questionIndex = v.findViewById(R.id.questionIndex);
        progressBar = v.findViewById(R.id.progressBar);
        question_layout = v.findViewById(R.id.question_layout);
        timer = v.findViewById(R.id.timer);
        Bundle b = getArguments();

        IsFunded(b);

        a1.setOnClickListener(e -> {
            SEND_ANSWER(a1.getText().toString(), b);
        });
        a2.setOnClickListener(e -> {
            SEND_ANSWER(a2.getText().toString(), b);
        });
        a3.setOnClickListener(e -> {
            SEND_ANSWER(a3.getText().toString(), b);
        });
        a4.setOnClickListener(e -> {
            SEND_ANSWER(a4.getText().toString(),b);
        });

        question_reset.setOnClickListener(e->{
            question_reset.setVisibility(View.INVISIBLE);
            Request_Question(TRIM(b));

        });

        return v;
    }



    private void IsFunded(Bundle b) {
        Request_class request_class = Base_config.getRetrofit().create(Request_class.class);
        Call<Map<String, Object>> isFunded = request_class.isFunded(new utilJava().GET_USER(new utilJava().GET_CACHED_MAP(getContext(), getString(R.string.SIGNED_IN_USER)), b.get("category").toString(), null,  2));
        isFunded.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (Boolean.parseBoolean(response.body().get("message").toString()))
                    Request_Question(TRIM(b));
                else if (response.body().get("message").toString().equals("Unauthorized Request !"))
                    new utilKotlin().message2(response.body().get("message").toString(), getContext());
                else
                    new utilKotlin().message2("Insufficient funds pls add funds !", getContext());
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), getContext());
            }
        });

    }



    private void SEND_ANSWER(String answer, Bundle b) {
        count = 9;
        UpdateTimer();
        UpdateQuestionUi(1);
        Map<String, Object> user_answers = new HashMap<>();
        user_answers.put("answer_selected", answer);
        user_answers.put("category", TRIM(b));
        user_answers.put("question_id",ip);
        alist.add(user_answers);
        Log.d(TAG, "SEND_ANSWER: "+p);
        if (p > 0) {
            progressBar.setVisibility(View.VISIBLE);
            new utilKotlin().message2(p + " more to go", getActivity());
            Request_Question(TRIM(b));
        } else if (n >= 5 && p == 0 && cancel) {
            LoadUserBalance(TRIM(b),  2);
            question_reset.setVisibility(View.VISIBLE);
            cancel = false;
        }
    }

    private void UpdateQuestionUi(int c) {
        question_layout.setVisibility(c == 0 ? View.VISIBLE : View.INVISIBLE);
    }


    private void Request_Question(String category) {
        Map<String, Object> user = new utilJava().GET_USER(new utilJava().GET_CACHED_MAP(getContext(), getString(R.string.SIGNED_IN_USER)),category, null,2);
        Request_class request = Base_config.getRetrofit().create(Request_class.class);
        Call<models> list = request.getPostList(user);
        list.enqueue(new Callback<models>() {
            @Override
            public void onResponse(Call<models> call, Response<models> response) {
                progressBar.setVisibility(View.INVISIBLE);
                stated = true;

                if (n > 5) {
                    n = 0;
                    p = 5;
                }

                if (p != 0)
                    p--;
                n++;
                List<Map<String, Object>> map = response.body().getMessage();
                if (!map.get(0).toString().contains("error")) {
                    UpdateQuestionUi(0);
                    for (Map<String, Object> i : map) {
                        Map<String, Object> x = (Map<String, Object>) i.get("Q");
                        List<?> a = getRandomElement(C(x.get("answers")), C(x.get("answers")).size());
                        Double mp = Double.parseDouble(x.get("id").toString());
                        ip = mp.intValue();
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
                } else if (map.get(0).get("error").toString().equals("All set !") || map.get(0).get("error").toString().equals("All Reset !")) {
                    question_reset.setVisibility(View.VISIBLE);
                    new utilKotlin().message2(map.get(0).get("error").toString(), getContext());
                } else {
                    new utilKotlin().message2(map.get(0).get("error").toString(), getContext());
                stated = false;
              }
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
                    if (count == 0 && reset && cancel) {
                       UpdateQuestionUi(1);
                        LoadReset("Sorry time's up !", "");
                        LoadUserBalance("",  2);
                        reset = false;
                        cancel = false;
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



    public List<?> C(Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray())
            list = Arrays.asList((Object[]) obj);
        else if (obj instanceof Collection)
            list = new ArrayList<>((Collection<?>) obj);
        return list;
    }


    private void LoadReset(String s, String b) {
        new utilKotlin().message2(s, getActivity());
        startActivity(new Intent(getContext(),MainActivity.class));
    }


    private void LoadUserBalance(String string,int call) {
        progressBar.setVisibility(View.VISIBLE);
        Request_class config = Base_config.getRetrofit().create(Request_class.class);
        Call<Map<String, Object>> isFunded = config.isFunded_Active(new utilJava().GET_USER(new utilJava().GET_CACHED_MAP(getContext(), getString(R.string.SIGNED_IN_USER)), string, alist, call));
        isFunded.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                new utilKotlin().message2(response.body().get("message").toString(), getContext());
                progressBar.setVisibility(View.INVISIBLE);
                stated = false;
                alist.clear();
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), getContext());
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

    private String TRIM(Bundle b) {
        return b.get("category").toString().trim().replace(" ", "");
    }

}
