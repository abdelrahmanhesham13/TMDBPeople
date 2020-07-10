package com.example.tmdbpeople.views.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.example.tmdbpeople.R
import com.example.tmdbpeople.viewmodels.ImageViewerViewModel
import com.example.tmdbpeople.views.baseviews.BaseActivityWithViewModel
import kotlinx.android.synthetic.main.activity_image_viewer.*

class ImageViewerActivity : BaseActivityWithViewModel<ImageViewerViewModel>() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = getString(R.string.person_image)
        mActivityViewModel.loadImage(intent,person_image)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_image_viewer,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.download) {
            mActivityViewModel.checkPermission(this,intent)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        mActivityViewModel.onRequestPermissionsResult(requestCode,permissions,grantResults ,intent,this)
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
