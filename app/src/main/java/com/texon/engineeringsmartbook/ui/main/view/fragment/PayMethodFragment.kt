package com.texon.engineeringsmartbook.ui.main.view.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import com.squareup.picasso.Picasso
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.databinding.FragmentPayMethodBinding
import com.texon.engineeringsmartbook.ui.main.view.auth.Login
import com.texon.engineeringsmartbook.ui.main.viewModel.FragmentCommunicator
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class PayMethodFragment : Fragment(R.layout.fragment_pay_method) {

    private lateinit var binding: FragmentPayMethodBinding
    private lateinit var fc: FragmentCommunicator
    private var id = ""
    private var avatar = ""
    private var title = ""
    private var token = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPayMethodBinding.bind(view)
        fc = activity as FragmentCommunicator
        loadProfile()
        loadToken()
        Log.d("Pay Method", "id = $id, title = $title, token = $token, avatar = $avatar")

        binding.payDetails.txtPayLater.setOnClickListener {
            Log.d("Pay Method", "id = $id, title = $title, token = $token, avatar = $avatar")
            fc.passData("home", 0)
        }

        binding.payDetails.btnMakePayment.setOnClickListener {
            val txn = binding.payDetails.txtTnxId.text.toString().trim()
            if(txn.isEmpty()){
                binding.payDetails.txtTnxId.error = "Txn Id Needed!"
                binding.payDetails.txtTnxId.requestFocus()
                return@setOnClickListener
            }
            val sharedPreferences = this.activity?.getSharedPreferences("BookAccess", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.apply{
                putString("txId", txn)
            }?.apply()

            fc.passData("confirmOrder", id.toInt())
        }

    }


    private fun loadProfile(){
        val sharedPreferences: SharedPreferences? = this.activity?.getSharedPreferences("BookAccess", Context.MODE_PRIVATE)
        val session = sharedPreferences?.getBoolean("session", false)
        if(session == true){
            id = sharedPreferences.getInt("id", 0).toString()
            avatar = sharedPreferences.getString("avatar", "")!!
            title = sharedPreferences.getString("title", "")!!
            binding.bookInfo.txtBookTitle.text = title
            Picasso.get().load(avatar).into(binding.bookInfo.imgBookCover)
        }
    }

    private fun loadToken(){
        val sharedPreferences: SharedPreferences? = this.activity?.getSharedPreferences("Session", Context.MODE_PRIVATE)
        val session = sharedPreferences?.getBoolean("session", false)
        if(session == true){
            token = sharedPreferences.getString("token", "")!!

        }
        else{
            val intent = Intent(this.context, Login::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}