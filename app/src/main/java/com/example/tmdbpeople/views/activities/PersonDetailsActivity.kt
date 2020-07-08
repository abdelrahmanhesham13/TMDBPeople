package com.example.tmdbpeople.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdbpeople.R
import com.example.tmdbpeople.databinding.ActivityPersonDetailsBinding
import com.example.tmdbpeople.models.responsemodels.PersonDetailsResponse
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.viewmodels.PersonDetailsViewModel
import com.example.tmdbpeople.viewmodels.PopularPersonsViewModel
import com.example.tmdbpeople.viewmodels.viewmodelfactory.CustomViewModelFactory
import com.example.tmdbpeople.views.adapters.PersonAdapter
import com.example.tmdbpeople.views.adapters.PersonDetailsAdapter

class PersonDetailsActivity : AppCompatActivity() , PersonAdapter.OnItemClicked {

    private lateinit var mPersonDetailsAdapter: PersonDetailsAdapter
    private var mActivityBinding : ActivityPersonDetailsBinding? = null
    private var mPersonDetailsViewModel : PersonDetailsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding = DataBindingUtil.setContentView(this,R.layout.activity_person_details)
        val viewModelFactory = CustomViewModelFactory(intent.getIntExtra(Constants.PERSON_ID_PATH , 0))
        mPersonDetailsViewModel =
            ViewModelProviders.of(this,viewModelFactory).get(PersonDetailsViewModel::class.java)
        setupViews()
        observeData()
    }

    private fun observeData() {
        mPersonDetailsViewModel?.personDetailsLiveData?.observe(this, object : Observer<PersonDetailsResponse?> {
            override fun onChanged(personDetailsResponse: PersonDetailsResponse?) {
                mActivityBinding?.progressBar?.visibility = View.GONE
                mPersonDetailsAdapter.setPersonDetailsResponse(personDetailsResponse)
            }
        })
    }

    private fun setupViews() {
        title = "Person Details"
        mPersonDetailsAdapter = PersonDetailsAdapter(this, ArrayList(),PersonDetailsResponse(),this)
        mActivityBinding?.detailsRecycler?.layoutManager = LinearLayoutManager(this)
        mActivityBinding?.detailsRecycler?.adapter = mPersonDetailsAdapter
    }

    override fun onItemClicked(id: Int?) {

    }
}
