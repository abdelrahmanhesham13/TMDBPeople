package com.example.tmdbpeople.viewmodels.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.tmdbpeople.networkutils.LoadCallback
import com.example.tmdbpeople.viewmodels.PersonDetailsViewModel
import com.example.tmdbpeople.viewmodels.PopularPersonsViewModel
import com.example.tmdbpeople.viewmodels.SearchPersonsViewModel

//Factory class to create ViewModels each with its required parameters
class CustomViewModelFactory() : ViewModelProvider.NewInstanceFactory() {

    private var personId : Int? = null

    constructor(personId : Int) : this() {
        this.personId = personId
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PersonDetailsViewModel(personId!!) as T
    }
}
