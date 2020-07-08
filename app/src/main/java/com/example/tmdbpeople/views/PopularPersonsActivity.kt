package com.example.tmdbpeople.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tmdbpeople.R
import com.example.tmdbpeople.databinding.ActivityMainBinding
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.networkutils.LoadCallback
import com.example.tmdbpeople.viewmodels.PopularPersonsViewModel
import com.example.tmdbpeople.viewmodels.viewmodelfactory.CustomViewModelFactory
import com.example.tmdbpeople.views.activities.PersonDetailsActivity
import com.example.tmdbpeople.views.adapters.PersonAdapter
import kotlinx.android.synthetic.main.activity_main.*

class PopularPersonsActivity : AppCompatActivity() , LoadCallback, PersonAdapter.OnItemClicked {
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



    private var mPopularPersonsViewModel: PopularPersonsViewModel? = null
    var mActivityBinding: ActivityMainBinding? = null
    var mPersonsAdapter: PersonAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val viewModelFactory = CustomViewModelFactory(this)
        mPopularPersonsViewModel =
            ViewModelProviders.of(this,viewModelFactory).get(PopularPersonsViewModel::class.java)
        setupViews()
        observeData()
    }

    private fun setupViews() {
        setTitle("Popular People")
        mPersonsAdapter = PersonAdapter(this,this)
        mActivityBinding?.personsRecycler?.layoutManager = LinearLayoutManager(this)
        mActivityBinding?.personsRecycler?.setHasFixedSize(true)
        mActivityBinding?.personsRecycler?.adapter = mPersonsAdapter
    }

    private fun observeData() {
        mPopularPersonsViewModel?.personPagedList?.observe(this, Observer {
            mPersonsAdapter?.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search) {
            startActivity(Intent(this,SearchPersonsActivity::class.java))
            return true
        } else if (item.itemId == R.id.refresh) {
            mPopularPersonsViewModel?.invalidate()
            return true
        }
        return false
    }

    override fun onItemClicked(id: Int?) {
        startActivity(Intent(this, PersonDetailsActivity::class.java)
            .putExtra(Constants.PERSON_ID_PATH,id))
    }

}
