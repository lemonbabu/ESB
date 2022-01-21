package com.texon.engineeringsmartbook.ui.main.view.fragment

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.texon.engineeringsmartbook.R
import com.texon.engineeringsmartbook.data.api.ApiInterfaces
import com.texon.engineeringsmartbook.data.api.MyAPI
import com.texon.engineeringsmartbook.data.api.RetrofitClient
import com.texon.engineeringsmartbook.utilits.UploadRequestBody
import com.texon.engineeringsmartbook.data.model.UploadResponse
import com.texon.engineeringsmartbook.databinding.FragmentProfileUpdateBinding
import com.texon.engineeringsmartbook.ui.main.view.auth.Login
import com.texon.engineeringsmartbook.ui.main.viewModel.FragmentCommunicator
import kotlinx.coroutines.*
import okhttp3.*
import rebus.permissionutils.PermissionEnum
import rebus.permissionutils.PermissionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*


@DelicateCoroutinesApi
class ProfileUpdateFragment : Fragment(R.layout.fragment_profile_update) {

    private lateinit var binding: FragmentProfileUpdateBinding
    private lateinit var token: String
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var fragmentCommunicator: FragmentCommunicator
    private val update: ApiInterfaces.ProfileUpdateInterface by lazy { RetrofitClient.getUpdateProfile() }
    private var selectedImage: Uri? = null

    private val PICK_IMAGE = 101

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileUpdateBinding.bind(view)
        loadProfile()

        PermissionManager.Builder()
            .permission(PermissionEnum.READ_EXTERNAL_STORAGE)
            .ask(this)

        binding.btnUpdate.setOnClickListener {
            name = binding.txtUserName.text.toString().trim()
            email = binding.txtUserEmail.text.toString().trim()
            if (name.isEmpty()){
                binding.txtUserName.error = "User Name is Required"
                binding.txtUserName.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()){
                binding.txtUserEmail.error = "Email is Required"
                binding.txtUserEmail.requestFocus()
                return@setOnClickListener
            }

            binding.btnUpdate.isEnabled = false
            updateProfile()
        }

        binding.btnUploadAvatar.setOnClickListener {
            Intent(Intent.ACTION_PICK).also {
                it.type="image/*"
                val mimeTypes = arrayOf("image/jpeg", "image/png", "image/jpg")
                it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                startActivityForResult(it,PICK_IMAGE)
            }
            //imagePickerActivityResult.launch(imagePickerIntent)
        }

    }

    private fun loadProfile(){
        val sharedPreferences: SharedPreferences? = this.activity?.getSharedPreferences("Session", Context.MODE_PRIVATE)
        val session = sharedPreferences?.getBoolean("session", false)
        if(session == true){
            token = sharedPreferences.getString("token", "").toString()
            binding.txtUserEmail.setText(sharedPreferences.getString("email", ""))
            binding.txtUserName.setText(sharedPreferences.getString("name", ""))
            val avatar = sharedPreferences.getString("avatar", "")
            Picasso.get().load(avatar).fit().into(binding.profileAvatar)
        }
        else{
            val intent = Intent(this.context, Login::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }

    private fun updateProfile(){
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = update.updateProfile( name, email,  "Bearer $token").awaitResponse()
                withContext(Dispatchers.Main){
                    Log.d("Profile Updated", "$name $email $response")
                    if (response.body()?.success == true){
                        Toast.makeText(context, "Profile is Updated", Toast.LENGTH_SHORT).show()
                        val sharedPreferences = activity?.getSharedPreferences("Session", Context.MODE_PRIVATE)
                        val editor = sharedPreferences?.edit()
                        editor?.apply{
                            putBoolean("session", true)
                            putString("name", name)
                            putString("email", email)
                        }?.apply()

                        fragmentCommunicator = activity as FragmentCommunicator
                        fragmentCommunicator.passData("profile", id)
                    }else{
                        Toast.makeText(context, response.body()?.message.toString() + response.errorBody() , Toast.LENGTH_SHORT).show()
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {

            when(requestCode){
                PICK_IMAGE ->{
                    selectedImage = data?.data
                    binding.profileAvatar.setImageURI(selectedImage)
                    uploadImage()
                }
            }
        }
    }

    private fun uploadImage(){
        if (selectedImage == null){
            Toast.makeText(context, "Image is not selected", Toast.LENGTH_SHORT).show()
            return
        }

        val parcelFileDescriptor =
            context?.contentResolver?.openFileDescriptor(selectedImage!!, "r", null) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(context?.cacheDir, context?.contentResolver?.getFileName(selectedImage!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        val body = UploadRequestBody(file, "image")
        MyAPI().uploadImage(
            MultipartBody.Part.createFormData("avatar", file.name, body),
            "Bearer $token"
        ).enqueue(object : Callback<UploadResponse> {
            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                //Toast.makeText(context, "Failed to upload" + t.message, Toast.LENGTH_SHORT).show()
                Log.d("Avatar= ", "Failed to Upload " + t.message)
            }
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                response.body()?.let {
                    //Toast.makeText(context, it.data.avatar, Toast.LENGTH_SHORT).show()
                    Log.d("Avatar= ", "Upload to Successfully" + it.data.avatar)
                    val sharedPreferences = activity?.getSharedPreferences("Session", Context.MODE_PRIVATE)
                    val editor = sharedPreferences?.edit()
                    editor?.apply{
                        putString("avatar", it.data.avatar)
                    }?.apply()
                }
            }
        })

    }

    private fun ContentResolver.getFileName(fileUri: Uri): String {
        var name = ""
        val returnCursor = this.query(fileUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }


}



