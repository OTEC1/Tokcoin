package com.otec.Tokcoin.Adapater;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.otec.Tokcoin.R;
import com.otec.Tokcoin.Retrofit_.Base_config;
import com.otec.Tokcoin.Retrofit_.Request_class;
import com.otec.Tokcoin.UI.Group_creation;
import com.otec.Tokcoin.utils.utilJava;
import com.otec.Tokcoin.utils.utilKotlin;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.Map;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Avater_adapter  extends RecyclerView.Adapter<Avater_adapter.Custom_adapter>{


    private  List<Integer> avaters;
    private Context  context;
    private  int i;


    private  String TAG = "Avater_adapter";

    public Avater_adapter(List<Integer> avaters, Context context, int u) {
        this.avaters = avaters;
        this.context = context;
        this.i = u;
    }

    @NonNull
    @NotNull
    @Override
    public Avater_adapter.Custom_adapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View adapter = LayoutInflater.from(parent.getContext()).inflate(R.layout.avater_circle,parent,false);
        return new Custom_adapter(adapter);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Avater_adapter.Custom_adapter holder, int position) {
        Glide.with(holder.imageView.getContext()).load(avaters.get(position)).into(holder.imageView);
            holder.imageView.setOnClickListener(e->{
                if(i == 0)
                   Update(e.getContext(),avaters.get(position));
                else {
                    new utilJava().init(holder.imageView.getContext()).edit().putInt("icons", avaters.get(position)).apply();
                         new utilJava().open_Fragment(new Group_creation(),"Group_creation",e,new utilJava().BUNDLE(avaters.get(position),null,null),R.id.frameLayout);
                }
            });
    }

    @Override
    public int getItemCount() {
        return avaters.size();
    }


    class  Custom_adapter extends RecyclerView.ViewHolder {

        private CircleImageView imageView;
        public Custom_adapter(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.avater);
        }
    }





    public void Update(Context cn, int i) {
        Request_class config = Base_config.getRetrofit().create(Request_class.class);
        Call<Map<String, Object>> isFunded = config.UPDATE_USER(new utilJava().UPDATES(new utilJava().GET_CACHED_MAP(cn, cn.getString(R.string.SIGNED_IN_USER)),i));
        isFunded.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (response.code() == 200)
                    new utilKotlin().message2(response.body().get("message").toString(), cn);
                else
                    Log.d(TAG, "onResponse: "+response.code());
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                new utilKotlin().message2(t.getMessage(), cn);
            }
        });

    }
    
}
