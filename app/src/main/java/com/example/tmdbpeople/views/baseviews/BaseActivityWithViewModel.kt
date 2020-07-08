package com.example.tmdbpeople.views.baseviews

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel


//BaseActivityWithViewModel is parent activity to open up button android handle its click on all Activities
//and to initialise view model and data binding of all activities
abstract class BaseActivityWithViewModel<V : ViewModel , D : ViewDataBinding> : AppCompatActivity() {

    protected var mActivityBinding: D? = null
    protected var mActivityViewModel: V? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (enableBackButton()) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
        }
        mActivityBinding = DataBindingUtil.setContentView(this,getLayoutResourceId())
        mActivityViewModel = initialiseViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return false
    }

    protected abstract fun enableBackButton() : Boolean

    protected abstract fun getLayoutResourceId(): Int

    protected abstract fun initialiseViewModel(): V

}