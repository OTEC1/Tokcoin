package com.otec.Tokcoin.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.otec.Tokcoin.R

class admob : Fragment() {
    override fun onCreateView(inflate: LayoutInflater, container: ViewGroup?, savedInstance: Bundle?): View?{
        val view: View = inflate.inflate(R.layout.activity_admob, container,false)
        return  view;
    }
}