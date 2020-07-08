package com.example.tmdbpeople.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tmdbpeople.R
import com.example.tmdbpeople.databinding.ActivityMainBinding
import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.models.responsemodels.PopularPersonResponse
import com.example.tmdbpeople.viewmodels.PopularPersonsViewModel
import com.example.tmdbpeople.views.adapters.PersonsAdapter

class MainActivity : AppCompatActivity() , PersonsAdapter.OnItemClicked {

    var mPopularPersonsViewModel : PopularPersonsViewModel? = null
    var mActivityBinding : ActivityMainBinding? = null
    var mPersonsAdapter : PersonsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        mPopularPersonsViewModel = ViewModelProviders.of(this).get(PopularPersonsViewModel::class.java)
        setupViews()
        observeData()
    }

    private fun setupViews() {
        mPersonsAdapter = PersonsAdapter(ArrayList(),this,this)
        mActivityBinding?.personsRecycler?.layoutManager = LinearLayoutManager(this)
        mActivityBinding?.personsRecycler?.setHasFixedSize(true)
        mActivityBinding?.personsRecycler?.adapter = mPersonsAdapter
    }

    private fun observeData() {
        mPopularPersonsViewModel?.popularPersonsList?.observe(this, Observer {
            showData(it)
        })
    }

    private fun showData(it: PopularPersonResponse?) {
        mPersonsAdapter?.addItems(it?.persons as ArrayList<PersonModel>)
    }

    override fun onItemClicked(position: Int) {

    }

    override fun onLoadMore() {
        mPopularPersonsViewModel?.getMoreData()
    }
}
