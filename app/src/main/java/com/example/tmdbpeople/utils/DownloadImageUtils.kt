package com.example.tmdbpeople.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import com.example.tmdbpeople.R
import com.example.tmdbpeople.utils.networkutils.ConnectionUtils
import com.example.tmdbpeople.utils.networkutils.Constants
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

//Helper class to download image from url and save it on device storage using Picasso third-party library
object DownloadImageUtils {
    fun imageDownload(url: String? , ctx : Context) {
        if (ConnectionUtils.isOnline(ctx)) {
            Picasso.get()
                .load(Constants.IMAGE_BASE_URL_ORIGINAL + url)
                .into(getTarget(url, ctx))
        } else {
            PrintUtils.printMessage(ctx, ctx.getString(R.string.failed_image_downloaded))
        }
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
                        PrintUtils.printMessage(ctx , ctx.getString(R.string.image_downloaded))
                    } catch (e: IOException) {
                        e.printStackTrace()
                        PrintUtils.printMessage(ctx , ctx.getString(R.string.failed_image_downloaded))
                    }
                }).start()
            }

            override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {
                e.printStackTrace()
                PrintUtils.printMessage(ctx , ctx.getString(R.string.failed_image_downloaded))
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
        }
    }
}