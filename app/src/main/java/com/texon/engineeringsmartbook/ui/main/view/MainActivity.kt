package com.texon.engineeringsmartbook.ui.main.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.databinding.ActivityMainBinding
import com.texon.engineeringsmartbook.ui.main.view.activities.ExoPlayer
import com.texon.engineeringsmartbook.ui.main.view.activities.VideoPlayer
import com.texon.engineeringsmartbook.ui.main.view.auth.Login
import com.texon.engineeringsmartbook.ui.main.view.fragment.*
import com.texon.engineeringsmartbook.ui.main.viewModel.FragmentCommunicator
import kotlinx.coroutines.DelicateCoroutinesApi
import java.net.NetworkInterface
import java.util.*


@DelicateCoroutinesApi
class MainActivity : AppCompatActivity(), FragmentCommunicator {

    private lateinit var binding: ActivityMainBinding
    private var backPress = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val nav = intent.getStringExtra("nav")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

//        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
//        val mac = wifiManager.connectionInfo.macAddress

//        val admin = DeviceAdminReceiver()
//        val devicepolicymanager = admin.getManager(applicationContext)
//        val name1 = admin.getWho(applicationContext)
//        if (devicepolicymanager.isAdminActive(name1)) {
//            val mac_address = devicepolicymanager.getWifiMacAddress(name1)
//            Log.e("macAddress", "" + mac_address)
//        }
//
//        val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
//        val wInfo = wifiManager.connectionInfo
//        val macAddress = wInfo.macAddress
//
//        val mac = getMacAddress()
//        Toast.makeText(applicationContext, macAddress, Toast.LENGTH_LONG).show()
//        Log.d("Mac", macAddress)

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

       loadProfilePic()

        binding.appBar.btnProfileMenu.setOnClickListener { menuProfile() }
        binding.appBar.btnHomeMenu.setOnClickListener { menuHome() }
        binding.appBar.btnScannerMenu.setOnClickListener {
            val intent = Intent(applicationContext, Scanner::class.java)
            startActivity(intent)
//            val intent = Intent(this, VideoPlayer::class.java)
//            //intent.putExtra("link", "JOJesiI1HTs")
//            startActivity(intent)
            //finish()
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
            Picasso.get().load(avatar).fit().into(binding.appBar.btnProfileMenu)
        }
        else{
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getMacAddress(): String {
        try {
            val all: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            for (nif in all) {
                if (!nif.name.equals("wlan0", ignoreCase = true)) continue
                val macBytes = nif.hardwareAddress ?: return "null"
                val res1 = StringBuilder()
                for (b in macBytes) {
                    res1.append(String.format("%02X:", b))
                }
                if (res1.isNotEmpty()) {
                    res1.deleteCharAt(res1.length - 1)
                }
                return res1.toString()
            }
        } catch (ex: Exception) {
        }
        return "02:00:00:00:00:00"
    }

}