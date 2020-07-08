package com.example.tmdbpeople.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tmdbpeople.models.responsemodels.PersonDetailsResponse
import com.example.tmdbpeople.repositories.PersonDetailsRepository

class PersonDetailsViewModel(private var personId : Int) : ViewModel() {

    var personDetailsLiveData : LiveData<PersonDetailsResponse?>?

    init {
        val personDetailsRepository : PersonDetailsRepository? = PersonDetailsRepository.instance
        personDetailsLiveData = personDetailsRepository?.getPersonDetails(personId)
    }

}