package com.example.tmdbpeople.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tmdbpeople.models.responsemodels.PersonDetailsResponse
import com.example.tmdbpeople.models.responsemodels.PersonImagesResponse
import com.example.tmdbpeople.repositories.PersonDetailsRepository

class PersonDetailsViewModel(personId : Int) : ViewModel() {

    var personDetailsLiveData : LiveData<PersonDetailsResponse?>?
    var personImagesLiveData : LiveData<PersonImagesResponse?>?

    init {
        val personDetailsRepository : PersonDetailsRepository? = PersonDetailsRepository.instance
        personDetailsLiveData = personDetailsRepository?.getPersonDetails(personId)
        personImagesLiveData = personDetailsRepository?.getPersonImages(personId)
    }

}