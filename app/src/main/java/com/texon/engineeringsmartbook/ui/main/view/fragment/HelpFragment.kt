package com.texon.engineeringsmartbook.ui.main.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.databinding.FragmentHelpBinding
import com.texon.engineeringsmartbook.ui.main.viewModel.FragmentCommunicator
import android.content.Intent
import android.net.Uri


class HelpFragment : Fragment(R.layout.fragment_help) {

    private lateinit var binding: FragmentHelpBinding
    private lateinit var fc: FragmentCommunicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHelpBinding.bind(view)

        binding.txtWebsiteLink.setOnClickListener {
            val viewIntent = Intent("android.intent.action.VIEW",Uri.parse("https://engineeringsmartbook.com/"))
            startActivity(viewIntent)
        }

    }
}