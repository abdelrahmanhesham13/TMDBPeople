package com.example.tmdbpeople.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tmdbpeople.dagger.component.repositories.DaggerPersonDetailsRepositoryComponent
import com.example.tmdbpeople.dagger.modules.ContextModule
import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.models.responsemodels.PersonImagesResponse
import com.example.tmdbpeople.utils.networkutils.Constants
import com.example.tmdbpeople.repositories.PersonDetailsRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

//PersonDetailsViewModel to get user details and images LiveData from repository and pass it to Activity to observe
class PersonDetailsViewModel(personId: Int, context: Context) : ViewModel() {

    var personDetailsLiveData: LiveData<PersonModel?>
    var personImagesLiveData: LiveData<PersonImagesResponse?>
    var errorStateLiveData: LiveData<Int>
    var compositeDisposable: CompositeDisposable?

    @Inject
    public lateinit var personDetailsRepository: PersonDetailsRepository

    init {
        val daggerPersonDetailsRepositoryComponent = DaggerPersonDetailsRepositoryComponent
            .builder()
            .contextModule(ContextModule(context))
            .build()
        daggerPersonDetailsRepositoryComponent.inject(this)

        personDetailsLiveData = personDetailsRepository.getPersonDetails(personId)
        personImagesLiveData = personDetailsRepository.getPersonImages(personId)
        errorStateLiveData = personDetailsRepository.errorStateLiveData;
        compositeDisposable = personDetailsRepository.compositeDisposable
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.dispose();
        compositeDisposable?.clear()
        compositeDisposable = null

    }

}