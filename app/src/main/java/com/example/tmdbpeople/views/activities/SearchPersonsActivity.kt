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
import kotlinx.android.synthetic.main.activity_popular_persons.*

class SearchPersonsActivity : RootActivity() , LoadCallback , PersonAdapter.OnItemClicked {

    private var mSearchPersonsViewModel: SearchPersonsViewModel? = null
    var mActivityBinding : ActivitySearchBinding? = null
    var mPersonsAdapter : PersonAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding = DataBindingUtil.setContentView(this,R.layout.activity_search)
        val customViewModelFactory = CustomViewModelFactory(this)
        mSearchPersonsViewModel = ViewModelProvider(this,customViewModelFactory).get(SearchPersonsViewModel::class.java)
        setupViews()
        observeData()
    }

    private fun observeData() {
        mSearchPersonsViewModel?.personPagedList?.observe(this, Observer {
            mPersonsAdapter?.submitList(it)
        })
    }

    private fun setupViews() {
        title = getString(R.string.search_for_person)
        injectAdapter()
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

    private fun injectAdapter() {
        val personAdapterComponent: PersonAdapterComponent = DaggerPersonAdapterComponent.builder()
            .contextModule(ContextModule(this))
            .onItemClickPersonModule(OnItemClickPersonModule(this))
            .build()

        mPersonsAdapter = personAdapterComponent.getPersonAdapter()
    }


    override fun onError(message: String) {
        runOnUiThread(Runnable {
            progressBar.visibility = View.GONE
            centerProgressBar.visibility = View.GONE
        })
        Toast.makeText(this,message, Toast.LENGTH_LONG).show()
    }

    override fun onSuccess() {
        runOnUiThread(Runnable {
            progressBar.visibility = View.GONE
            centerProgressBar.visibility = View.GONE
        })
    }

    override fun onLoadMore() {
        runOnUiThread(Runnable {
            progressBar.visibility = View.VISIBLE
        })
    }

    override fun onFirstLoad() {
        runOnUiThread(Runnable {
            centerProgressBar.visibility = View.VISIBLE
        })
    }

    override fun onItemClicked(id: Int?) {
        startActivity(
            Intent(this,PersonDetailsActivity::class.java)
                .putExtra(Constants.PERSON_ID_PATH,id))
    }
}
