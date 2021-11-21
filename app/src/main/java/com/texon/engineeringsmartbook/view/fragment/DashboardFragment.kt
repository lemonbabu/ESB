package com.texon.engineeringsmartbook.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var binding: FragmentDashboardBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)

    }

}