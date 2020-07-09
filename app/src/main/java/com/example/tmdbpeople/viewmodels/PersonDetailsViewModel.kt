package com.example.tmdbpeople.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.models.responsemodels.PersonImagesResponse
import com.example.tmdbpeople.repositories.PersonDetailsRepository
import io.reactivex.disposables.CompositeDisposable

//PersonDetailsViewModel to get user details and images LiveData from repository and pass it to Activity to observe
class PersonDetailsViewModel(personId: Int, context: Context) : ViewModel() {

    var personDetailsLiveData: LiveData<PersonModel?>?
    var personImagesLiveData: LiveData<PersonImagesResponse?>?
    var loadStateLiveData: LiveData<Int>?
    var errorStateLiveData: LiveData<Int>?
    var compositeDisposable: CompositeDisposable?

    init {
        val personDetailsRepository: PersonDetailsRepository? = PersonDetailsRepository(context)
        personDetailsLiveData = personDetailsRepository?.getPersonDetails(personId)
        personImagesLiveData = personDetailsRepository?.getPersonImages(personId)
        loadStateLiveData = personDetailsRepository?.loadStateLiveData
        errorStateLiveData = personDetailsRepository?.errorStateLiveData;
        compositeDisposable = personDetailsRepository?.compositeDisposable
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.dispose();
        compositeDisposable?.clear()
        compositeDisposable = null

    }

}