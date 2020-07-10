package com.example.tmdbpeople.views.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdbpeople.R
import com.example.tmdbpeople.dagger.component.adapters.DaggerPersonAdapterComponent
import com.example.tmdbpeople.dagger.component.adapters.PersonAdapterComponent
import com.example.tmdbpeople.dagger.modules.ContextModule
import com.example.tmdbpeople.dagger.modules.clickhandlers.OnPersonClickedModule
import com.example.tmdbpeople.databinding.ActivitySearchBinding
import com.example.tmdbpeople.utils.networkutils.Constants
import com.example.tmdbpeople.utils.PrintUtils
import com.example.tmdbpeople.viewmodels.SearchPersonsViewModel
import com.example.tmdbpeople.views.adapters.PersonAdapter
import com.example.tmdbpeople.views.baseviews.BaseActivityWithViewModel
import kotlinx.android.synthetic.main.activity_popular_persons.*
import kotlinx.android.synthetic.main.activity_popular_persons.centerProgressBar
import kotlinx.android.synthetic.main.activity_popular_persons.progressBar
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

class SearchPersonsActivity : BaseActivityWithViewModel<SearchPersonsViewModel>() , PersonAdapter.OnPersonClicked , TextWatcher {

    @Inject
    lateinit var mPersonsAdapter : PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        observeData()
    }

    private fun observeData() {
        mActivityViewModel.getErrorLiveData()?.observe(this, Observer {
            progressBar.visibility = View.GONE
            centerProgressBar.visibility = View.GONE
            PrintUtils.printMessage(this,it)
        })
        mActivityViewModel.getStateLiveData()?.observe(this, Observer {
            when (it!!) {
                Constants.State.SUCCESS_STATE -> {
                    progressBar.visibility = View.GONE
                    centerProgressBar.visibility = View.GONE
                }
                Constants.State.LOAD_MORE_STATE -> {
                    progressBar.visibility = View.VISIBLE
                }
                Constants.State.FIRST_LOAD_STATE -> {
                    centerProgressBar.visibility = View.VISIBLE
                }
            }
        })

        mActivityViewModel.personPagedList.observe(this, Observer {
            mPersonsAdapter.submitList(it)
        })

    }

    private fun setupViews() {
        title = getString(R.string.search_for_person)
        injectAdapter()
        search_results_recycler.layoutManager = LinearLayoutManager(this)
        search_results_recycler.adapter = mPersonsAdapter

        search_edit_text.addTextChangedListener(this)
    }

    private fun injectAdapter() {
        val personAdapterComponent: PersonAdapterComponent = DaggerPersonAdapterComponent.builder()
            .contextModule(ContextModule(this))
            .onPersonClickedModule(
                OnPersonClickedModule(
                    this
                )
            )
            .build()

        personAdapterComponent.inject(this)
    }

    override fun onPersonClicked(id: Int?) {
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

    override fun afterTextChanged(s: Editable?) {
        if (s!!.isNotEmpty()) {
            mActivityViewModel.doSearch(s.toString())
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }
}
