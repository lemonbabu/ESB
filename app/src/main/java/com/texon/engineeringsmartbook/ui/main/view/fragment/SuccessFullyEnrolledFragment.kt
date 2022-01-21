package com.texon.engineeringsmartbook.ui.main.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.databinding.FragmentSuccessFullyEnrolledBinding
import com.texon.engineeringsmartbook.ui.main.viewModel.FragmentCommunicator
import kotlin.properties.Delegates

class SuccessFullyEnrolledFragment : Fragment(R.layout.fragment_success_fully_enrolled) {

    private lateinit var binding: FragmentSuccessFullyEnrolledBinding
    private lateinit var fc: FragmentCommunicator
    private var bookId by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSuccessFullyEnrolledBinding.bind(view)
        bookId = arguments?.getInt("id") ?: -1
        fc = activity as FragmentCommunicator

        if(bookId == -1){
            binding.txtMsg1.text = "Your Order is Confirmed, Thank you!"
            binding.txtMsg2.text = "We will be contact with you as soon as possible"
            binding.btnGoCourse.text = "Go To Home"
        }

        binding.btnGoCourse.setOnClickListener {
            if(bookId == -1){
                fc.passData("Home", bookId)
            } else{
                fc.passData("accessBook", bookId)
            }
        }

    }

}
