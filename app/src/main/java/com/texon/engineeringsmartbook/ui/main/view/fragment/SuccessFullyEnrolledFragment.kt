package com.texon.engineeringsmartbook.ui.main.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.databinding.FragmentSuccessFullyEnrolledBinding

class SuccessFullyEnrolledFragment : Fragment(R.layout.fragment_success_fully_enrolled) {

    private lateinit var binding: FragmentSuccessFullyEnrolledBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSuccessFullyEnrolledBinding.bind(view)
    }

}
