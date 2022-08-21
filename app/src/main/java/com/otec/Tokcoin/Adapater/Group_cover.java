package com.otec.Tokcoin.Adapater;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.otec.Tokcoin.UI.Botmine;
import com.otec.Tokcoin.UI.GroupRequest;
import com.otec.Tokcoin.UI.JoinedGroup;
import com.otec.Tokcoin.UI.Usermine;

import org.jetbrains.annotations.NotNull;

public class Group_cover extends FragmentPagerAdapter {

    private Context context;
    int total;
    int call;
    String TAG ="Group_cover";

    public Group_cover(@NonNull @NotNull FragmentManager fm, Context inner_context, int totals, int call) {
        super(fm);
        context = inner_context;
        total = totals;
        this.call = call;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                if (call == 1)
                    return new JoinedGroup();
                else
                    return new Usermine();
            case 1:
                if (call == 1)
                    return new GroupRequest();
                else
                    return new Botmine();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return total;
    }
}
