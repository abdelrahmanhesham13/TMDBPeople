package com.example.tmdbpeople.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdbpeople.R
import com.example.tmdbpeople.dagger.component.adapters.DaggerPersonAdapterComponent
import com.example.tmdbpeople.dagger.component.adapters.PersonAdapterComponent
import com.example.tmdbpeople.dagger.modules.ContextModule
import com.example.tmdbpeople.dagger.modules.clickhandlers.OnPersonClickedModule
import com.example.tmdbpeople.utils.networkutils.Constants
import com.example.tmdbpeople.utils.PrintUtils
import com.example.tmdbpeople.viewmodels.PopularPersonsViewModel
import com.example.tmdbpeople.views.adapters.PersonAdapter
import com.example.tmdbpeople.views.baseviews.BaseActivityWithViewModel
import kotlinx.android.synthetic.main.activity_popular_persons.*
import javax.inject.Inject


class PopularPersonsActivity : BaseActivityWithViewModel<PopularPersonsViewModel>() , PersonAdapter.OnPersonClicked {

    @Inject
    lateinit var mPersonsAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        observeData()
    }

    private fun setupViews() {
        title = getString(R.string.popular_people)
        injectAdapter()
        persons_recycler.layoutManager = LinearLayoutManager(this)
        persons_recycler.adapter = mPersonsAdapter
    }

    private fun injectAdapter() {
        val personAdapterComponent: PersonAdapterComponent = DaggerPersonAdapterComponent.builder()
            .contextModule(ContextModule(this))
            .onPersonClickedModule(OnPersonClickedModule(this))
            .build()

        personAdapterComponent.inject(this)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            startActivity(Intent(this, SearchPersonsActivity::class.java))
            return true
        } else if (item.itemId == R.id.refresh) {
            centerProgressBar.visibility = View.VISIBLE
            mActivityViewModel.invalidate()
            return true
        }
        return false
    }

    override fun onPersonClicked(id: Int?) {
        startActivity(Intent(this,
            PersonDetailsActivity::class.java).putExtra(Constants.PERSON_ID_PATH,id))
    }

    override fun getLayoutResourceId(): Int {
       return R.layout.activity_popular_persons
    }

    override fun initialiseViewModel(): PopularPersonsViewModel {
        return ViewModelProvider(this).get(PopularPersonsViewModel::class.java)
    }

    override fun enableBackButton(): Boolean {
        return false
    }

}
