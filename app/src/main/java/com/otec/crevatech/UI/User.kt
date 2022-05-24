package com.otec.crevatech.UI

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.otec.crevatech.Adapater.UserGroupslist
import com.otec.crevatech.R
import com.otec.crevatech.Retrofit_.Base_config
import com.otec.crevatech.Retrofit_.Request_class
import com.otec.crevatech.utils.utilJava
import com.otec.crevatech.utils.utilKotlin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*


class User : Fragment() {

    val TAG =  "User"
    private lateinit var createdGroup: RecyclerView
    private lateinit var down: ProgressBar
    override fun onCreateView(inflate: LayoutInflater, container: ViewGroup?, savedInstance: Bundle?): View?{
        val view: View = inflate.inflate(R.layout.activity_user, container,false)
        val userName: TextView = view.findViewById(R.id.userName)
        val userBalance: TextView = view.findViewById(R.id.userBalance)
        val userGas: TextView = view.findViewById(R.id.userGas)
        down = view.findViewById(R.id.down)
        createdGroup = view.findViewById(R.id.createdGroup)
        createdGroup.layoutManager = LinearLayoutManager(context)
        LoopChanges(userBalance,userGas,userName)
        RequestGroupList();
        return  view;
    }

    private fun LoopChanges(bal: TextView, gas: TextView, userName: TextView) {
        userName.text = utilJava().GET_CACHED_MAP(context, getString(R.string.SIGNED_IN_USER))["email"].toString()
        if (FirebaseAuth.getInstance().uid != null) FirebaseFirestore.getInstance().collection(getString(R.string.REGISTER_USER)).document(FirebaseAuth.getInstance().uid!!).addSnapshotListener { value: DocumentSnapshot?, error: FirebaseFirestoreException? ->
              bal.text = Currency(value!!["User_details.bal"].toString())
              gas.text = Currency(value["User_details.gas"].toString())
            }
    }


    private fun Currency(va: String): String? {
        val Us = NumberFormat.getCurrencyInstance(Locale.US)
        return Us.format(va.toDouble())
    }


    private fun RequestGroupList() {
        val request = Base_config.getRetrofit().create(Request_class::class.java)
        val isFunded = request.GT_GROUPS(utilJava().GET_GROUP(utilJava().GET_CACHED_MAP(context, getString(R.string.SIGNED_IN_USER))))
        isFunded.enqueue(object : Callback<Map<String,Any>> {
            override fun onResponse(call: Call<Map<String,Any>>, response: Response<Map<String,Any>>) {
                val l1: Map<*, *>? = response.body()!!["message"] as Map<*, *>?
                val l1a: Map<*, *>? = l1?.get("listA") as Map<*, *>?
                val  groups = UserGroupslist(l1a?.get("raw1") as MutableList<MutableMap<String, Any>>?,context,1)
                createdGroup.adapter = groups
                down.visibility = View.INVISIBLE
            }

            override fun onFailure(call: Call<Map<String,Any>>, t: Throwable) {
                utilKotlin().message2(t.message, context)
                down.visibility = View.INVISIBLE
            }
        })

    }





}