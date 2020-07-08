package com.example.tmdbpeople.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import com.example.tmdbpeople.R
import com.example.tmdbpeople.dagger.component.DaggerPersonDetailsAdapterComponent
import com.example.tmdbpeople.dagger.component.PersonDetailsAdapterComponent
import com.example.tmdbpeople.dagger.modules.ContextModule
import com.example.tmdbpeople.dagger.modules.OnItemClickedImageModule
import com.example.tmdbpeople.databinding.ActivityPersonDetailsBinding
import com.example.tmdbpeople.models.PersonImage
import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.viewmodels.PersonDetailsViewModel
import com.example.tmdbpeople.viewmodels.viewmodelfactory.CustomViewModelFactory
import com.example.tmdbpeople.views.viewutils.SpacesItemDecoration
import com.example.tmdbpeople.views.adapters.PersonDetailsAdapter
import com.example.tmdbpeople.views.baseviews.BaseActivityWithViewModel
import javax.inject.Inject


class PersonDetailsActivity : BaseActivityWithViewModel<PersonDetailsViewModel, ActivityPersonDetailsBinding>() , PersonDetailsAdapter.OnItemClicked {

    @Inject
    lateinit var mPersonDetailsAdapter: PersonDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        observeDetails()
        observeImages()
    }

    private fun observeImages() {
        mActivityViewModel?.personImagesLiveData?.observe(this , Observer {
            mPersonDetailsAdapter.addImages(it?.profiles as ArrayList<PersonImage>)
        })
    }

    private fun observeDetails() {
        mActivityViewModel?.personDetailsLiveData?.observe(this,
            Observer<PersonModel?> { personModel ->
                mActivityBinding?.progressBar?.visibility = View.GONE
                mPersonDetailsAdapter.setPersonDetailsResponse(personModel)
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

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_person_details
    }

    override fun initialiseViewModel(): PersonDetailsViewModel {
        val viewModelFactory = CustomViewModelFactory(intent.getIntExtra(Constants.PERSON_ID_PATH , Constants.PERSON_ID_PATH_DEFAULT_VALUE))
        return ViewModelProvider(this,viewModelFactory).get(PersonDetailsViewModel::class.java)
    }

    override fun enableBackButton(): Boolean {
        return true
    }
}
