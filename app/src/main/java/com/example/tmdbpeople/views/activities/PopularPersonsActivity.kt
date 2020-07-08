package com.example.tmdbpeople.views.activities

import android.content.Intent
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_popular_persons.*


class PopularPersonsActivity : AppCompatActivity() , LoadCallback , PersonAdapter.OnItemClicked {

    private var mPopularPersonsViewModel: PopularPersonsViewModel? = null
    private var mActivityBinding: ActivityPopularPersonsBinding? = null
    lateinit var mPersonsAdapter: PersonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_popular_persons)
        val viewModelFactory = CustomViewModelFactory(this)
        mPopularPersonsViewModel =  ViewModelProvider(this,viewModelFactory).get(PopularPersonsViewModel::class.java)
        setupViews()
        observeData()
    }

    private fun setupViews() {
        title = getString(R.string.popular_people)

        injectAdapter()


        mActivityBinding?.personsRecycler?.layoutManager = LinearLayoutManager(this)
        mActivityBinding?.personsRecycler?.setHasFixedSize(true)
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
        mPopularPersonsViewModel?.personPagedList?.observe(this, Observer {
            mPersonsAdapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            startActivity(Intent(this,
                SearchPersonsActivity::class.java))
            return true
        } else if (item.itemId == R.id.refresh) {
            mActivityBinding?.centerProgressBar?.visibility = View.VISIBLE
            mPopularPersonsViewModel?.invalidate()
            return true
        }
        return false
    }

    override fun onError(message: String) {
        runOnUiThread(Runnable {
            progressBar.visibility = View.GONE
            centerProgressBar.visibility = View.GONE
        })
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
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
        startActivity(Intent(this,PersonDetailsActivity::class.java)
            .putExtra(Constants.PERSON_ID_PATH,id))
    }

}
