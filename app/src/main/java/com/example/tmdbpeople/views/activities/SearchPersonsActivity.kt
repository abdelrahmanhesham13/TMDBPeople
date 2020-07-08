package com.example.tmdbpeople.views.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdbpeople.R
import com.example.tmdbpeople.dagger.component.DaggerPersonAdapterComponent
import com.example.tmdbpeople.dagger.component.PersonAdapterComponent
import com.example.tmdbpeople.dagger.modules.ContextModule
import com.example.tmdbpeople.dagger.modules.OnItemClickPersonModule
import com.example.tmdbpeople.databinding.ActivitySearchBinding
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.networkutils.LoadCallback
import com.example.tmdbpeople.viewmodels.SearchPersonsViewModel
import com.example.tmdbpeople.viewmodels.viewmodelfactory.CustomViewModelFactory
import com.example.tmdbpeople.views.adapters.PersonAdapter
import com.example.tmdbpeople.views.baseviews.BaseActivityWithViewModel
import kotlinx.android.synthetic.main.activity_popular_persons.*

class SearchPersonsActivity : BaseActivityWithViewModel<SearchPersonsViewModel , ActivitySearchBinding>() , PersonAdapter.OnItemClicked {

    lateinit var mPersonsAdapter : PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        observeData()
    }

    private fun observeData() {
        mActivityViewModel?.getErrorLiveData()?.observe(this, Observer {
            progressBar.visibility = View.GONE
            centerProgressBar.visibility = View.GONE
            Toast.makeText(this,it, Toast.LENGTH_LONG).show()
        })
        mActivityViewModel?.getStateLiveData()?.observe(this, Observer {
            when (it) {
                Constants.SUCCESS_STATE -> {
                    progressBar.visibility = View.GONE
                    centerProgressBar.visibility = View.GONE
                }
                Constants.LOAD_MORE_STATE -> {
                    progressBar.visibility = View.VISIBLE
                }
                Constants.FIRST_LOAD_STATE -> {
                    centerProgressBar.visibility = View.VISIBLE
                }
            }
        })

        mActivityViewModel?.personPagedList?.observe(this, Observer {
            mPersonsAdapter.submitList(it)
        })

    }

    private fun setupViews() {
        title = getString(R.string.search_for_person)
        injectAdapter()
        mActivityBinding?.searchResultsRecycler?.layoutManager = LinearLayoutManager(this)
        mActivityBinding?.searchResultsRecycler?.adapter = mPersonsAdapter

        mActivityBinding?.searchEditText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s!!.isNotEmpty()) {
                    mActivityViewModel?.doSearch(s.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    private fun injectAdapter() {
        val personAdapterComponent: PersonAdapterComponent = DaggerPersonAdapterComponent.builder()
            .contextModule(ContextModule(this))
            .onItemClickPersonModule(OnItemClickPersonModule(this))
            .build()

        mPersonsAdapter = personAdapterComponent.getPersonAdapter()
    }

    override fun onItemClicked(id: Int?) {
        startActivity(Intent(this,PersonDetailsActivity::class.java)
                .putExtra(Constants.PERSON_ID_PATH,id))
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_search
    }

    override fun initialiseViewModel(): SearchPersonsViewModel {
        return ViewModelProvider(this).get(SearchPersonsViewModel::class.java)
    }

    override fun enableBackButton(): Boolean {
        return true
    }
}
