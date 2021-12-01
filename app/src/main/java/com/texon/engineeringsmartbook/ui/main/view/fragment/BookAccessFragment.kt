package com.texon.engineeringsmartbook.ui.main.view.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.data.api.ApiInterfaces
import com.texon.engineeringsmartbook.data.api.RetrofitClient
import com.texon.engineeringsmartbook.databinding.FragmentBookAccessBinding
import com.texon.engineeringsmartbook.ui.main.view.auth.Login
import com.texon.engineeringsmartbook.ui.main.viewModel.FragmentCommunicator
import kotlinx.coroutines.*
import retrofit2.awaitResponse
import java.lang.Exception

@DelicateCoroutinesApi
class BookAccessFragment : Fragment(R.layout.fragment_book_access) {

    private lateinit var binding: FragmentBookAccessBinding
    private var token = ""
    private val bookAccessBySerial: ApiInterfaces.BookAccessBySerialInterface by lazy { RetrofitClient.getBookAccessBySerial() }
    private lateinit var fragmentCommunicator: FragmentCommunicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookAccessBinding.bind(view )

        loadToken()
        val id = loadProfile()
        if(id == 0){
            val intent = Intent(this.context, Login::class.java)
            startActivity(intent)
            activity?.finish()
        }
        Log.d("BookAccess = ", id.toString())

        binding.btnApply.setOnClickListener {
            val serial = binding.txtBookSerial.text.toString().trim()
            if (serial.isEmpty()){
                binding.txtBookSerial.error = "Serial Number Required"
                binding.txtBookSerial.requestFocus()
                return@setOnClickListener
            }
            binding.btnApply.isEnabled = false
            getAccess(id, serial.toLong())
        }
    }

    private fun loadProfile(): Int{
        val sharedPreferences: SharedPreferences? = this.activity?.getSharedPreferences("BookAccess", Context.MODE_PRIVATE)
        val session = sharedPreferences?.getBoolean("session", false)
        return if(session == true){
            sharedPreferences.getInt("id", 0)

        } else{
            0
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

    private fun getAccess(id: Int, serial: Long){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = bookAccessBySerial.bookAccessBySerial( id, serial,  "Bearer $token").awaitResponse()
                withContext(Dispatchers.Main){
                    Log.d("Book Access", "$id $serial $response")
                    if (response.body()?.success == true){
                        Toast.makeText(context, response.body()?.data?.message.toString(), Toast.LENGTH_SHORT).show()
                        fragmentCommunicator = activity as FragmentCommunicator
                        fragmentCommunicator.passData("successFullyEnrolled", id)
                    }else{
                        //Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                        binding.btnApply.isEnabled = true
                        binding.txtBookSerial.error = "Serial Number is not valid"
                        binding.txtBookSerial.requestFocus()
                    }
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.btnApply.isEnabled = true
                    Toast.makeText(context,"Internet Connection is not stable!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnApply.isEnabled = true
    }

}


