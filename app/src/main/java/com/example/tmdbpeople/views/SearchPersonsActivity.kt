package com.example.tmdbpeople.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdbpeople.R
import com.example.tmdbpeople.databinding.ActivitySearchBinding
import com.example.tmdbpeople.viewmodels.PopularPersonsViewModel
import com.example.tmdbpeople.viewmodels.SearchPersonsViewModel
import com.example.tmdbpeople.views.adapters.PersonAdapter

class SearchPersonsActivity : AppCompatActivity() {

    private var mSearchPersonsViewModel: SearchPersonsViewModel? = null
    var mActivityBinding : ActivitySearchBinding? = null
    var mPersonsAdapter : PersonAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding = DataBindingUtil.setContentView(this,R.layout.activity_search)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        mSearchPersonsViewModel = ViewModelProviders.of(this).get(SearchPersonsViewModel::class.java)
        setupViews()
        observeData()
    }

    private fun observeData() {
        mSearchPersonsViewModel?.personPagedList?.observe(this, Observer {
//            mActivityBinding?.progressBar?.visibility = View.GONE
//            mActivityBinding?.centerProgressBar?.visibility = View.GONE
            mPersonsAdapter?.submitList(it)
        })
    }

    private fun setupViews() {
        mPersonsAdapter = PersonAdapter(this)
        mActivityBinding?.searchResultsRecycler?.layoutManager = LinearLayoutManager(this)
        mActivityBinding?.searchResultsRecycler?.setHasFixedSize(true)
        mActivityBinding?.searchResultsRecycler?.adapter = mPersonsAdapter

        mActivityBinding?.searchEditText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (!s?.isEmpty()!!) {
                    mSearchPersonsViewModel?.doSearch(s.toString())
                } else {
                    mPersonsAdapter?.submitList(null)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return false
    }
}
