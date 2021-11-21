package com.texon.engineeringsmartbook.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)

    }


}