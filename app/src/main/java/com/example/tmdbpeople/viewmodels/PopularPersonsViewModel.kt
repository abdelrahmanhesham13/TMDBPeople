package com.example.tmdbpeople.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.tmdbpeople.models.responsemodels.PopularPersonResponse
import com.example.tmdbpeople.repositories.PopularPersonsRepository
import com.example.tmdbpeople.repositories.PopularPersonsRepository.Companion.instance

class PopularPersonsViewModel(application: Application) :
    AndroidViewModel(application) {
    var mPageNumber = 1
    var popularPersonsList: LiveData<PopularPersonResponse?>?
    var mPopularPersonsRepository: PopularPersonsRepository? = instance

    init {
        popularPersonsList = mPopularPersonsRepository?.getPopularPersons(mPageNumber)
    }

    fun getMoreData() {
        mPageNumber++
        Log.d("ViewMode", mPageNumber.toString())
        mPopularPersonsRepository?.getPopularPersons(mPageNumber)
    }
}