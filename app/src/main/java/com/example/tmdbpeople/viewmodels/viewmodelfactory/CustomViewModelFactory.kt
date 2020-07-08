package com.example.tmdbpeople.viewmodels.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.tmdbpeople.networkutils.LoadCallback
import com.example.tmdbpeople.viewmodels.PersonDetailsViewModel
import com.example.tmdbpeople.viewmodels.PopularPersonsViewModel
import com.example.tmdbpeople.viewmodels.SearchPersonsViewModel

class CustomViewModelFactory() :
    ViewModelProvider.NewInstanceFactory() {

    private lateinit var loadCallback : LoadCallback
    private var personId : Int = 0

    constructor(personId : Int) : this() {
        this.personId = personId
    }

    constructor(loadCallback: LoadCallback) : this() {
        this.loadCallback = loadCallback
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.simpleName == "PopularPersonsViewModel") {
            return PopularPersonsViewModel(loadCallback) as T
        } else if (modelClass.simpleName == "SearchPersonsViewModel") {
            return SearchPersonsViewModel(loadCallback) as T
        } else {
            return PersonDetailsViewModel(personId) as T
        }
    }
}
