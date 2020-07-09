package com.example.tmdbpeople.views.activities

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.tmdbpeople.R
import com.example.tmdbpeople.databinding.ActivityImageViewerBinding
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.utils.DownloadImageUtils
import com.example.tmdbpeople.viewmodels.ImageViewerViewModel
import com.example.tmdbpeople.views.baseviews.BaseActivityWithViewModel
import com.squareup.picasso.Picasso

class ImageViewerActivity : BaseActivityWithViewModel<ImageViewerViewModel , ActivityImageViewerBinding>() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.person_image)
        mActivityViewModel?.loadImage(intent,mActivityBinding?.personImage)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_image_viewer,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.download) {
            mActivityViewModel?.checkPermission(this,intent)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        mActivityViewModel?.onRequestPermissionsResult(requestCode,permissions,grantResults ,intent,this)
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_image_viewer;
    }

    override fun initialiseViewModel(): ImageViewerViewModel {
        return ViewModelProvider(this).get(ImageViewerViewModel::class.java)
    }

    override fun enableBackButton(): Boolean {
        return true
    }

}
