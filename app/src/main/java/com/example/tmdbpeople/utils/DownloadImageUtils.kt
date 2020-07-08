package com.example.tmdbpeople.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.example.tmdbpeople.R
import com.example.tmdbpeople.networkutils.Constants
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

//Helper class to download image from url and save it on device storage using Picasso third-party library
object DownloadImageUtils {
    fun imageDownload(url: String? , ctx : Context) {
        Picasso.get()
            .load(Constants.IMAGE_BASE_URL_ORIGINAL + url)
            .into(getTarget(url,ctx))
    }

    //Function to create file and compress bitmap after download image
    private fun getTarget(url: String?,ctx: Context): Target {
        return object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
                Thread(Runnable {
                    val file =
                        File(Environment.getExternalStorageDirectory().absolutePath + "/" + url)
                    try {
                        file.createNewFile()
                        val ostream = FileOutputStream(file)
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream)
                        ostream.flush()
                        ostream.close()
                        printSuccessMessage(ctx , ctx.getString(R.string.image_downloaded))
                    } catch (e: IOException) {
                        e.printStackTrace()
                        printSuccessMessage(ctx , ctx.getString(R.string.failed_image_downloaded))
                    }
                }).start()
            }

            override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {
                e.printStackTrace()
                printSuccessMessage(ctx , ctx.getString(R.string.failed_image_downloaded))
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
        }
    }

    private fun printSuccessMessage(ctx : Context , message : String) {
        val handler = Handler(Looper.getMainLooper())
        handler.post(Runnable {
            Toast.makeText(ctx , message , Toast.LENGTH_LONG).show()
        })
    }
}