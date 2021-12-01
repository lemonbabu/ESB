package com.texon.engineeringsmartbook.ui.main.view.fragment

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.squareup.picasso.Picasso
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.databinding.FragmentProfileBinding
import com.texon.engineeringsmartbook.ui.main.view.auth.Login
import com.texon.engineeringsmartbook.ui.main.viewModel.FragmentCommunicator
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var fragmentCommunicator: FragmentCommunicator
    private lateinit var token: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        fragmentCommunicator = activity as FragmentCommunicator
        loadProfile()

        binding.btnLogOut.setOnClickListener {
            val sharedPreferences: SharedPreferences? = this.activity?.getSharedPreferences("Session", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.apply{
                putBoolean("session", false)
                putBoolean("remember", false)
            }?.apply()

            val intent = Intent(this.context, Login::class.java)
            startActivity(intent)
            activity?.finish()
        }

        binding.btnEditProfile.setOnClickListener {
            fragmentCommunicator.passData("editProfile", 0)
        }

        binding.txtChangePassword.setOnClickListener {
            fragmentCommunicator.passData("updatePassword", 0)
        }

    }


    private fun loadProfile(){
        val sharedPreferences: SharedPreferences? = this.activity?.getSharedPreferences("Session", Context.MODE_PRIVATE)
        val session = sharedPreferences?.getBoolean("session", false)
        if(session == true){
            token = sharedPreferences.getString("token", "").toString()
            binding.txtUserEmail.text = sharedPreferences.getString("email", "")
            binding.txtUserName.text = sharedPreferences.getString("name", "")
            binding.txtUserPhone.text = sharedPreferences.getString("phone", "")
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


}