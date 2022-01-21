package com.texon.engineeringsmartbook.utilits

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Intent
import java.io.InputStream
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream


object FileUtils {

    private fun getFileUriFromIntentOnActivityResult(intent: Intent?): Uri? {
        intent?.data?.let { uri ->
            return uri
        }
        return null
    }


    // For bitmap image
    fun getImageBitmapFromIntentOnActivityResult(intent: Intent?, context: Context): Uri? {
        try {
            val filePath = getFileUriFromIntentOnActivityResult(intent) ?: return null
            val inputStream: InputStream =
                context.contentResolver.openInputStream(filePath) ?: return null
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            return filePath
        } catch (e: Exception) {
            Log.d("", "Could no upload")
            return null
        }
    }

}