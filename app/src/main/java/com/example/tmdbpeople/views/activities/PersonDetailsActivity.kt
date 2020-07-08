package com.example.tmdbpeople.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.example.tmdbpeople.R
import com.example.tmdbpeople.dagger.component.DaggerPersonAdapterComponent
import com.example.tmdbpeople.dagger.component.DaggerPersonDetailsAdapterComponent
import com.example.tmdbpeople.dagger.component.PersonAdapterComponent
import com.example.tmdbpeople.dagger.component.PersonDetailsAdapterComponent
import com.example.tmdbpeople.dagger.modules.ContextModule
import com.example.tmdbpeople.dagger.modules.OnItemClickPersonModule
import com.example.tmdbpeople.dagger.modules.OnItemClickedImageModule
import com.example.tmdbpeople.databinding.ActivityPersonDetailsBinding
import com.example.tmdbpeople.models.PersonImage
import com.example.tmdbpeople.models.responsemodels.PersonDetailsResponse
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.viewmodels.PersonDetailsViewModel
import com.example.tmdbpeople.viewmodels.viewmodelfactory.CustomViewModelFactory
import com.example.tmdbpeople.views.SpacesItemDecoration
import com.example.tmdbpeople.views.adapters.PersonAdapter
import com.example.tmdbpeople.views.adapters.PersonDetailsAdapter
import javax.inject.Inject


class PersonDetailsActivity : RootActivity() , PersonDetailsAdapter.OnItemClicked {

    @Inject public lateinit var mPersonDetailsAdapter: PersonDetailsAdapter
    private var mActivityBinding : ActivityPersonDetailsBinding? = null
    private var mPersonDetailsViewModel : PersonDetailsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_person_details)
        val viewModelFactory = CustomViewModelFactory(intent.getIntExtra(Constants.PERSON_ID_PATH , 0))
        mPersonDetailsViewModel =
            ViewModelProvider(this,viewModelFactory).get(PersonDetailsViewModel::class.java)
        setupViews()
        observeDetails()
        observeImages()
    }

    private fun observeImages() {
        mPersonDetailsViewModel?.personImagesLiveData?.observe(this , Observer {
            mPersonDetailsAdapter.addImages(it?.profiles as ArrayList<PersonImage>)
        })
    }

    private fun observeDetails() {
        mPersonDetailsViewModel?.personDetailsLiveData?.observe(this, object : Observer<PersonDetailsResponse?> {
            override fun onChanged(personDetailsResponse: PersonDetailsResponse?) {
                mActivityBinding?.progressBar?.visibility = View.GONE
                mPersonDetailsAdapter.setPersonDetailsResponse(personDetailsResponse)
            }
        })
    }

    private fun setupViews() {
        title = getString(R.string.person_details)

        injectAdapter()

        val gridLayout = GridLayoutManager(this, 2)

        //Give the PersonDetails View full width of first row (span 2) else the image will take half of screen width as usual
        gridLayout.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (mPersonDetailsAdapter.getItemViewType(position)) {
                    PersonDetailsAdapter.IMAGE_VIEW_TYPE -> 1
                    PersonDetailsAdapter.DETAILS_VIEW_TYPE -> 2
                    else -> 1
                }
            }
        }


        mActivityBinding?.detailsRecycler?.layoutManager = gridLayout
        mActivityBinding?.detailsRecycler?.addItemDecoration(SpacesItemDecoration(5))
        mActivityBinding?.detailsRecycler?.adapter = mPersonDetailsAdapter
    }

    private fun injectAdapter() {
        val personDetailsAdapterComponent: PersonDetailsAdapterComponent =
            DaggerPersonDetailsAdapterComponent.builder()
                .contextModule(ContextModule(this))
                .onItemClickedImageModule(OnItemClickedImageModule(this))
                .build()

        personDetailsAdapterComponent.inject(this)
    }


    override fun onItemClicked(image: String?) {
        startActivity(Intent(this,ImageViewerActivity::class.java)
            .putExtra(Constants.IMAGE_KEY,image))
    }
}
