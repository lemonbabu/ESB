package com.texon.engineeringsmartbook.ui.main.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.ui.main.view.activities.Scanner
import com.texon.engineeringsmartbook.databinding.ActivityMainBinding
import com.texon.engineeringsmartbook.ui.main.view.auth.Login
import com.texon.engineeringsmartbook.ui.main.view.fragment.*
import com.texon.engineeringsmartbook.ui.main.viewModel.FragmentCommunicator
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class MainActivity : AppCompatActivity(), FragmentCommunicator {

    private lateinit var binding: ActivityMainBinding
    private var backPress = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nav = intent.getStringExtra("nav")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        menuHome()
        loadProfilePic()

        when (nav) {
            "home" -> {
                menuHome()
            }
            "bookAccess" -> {
                menuBookAccess()
            }
            "orderNow" -> {
                menuOrderNow()
            }
            else -> {
                menuHome()
            }
        }

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


    private fun menuBookAccess(){
        replaceFragment(BookAccessFragment())
        binding.appBar.btnHomeMenu.setImageResource(R.drawable.ic_home)
        backPress = true
    }

    private fun menuOrderNow(){
        replaceFragment(PayMethodFragment())
        binding.appBar.btnHomeMenu.setImageResource(R.drawable.ic_home)
        backPress = true
    }

    private fun menuConfirmOrder(){
        replaceFragment(ConfirmOrderFragment())
        binding.appBar.btnHomeMenu.setImageResource(R.drawable.ic_home)
        backPress = true
    }

    private fun menuBookSuccessFullEnrollment(){
        replaceFragment(SuccessFullyEnrolledFragment())
        binding.appBar.btnHomeMenu.setImageResource(R.drawable.ic_home)
        backPress = true
    }

    private fun menuProfileUpdate(){
        replaceFragment(ProfileUpdateFragment())
        binding.appBar.btnHomeMenu.setImageResource(R.drawable.ic_home)
        backPress = true
    }

    private fun menuPasswordUpdate(){
        replaceFragment(UpdatePasswordFragment())
        binding.appBar.btnHomeMenu.setImageResource(R.drawable.ic_home)
        backPress = true
    }


    private fun menuHome(){
        replaceFragment(DashboardFragment())
        binding.appBar.btnHomeMenu.setImageResource(R.drawable.ic_home_primary_color)
        //binding.appBar.btnProfileMenu.setImageResource(R.drawable.ic_user_profile)
        binding.headerTitle.btnBackMenu.visibility = View.INVISIBLE
        binding.headerTitle.titleBarHeading.visibility = View.VISIBLE
        backPress = false
    }

    private fun menuProfile(){
        replaceFragment(ProfileFragment())
        binding.appBar.btnHomeMenu.setImageResource(R.drawable.ic_home)
        //binding.appBar.btnProfileMenu.setImageResource(R.drawable.ic_user_profile_primary_color)
        backPress = true
    }

    override fun passData(textView: String, id: Int) {
        val bundle = Bundle()
        bundle.putString("message", textView)
        bundle.putInt("id", id)

        when (textView) {
            "BookAccess" -> {
                val fragment = BookAccessFragment()
                fragment.arguments = bundle
                replaceFragment(fragment)
            }
            "successFullyEnrolled" -> {
                menuBookSuccessFullEnrollment()
            }
            "editProfile" -> {
                menuProfileUpdate()
            }
            "updatePassword" -> {
                menuPasswordUpdate()
            }
            "profile" -> {
                menuProfile()
            }
            "confirmOrder" -> {
                menuConfirmOrder()
             }
            "accessBook" -> {
                val fragment = BookProfileFragment()
                fragment.arguments = bundle
                replaceFragment(fragment)
                binding.appBar.btnHomeMenu.setImageResource(R.drawable.ic_home)
                backPress = true
            }
            else -> {
                menuHome()
            }
        }

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


    private fun loadProfilePic(){
        val sharedPreferences: SharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE)
        val session = sharedPreferences.getBoolean("session", false)
        if(session){
            val avatar = sharedPreferences.getString("avatar", "")
            Picasso.get()
                .load(avatar)
                .placeholder(R.mipmap.ic_launcher)
                .into(binding.appBar.btnProfileMenu)
        }
        else{
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }
    }


}