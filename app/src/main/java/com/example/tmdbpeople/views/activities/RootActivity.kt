package com.example.tmdbpeople.views.activities

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

open class RootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return false
    }

}