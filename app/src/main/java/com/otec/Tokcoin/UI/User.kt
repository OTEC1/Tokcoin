package com.otec.Tokcoin.UI

import android.os.Bundle
import android.util.Log
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
import com.otec.Tokcoin.Adapater.UserGroupslist
import com.otec.Tokcoin.R
import com.otec.Tokcoin.Retrofit_.Base_config
import com.otec.Tokcoin.Retrofit_.Request_class
import com.otec.Tokcoin.utils.utilJava
import com.otec.Tokcoin.utils.utilKotlin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*


class User : Fragment() {

    val TAG = "User"
    private lateinit var createdGroup: RecyclerView
    private lateinit var down: ProgressBar
    private var createGroup_btn: TextView? = null


    override fun onCreateView(
        inflate: LayoutInflater,
        container: ViewGroup?,
        savedInstance: Bundle?
    ): View? {
        val view: View = inflate.inflate(R.layout.activity_user, container, false)
        val userName: TextView = view.findViewById(R.id.userName)
        val userBalance: TextView = view.findViewById(R.id.userBalance)
        val userGas: TextView = view.findViewById(R.id.userGas)
        createGroup_btn = view.findViewById(R.id.create)
        createdGroup = view.findViewById(R.id.createdGroup)
        down = view.findViewById(R.id.down)
        createdGroup.layoutManager = LinearLayoutManager(context)
        changed(userBalance, userGas, userName)
        requestGroupList();


        createGroup_btn?.setOnClickListener {
            Log.d(TAG, "onCreateView: ")
            utilJava().openFrag(Group_creation(), "Group_creation", null, activity)
        }
        return view;
    }


    //Send to backend route
    private fun changed(bal: TextView, gas: TextView, userName: TextView) {
        userName.text = utilJava().GET_CACHED_MAP(
            context,
            getString(R.string.SIGNED_IN_USER)
        )["email"].toString()
        if (FirebaseAuth.getInstance().uid != null) FirebaseFirestore.getInstance()
            .collection(getString(R.string.REGISTER_USER))
            .document(FirebaseAuth.getInstance().currentUser?.email!!)
            .addSnapshotListener { value: DocumentSnapshot?, _: FirebaseFirestoreException? ->
                bal.text = currency(value!!["User_details.bal"].toString())
                gas.text = value["User_details.gas"].toString()
            }
    }


    private fun currency(va: String): String? {
        val Us = NumberFormat.getCurrencyInstance(Locale.US)
        return Us.format(va.toDouble())
    }


    private fun requestGroupList() {
        val request = Base_config.getRetrofit().create(Request_class::class.java)
        val isFunded = request.GT_GROUPS(
            utilJava().GET_GROUP(
                utilJava().GET_CACHED_MAP(
                    context,
                    getString(R.string.SIGNED_IN_USER)
                )
            )
        )
        isFunded.enqueue(object : Callback<Map<String, Any>> {
            override fun onResponse(
                call: Call<Map<String, Any>>,
                response: Response<Map<String, Any>>
            ) {
                if (!response.body()!!["message"].toString().contains("error")) {
                    val l1: Map<*, *>? = response.body()!!["message"] as Map<*, *>?
                    val l1a: Map<*, *>? = l1?.get("listA") as Map<*, *>?
                    val groups = UserGroupslist(
                        l1a?.get("raw1") as MutableList<MutableMap<String, Any>>?,
                        context,
                        1
                    )
                    createdGroup.adapter = groups
                    down.visibility = View.INVISIBLE
                }
            }

            override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                utilKotlin().message2(t.message, context)
                down.visibility = View.INVISIBLE
            }
        })
    }
}