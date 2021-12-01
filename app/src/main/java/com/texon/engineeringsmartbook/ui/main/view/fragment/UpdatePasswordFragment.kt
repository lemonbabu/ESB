package com.texon.engineeringsmartbook.ui.main.view.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.data.api.ApiInterfaces
import com.texon.engineeringsmartbook.data.api.RetrofitClient
import com.texon.engineeringsmartbook.databinding.FragmentUpdatePasswordBinding
import com.texon.engineeringsmartbook.ui.main.view.auth.Login
import com.texon.engineeringsmartbook.ui.main.viewModel.FragmentCommunicator
import kotlinx.coroutines.*
import retrofit2.awaitResponse
import java.lang.Exception

@DelicateCoroutinesApi
class UpdatePasswordFragment : Fragment(R.layout.fragment_update_password) {

    private lateinit var binding: FragmentUpdatePasswordBinding
    private lateinit var token: String
    private lateinit var pass: String
    private lateinit var newPass: String
    private lateinit var fragmentCommunicator: FragmentCommunicator
    private val update: ApiInterfaces.PasswordUpdateInterface by lazy { RetrofitClient.getUpdatePassword() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUpdatePasswordBinding.bind(view)

        loadProfile()

        binding.btnUpdate.setOnClickListener {
            pass = binding.txtPassword.text.toString().trim()
            newPass = binding.txtNewPassword.text.toString().trim()
            val conPass = binding.txtConPassword.text.toString().trim()

            if (pass.isEmpty()){
                binding.txtPassword.error = "Old password must be needed!!"
                binding.txtPassword.requestFocus()
                return@setOnClickListener
            }

            if (newPass.isEmpty()){
                binding.txtNewPassword.error = "New password is not allow empty!!"
                binding.txtNewPassword.requestFocus()
                return@setOnClickListener
            }

            if (newPass != conPass){
                binding.txtConPassword.error = "Password not Match!!"
                binding.txtConPassword.requestFocus()
                return@setOnClickListener
            }

            binding.btnUpdate.isEnabled = false
            updatePassword()
        }


    }

    private fun loadProfile(){
        val sharedPreferences: SharedPreferences? = this.activity?.getSharedPreferences("Session", Context.MODE_PRIVATE)
        val session = sharedPreferences?.getBoolean("session", false)
        if(session == true){
            token = sharedPreferences.getString("token", "").toString()
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

    private fun updatePassword(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = update.updatePassword( pass, newPass, newPass,  "Bearer $token").awaitResponse()
                withContext(Dispatchers.Main){
                    Log.d("Profile Updated", "$pass $newPass $response")
                    if (response.body()?.success == true){
                        Toast.makeText(context, "Password is Updated", Toast.LENGTH_SHORT).show()

                        fragmentCommunicator = activity as FragmentCommunicator
                        fragmentCommunicator.passData("profile", 0)
                    }else{
                        Toast.makeText(context, response.body()?.message.toString() + response.errorBody() , Toast.LENGTH_SHORT).show()
                        binding.txtPassword.error = "Old password is not correct!!"
                        binding.txtPassword.requestFocus()
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

}