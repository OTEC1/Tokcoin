package com.otec.crevatech.UI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.otec.crevatech.R
import com.otec.crevatech.Retrofit_.Base_config
import com.otec.crevatech.Retrofit_.Request_class
import com.otec.crevatech.utils.utilJava
import com.otec.crevatech.utils.utilKotlin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class User : Fragment() {

    val TAG =  "User"
    override fun onCreateView(inflate: LayoutInflater, container: ViewGroup?, savedInstance: Bundle?): View?{
        val view: View = inflate.inflate(R.layout.activity_user, container,false)
        val userName: TextView = view.findViewById(R.id.userName)
        val userBalance: TextView = view.findViewById(R.id.userBalance)
        val userGas: TextView = view.findViewById(R.id.userGas)
        userName.text = utilJava().GET_CACHED_MAP(context, getString(R.string.SIGNED_IN_USER))["email"].toString()
        LoopChanges(userBalance,userGas)
        val b = arguments
        Log.d(TAG, "onCreateView: $b")
        return  view;
    }



    private fun LoopChanges(bal: TextView,gas:TextView) {
        if (FirebaseAuth.getInstance().uid != null) FirebaseFirestore.getInstance()
            .collection(getString(R.string.REGISTER_USER))
            .document(FirebaseAuth.getInstance().uid!!)
            .addSnapshotListener { value: DocumentSnapshot?, error: FirebaseFirestoreException? ->
              bal.text = value!!["User_details.bal"].toString()
              gas.text = value!!["User_details.gas"].toString()
            }
    }






}