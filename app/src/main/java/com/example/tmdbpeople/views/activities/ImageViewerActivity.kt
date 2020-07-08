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
import com.example.tmdbpeople.R
import com.example.tmdbpeople.databinding.ActivityImageViewerBinding
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.utils.DownloadImageUtils
import com.squareup.picasso.Picasso

class ImageViewerActivity : RootActivity() {

    private val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: Int = 1
    lateinit var mActivityBinding: ActivityImageViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_viewer)
        title = "Person Image"
        loadImage(intent.getStringExtra(Constants.IMAGE_KEY))
    }

    private fun loadImage(image: String?) {
        Picasso.get().load(Constants.IMAGE_BASE_URL_ORIGINAL + image)
            .placeholder(R.drawable.im_placeholder)
            .error(R.drawable.im_placeholder)
            .into(mActivityBinding.personImage)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_image_viewer,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.download) {
            checkPermission()
            return true
        } else if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return false
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
        } else {
            DownloadImageUtils.imageDownload(intent.getStringExtra(Constants.IMAGE_KEY),this)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    DownloadImageUtils.imageDownload(intent.getStringExtra(Constants.IMAGE_KEY),this)
                } else {
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

}
