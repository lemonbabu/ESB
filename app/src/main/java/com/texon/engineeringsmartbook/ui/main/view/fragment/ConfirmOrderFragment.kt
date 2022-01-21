package com.texon.engineeringsmartbook.ui.main.view.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.beust.klaxon.Json
import com.beust.klaxon.JsonObject
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.data.api.ApiInterfaces
import com.texon.engineeringsmartbook.data.api.RetrofitClient
import com.texon.engineeringsmartbook.databinding.FragmentConfirmOrderBinding
import com.texon.engineeringsmartbook.ui.main.view.auth.Login
import com.texon.engineeringsmartbook.ui.main.viewModel.FragmentCommunicator
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.awaitResponse
import java.lang.Exception

@DelicateCoroutinesApi
class ConfirmOrderFragment : Fragment(R.layout.fragment_confirm_order) {

    private lateinit var binding: FragmentConfirmOrderBinding
    private val orderConfirmation: ApiInterfaces.ConfirmOrderInterface by lazy { RetrofitClient.getConfirmOrder() }
    private lateinit var fc: FragmentCommunicator
    private var id = ""
    private var avatar = ""
    private var title = ""
    private var token = ""
    private var txId = ""
    private var actNo = ""
    private lateinit var address : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentConfirmOrderBinding.bind(view)
        fc = activity as FragmentCommunicator
        loadProfile()
        loadToken()
        Log.d("Pay Method", "id = $id, phone = $actNo txnId = $txId title = $title, token = $token, avatar = $avatar")

        binding.addressInfo.btnConfirmOrder.setOnClickListener {
            val name = binding.addressInfo.txtCusName.text.toString().trim()
            val phn = binding.addressInfo.txtCusPhone.text.toString().trim()
            val dis = binding.addressInfo.txtDistrict.text.toString().trim()
            val city = binding.addressInfo.txtCity.text.toString().trim()
            val ads = binding.addressInfo.txtAddress.text.toString().trim()

            if(name.isEmpty()){
                binding.addressInfo.txtCusName.error = "Name is Empty!"
                binding.addressInfo.txtCusName.requestFocus()
                return@setOnClickListener
            }

            if(phn.isEmpty()){
                binding.addressInfo.txtCusPhone.error = "Phone is Empty!"
                binding.addressInfo.txtCusPhone.requestFocus()
                return@setOnClickListener
            }

            if(dis.isEmpty()){
                binding.addressInfo.txtDistrict.error = "District is Empty!"
                binding.addressInfo.txtDistrict.requestFocus()
                return@setOnClickListener
            }

            if(city.isEmpty()){
                binding.addressInfo.txtCity.error = "City is Empty!"
                binding.addressInfo.txtCity.requestFocus()
                return@setOnClickListener
            }

            if(ads.isEmpty()){
                binding.addressInfo.txtAddress.error = "Address is Empty!"
                binding.addressInfo.txtAddress.requestFocus()
                return@setOnClickListener
            }

            address = "{\"name\": \"$name\", \"phone\": \"$phn\", \"district\": \"$dis\", \"city\": \"$city\", \"address\": \"$ads\"}"

            binding.addressInfo.btnConfirmOrder.isClickable = false
            orderBook()


        }

    }

    private fun loadProfile(){
        val sharedPreferences: SharedPreferences? = this.activity?.getSharedPreferences("BookAccess", Context.MODE_PRIVATE)
        val session = sharedPreferences?.getBoolean("session", false)
        if(session == true){
            id = sharedPreferences.getInt("id", 0).toString()
            avatar = sharedPreferences.getString("avatar", "")!!
            title = sharedPreferences.getString("title", "")!!
            txId = sharedPreferences.getString("txId", "")!!
            binding.bookInfo.txtBookTitle.text = title
            Picasso.get().load(avatar).into(binding.bookInfo.imgBookCover)
        }
    }

    private fun loadToken(){
        val sharedPreferences: SharedPreferences? = this.activity?.getSharedPreferences("Session", Context.MODE_PRIVATE)
        val session = sharedPreferences?.getBoolean("session", false)
        if(session == true){
            token = sharedPreferences.getString("token", "")!!
            actNo = sharedPreferences.getString("phone", "")!!

        }
        else{
            val intent = Intent(this.context, Login::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }


    private fun orderBook(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                Log.d("Book Order data", address)
                val response = orderConfirmation.bookOrder(id.toInt(), 1, "bKash", actNo, txId, address, "Bearer $token").awaitResponse()
                withContext(Dispatchers.Main){
                    if(response.body()?.success == true){
                        Toast.makeText( context,"Congratulation, Order is confirm.", Toast.LENGTH_SHORT).show()
                        fc.passData("OrderConfirm", -1)
                    }
                    Log.d("Book Order res= " , response.message().toString())
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText( context,"Internet Connection is not stable!!", Toast.LENGTH_SHORT).show()
                }
                Log.d("Book Order ex= " , e.message.toString())
            }
            binding.addressInfo.btnConfirmOrder.isClickable = true
        }
    }

}