package com.otec.crevatech.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import com.otec.crevatech.R

class utilKotlin {


    fun message2(string: String?, context: Context?) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show()
    }

    //dynamic based on user choice
    fun Top_status_bar(window: Window, activity: Activity, home_background: RelativeLayout) {
        if (Build.VERSION.SDK_INT >= 21) {
            val windows = window
            windows.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            windows.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //dark by default
            windows.statusBarColor = activity.resources.getColor(R.color.Top_most_section)
            home_background.setBackgroundResource(R.mipmap.dimetradedarktheme)
        }
    }


    fun verfiy(editText: EditText, editText2: EditText): Boolean {
        return editText.text.isNotEmpty() && editText.text.isNotEmpty()
    }

    fun  check(editText: EditText): Boolean{
       return  editText.getText().toString().trim().isEmpty()
    }
}