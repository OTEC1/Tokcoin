package com.otec.crevatech.UI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.otec.crevatech.R
import com.otec.crevatech.utils.utilJava


class User : Fragment() {

    val TAG =  "User"
    override fun onCreateView(inflate: LayoutInflater, container: ViewGroup?, savedInstance: Bundle?): View?{
        val view: View = inflate.inflate(R.layout.activity_user, container,false)

        val userName: TextView = view.findViewById(R.id.userName);
        userName.text= utilJava().GET_CACHED_MAP(context, getString(R.string.SIGNED_IN_USER))["email"].toString()
        return  view;
    }
}