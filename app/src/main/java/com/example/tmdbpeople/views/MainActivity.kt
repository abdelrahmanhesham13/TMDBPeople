package com.example.tmdbpeople.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdbpeople.R
import com.example.tmdbpeople.databinding.ActivityMainBinding
import com.example.tmdbpeople.viewmodels.PopularPersonsViewModel
import com.example.tmdbpeople.views.adapters.PersonAdapter

class MainActivity : AppCompatActivity() {

    var mPopularPersonsViewModel: PopularPersonsViewModel? = null
    var mActivityBinding: ActivityMainBinding? = null
    var mPersonsAdapter: PersonAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mPopularPersonsViewModel =
            ViewModelProviders.of(this).get(PopularPersonsViewModel::class.java)
        setupViews()
        observeData()
    }

    private fun setupViews() {
        mPersonsAdapter = PersonAdapter(this)
        mActivityBinding?.personsRecycler?.layoutManager = LinearLayoutManager(this)
        mActivityBinding?.personsRecycler?.setHasFixedSize(true)
        mActivityBinding?.personsRecycler?.adapter = mPersonsAdapter
    }

    private fun observeData() {
        mPopularPersonsViewModel?.itemPagedList?.observe(this, Observer {
            mActivityBinding?.progressBar?.visibility = View.GONE
            mActivityBinding?.centerProgressBar?.visibility = View.GONE
            mPersonsAdapter?.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {

            return true
        }
        return false
    }

}
