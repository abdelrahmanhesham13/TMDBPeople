package com.example.tmdbpeople.views.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.example.tmdbpeople.databinding.ActivityPopularPersonsBinding
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.networkutils.LoadCallback
import com.example.tmdbpeople.viewmodels.PopularPersonsViewModel
import com.example.tmdbpeople.viewmodels.viewmodelfactory.CustomViewModelFactory
import com.example.tmdbpeople.views.adapters.PersonAdapter
import com.example.tmdbpeople.views.baseviews.BaseActivityWithViewModel
import kotlinx.android.synthetic.main.activity_popular_persons.*


class PopularPersonsActivity : BaseActivityWithViewModel<PopularPersonsViewModel , ActivityPopularPersonsBinding>() , PersonAdapter.OnItemClicked {

    private lateinit var mPersonsAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        observeData()
    }

    private fun setupViews() {
        title = getString(R.string.popular_people)
        injectAdapter()
        mActivityBinding?.personsRecycler?.layoutManager = LinearLayoutManager(this)
        mActivityBinding?.personsRecycler?.adapter = mPersonsAdapter
    }

    private fun injectAdapter() {
        val personAdapterComponent: PersonAdapterComponent = DaggerPersonAdapterComponent.builder()
            .contextModule(ContextModule(this))
            .onItemClickPersonModule(OnItemClickPersonModule(this))
            .build()

        mPersonsAdapter = personAdapterComponent.getPersonAdapter()
    }

    private fun observeData() {
        mActivityViewModel?.getErrorLiveData()?.observe(this, Observer {
            progressBar.visibility = View.GONE
            centerProgressBar.visibility = View.GONE
            Toast.makeText(this,it,Toast.LENGTH_LONG).show()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            startActivity(Intent(this, SearchPersonsActivity::class.java))
            return true
        } else if (item.itemId == R.id.refresh) {
            mActivityBinding?.centerProgressBar?.visibility = View.VISIBLE
            mActivityViewModel?.invalidate()
            return true
        }
        return false
    }

    override fun onItemClicked(id: Int?) {
        startActivity(Intent(this,PersonDetailsActivity::class.java).putExtra(Constants.PERSON_ID_PATH,id))
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
