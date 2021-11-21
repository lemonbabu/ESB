package com.texon.engineeringsmartbook.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.Scanner
import com.texon.engineeringsmartbook.databinding.ActivityMainBinding
import com.texon.engineeringsmartbook.view.fragment.DashboardFragment
import com.texon.engineeringsmartbook.view.fragment.ProfileFragment
import com.texon.engineeringsmartbook.viewModel.FragmentCommunicator
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity(), FragmentCommunicator {

    private lateinit var binding: ActivityMainBinding
    private var backPress = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        menuHome()



        binding.appBar.btnProfileMenu.setOnClickListener { menuProfile() }
        binding.appBar.btnHomeMenu.setOnClickListener { menuHome() }
        binding.appBar.btnScannerMenu.setOnClickListener {
            val intent = Intent(applicationContext, Scanner::class.java)
            startActivity(intent)
        }
        binding.headerTitle.btnBackMenu.setOnClickListener { menuHome() }

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frgContainer, fragment)
        fragmentTransaction.commit()
        binding.headerTitle.btnBackMenu.visibility = View.VISIBLE
        binding.headerTitle.titleBarHeading.visibility = View.INVISIBLE
    }


    private fun menuHome(){
        replaceFragment(DashboardFragment())
        binding.appBar.btnHomeMenu.setImageResource(R.drawable.ic_home_primary_color)
        binding.appBar.btnProfileMenu.setImageResource(R.drawable.ic_user_profile)
        binding.headerTitle.btnBackMenu.visibility = View.INVISIBLE
        binding.headerTitle.titleBarHeading.visibility = View.VISIBLE
        backPress = false
    }

    private fun menuProfile(){
        replaceFragment(ProfileFragment())
        binding.appBar.btnHomeMenu.setImageResource(R.drawable.ic_home)
        binding.appBar.btnProfileMenu.setImageResource(R.drawable.ic_user_profile_primary_color)
        backPress = true
    }

    override fun passData(textView: String) {
        val bundle = Bundle()
        bundle.putString("message", textView)

        //val transaction = this.supportFragmentManager.beginTransaction()

//        when (textView) {
//            "btnDamage" -> {
//                startActivity(Intent(this, DamageIssue::class.java))
//            }
//            "btnComplain" -> {
//                startActivity(Intent(this, ComplainView::class.java))
//            }
//            else -> {
//                val fragment = FragmentOrderList()
//                fragment.arguments = bundle
//
//                replaceFragment(fragment)
//            }
//        }

    }

    override fun onBackPressed() {
        if(!backPress){
        android.app.AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Closing Activity")
            .setMessage("Are you sure? Do you want to exit this app?")
            .setPositiveButton("Yes") { _, _ -> finish() }
            .setNegativeButton("No", null)
            .show()
        }else{
            menuHome()
        }
    }


}