package com.otec.Tokcoin.UI

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.otec.Tokcoin.Adapater.UserGroupslist
import com.otec.Tokcoin.R
import com.otec.Tokcoin.Retrofit_.Base_config
import com.otec.Tokcoin.Retrofit_.Request
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
    private lateinit var load: ProgressBar
    private var createGroup_btn: TextView? = null
    private var avater_img: ImageView? = null;


    override fun onCreateView(
        inflate: LayoutInflater,
        container: ViewGroup?,
        savedInstance: Bundle?
    ): View? {
        val view: View = inflate.inflate(R.layout.activity_user, container, false)
        val userName: TextView = view.findViewById(R.id.userName)
        val userBalance: TextView = view.findViewById(R.id.userBalance)
        val walletIcon: ImageView = view.findViewById(R.id.walletIcon)
        val userGas: TextView = view.findViewById(R.id.userGas)
        createGroup_btn = view.findViewById(R.id.create)
        avater_img = view.findViewById(R.id.user_screen_avatar)
        createdGroup = view.findViewById(R.id.createdGroup)
        down = view.findViewById(R.id.down)
        load = view.findViewById(R.id.load)
        createdGroup.layoutManager = LinearLayoutManager(context)
        Request_Vault(userBalance, userGas, userName)
        requestGroupList();


        createGroup_btn?.setOnClickListener {
            utilJava().openFragment(Group_creation(), "Group_creation", 0, context as AppCompatActivity?)
        }


        avater_img?.setOnClickListener {
            load.visibility = View.VISIBLE
            utilJava().openFragment(Avater(), "Avatar", 0, context as AppCompatActivity?)
        }


        walletIcon.setOnClickListener{
            val bund = Bundle()
            bund.putInt("node_", 1)
            utilJava().openFrag(Converter_io(), "Converter_io", bund, activity)
        }



        return view;
    }




    private fun Request_Vault(bal: TextView, gas: TextView, email: TextView) {
        val config = Base_config.getRetrofit().create(
            Request::class.java
        )
        val isFunded = config.isFunded(
            utilJava().GET_GROUP(
                utilJava().GET_CACHED_MAP(
                    context, getString(R.string.SIGNED_IN_USER)
                )
            )
        )
        isFunded.enqueue(object : Callback<Map<String?, Any>?> {
            override fun onResponse(call: Call<Map<String?, Any>?>, response: Response<Map<String?, Any>?>) {
                if (response.body() != null) {

                    val auths: Map<String, Any>? = response.body()!!["message"] as Map<String, Any>?

                    bal.text = currency(auths!!["bal"].toString())
                    gas.text = auths["gas"].toString()
                    email.text = utilJava().GET_CACHED_MAP(context, getString(R.string.SIGNED_IN_USER))["email"].toString()

                    if ((auths["avatar"] as Double).toInt() == 0)
                        avater_img?.setBackgroundResource(R.drawable.user)
                    else
                        avater_img?.setBackgroundResource((auths["avatar"] as Double).toInt())

                } else
                    utilKotlin().message2("Error occurred while retrieving user details !", context)
            }

            override fun onFailure(call: Call<Map<String?, Any>?>, t: Throwable) {
                utilKotlin().message2(t.message, context)
            }
        })
    }


    private fun currency(va: String): String? {
        val Us = NumberFormat.getCurrencyInstance(Locale.US)
        return Us.format(va.toDouble())
    }


    private fun requestGroupList() {
        val request = Base_config.getRetrofit().create(Request::class.java)
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