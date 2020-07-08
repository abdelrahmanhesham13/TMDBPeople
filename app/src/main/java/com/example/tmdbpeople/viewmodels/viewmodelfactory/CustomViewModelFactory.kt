package com.example.tmdbpeople.viewmodels.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import com.example.tmdbpeople.networkutils.LoadCallback
import com.example.tmdbpeople.viewmodels.PopularPersonsViewModel

class CustomViewModelFactory(var loadCallback: LoadCallback) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return PopularPersonsViewModel(loadCallback) as T
    }
}
