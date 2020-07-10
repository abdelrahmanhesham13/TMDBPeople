package com.example.tmdbpeople.viewmodels

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.example.tmdbpeople.R
import com.example.tmdbpeople.utils.networkutils.Constants
import com.example.tmdbpeople.utils.ImageUtils
import com.squareup.picasso.Picasso

class ImageViewerViewModel(application: Application) : AndroidViewModel(application) {

    fun loadImage(intent : Intent, imageView : ImageView) {
        Picasso.get().load(Constants.IMAGE_BASE_URL_ORIGINAL + intent.getStringExtra(Constants.IMAGE_KEY))
            .placeholder(R.drawable.im_placeholder)
            .error(R.drawable.im_placeholder)
            .into(imageView)
    }

    //Check permission before download image if not granted request it at runtime
    fun checkPermission(activity : Activity , intent: Intent) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
            )
        } else {
            ImageUtils.imageDownload(intent.getStringExtra(Constants.IMAGE_KEY),activity)
        }
    }

    companion object {
        private const val PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: Int = 1
    }

    //after permission has been granted download image and save it to phone
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray , intent: Intent , context: Context) {
        when (requestCode) {
            PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    ImageUtils.imageDownload(intent.getStringExtra(Constants.IMAGE_KEY),context)
                } else {
                    Toast.makeText(context,context.getString(R.string.permission_denied), Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

}