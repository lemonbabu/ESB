package com.texon.engineeringsmartbook.ui.main.view.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.data.api.ApiInterfaces
import com.texon.engineeringsmartbook.data.api.RetrofitClient
import com.texon.engineeringsmartbook.databinding.FragmentProfileUpdateBinding
import com.texon.engineeringsmartbook.ui.main.view.auth.Login
import com.texon.engineeringsmartbook.ui.main.viewModel.FragmentCommunicator
import kotlinx.coroutines.*
import retrofit2.awaitResponse
import java.lang.Exception

@DelicateCoroutinesApi
class ProfileUpdateFragment : Fragment(R.layout.fragment_profile_update) {

    private lateinit var binding: FragmentProfileUpdateBinding
    private lateinit var token: String
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var fragmentCommunicator: FragmentCommunicator
    private val update: ApiInterfaces.ProfileUpdateInterface by lazy { RetrofitClient.getUpdateProfile() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileUpdateBinding.bind(view)
        loadProfile()

        binding.btnUpdate.setOnClickListener {
            name = binding.txtUserName.text.toString().trim()
            email = binding.txtUserEmail.text.toString().trim()
            if (name.isEmpty()){
                binding.txtUserName.error = "User Name is Required"
                binding.txtUserName.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()){
                binding.txtUserEmail.error = "Email is Required"
                binding.txtUserEmail.requestFocus()
                return@setOnClickListener
            }

            binding.btnUpdate.isEnabled = false
            updateProfile()
        }
    }


    private fun loadProfile(){
        val sharedPreferences: SharedPreferences? = this.activity?.getSharedPreferences("Session", Context.MODE_PRIVATE)
        val session = sharedPreferences?.getBoolean("session", false)
        if(session == true){
            token = sharedPreferences.getString("token", "").toString()
            binding.txtUserEmail.setText(sharedPreferences.getString("email", ""))
            binding.txtUserName.setText(sharedPreferences.getString("name", ""))
            val avatar = sharedPreferences.getString("avatar", "")
            Picasso.get()
                .load(avatar)
                .placeholder(R.mipmap.ic_launcher)
                .into(binding.profileAvatar)
        }
        else{
            val intent = Intent(this.context, Login::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun updateProfile(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = update.updateProfile( name, email,  "Bearer $token").awaitResponse()
                withContext(Dispatchers.Main){
                    Log.d("Profile Updated", "$name $email $response")
                    if (response.body()?.success == true){
                        Toast.makeText(context, "Profile is Updated", Toast.LENGTH_SHORT).show()
                        val sharedPreferences = activity?.getSharedPreferences("Session", Context.MODE_PRIVATE)
                        val editor = sharedPreferences?.edit()
                        editor?.apply{
                            putBoolean("session", true)
                            putString("name", name)
                            putString("email", email)
                        }?.apply()

                        fragmentCommunicator = activity as FragmentCommunicator
                        fragmentCommunicator.passData("profile", id)
                    }else{
                        Toast.makeText(context, response.body()?.message.toString() + response.errorBody() , Toast.LENGTH_SHORT).show()
                    }
                }
            }catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context,"Internet Connection is not stable!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.btnUpdate.isEnabled = true
    }

    private fun changeAvatar(){

    }

}
