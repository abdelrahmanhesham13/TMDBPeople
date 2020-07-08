package com.example.tmdbpeople.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.tmdbpeople.datasource.populardatasource.PersonDataSourceFactory
import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.networkutils.LoadCallback

//PopularPersonsViewModel create DataSource Factory for Person List Pagination and create LiveData object to observe on it
class PopularPersonsViewModel(application: Application) : AndroidViewModel(application) {
    private var personDataSourceFactory : PersonDataSourceFactory = PersonDataSourceFactory(application)
    val personPagedList: LiveData<PagedList<PersonModel?>>
    private val liveDataSource: LiveData<PageKeyedDataSource<Int?, PersonModel?>>

    init {
        liveDataSource = personDataSourceFactory.itemLiveDataSource
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Constants.PAGE_SIZE).build()
        personPagedList = LivePagedListBuilder(personDataSourceFactory, pagedListConfig).build()
    }

    fun getStateLiveData() : LiveData<Int>? {
        return personDataSourceFactory.getStateLiveData()
    }

    fun getErrorLiveData() : LiveData<String>? {
        return personDataSourceFactory.getErrorLiveData()
    }

    fun invalidate() {
        personDataSourceFactory.invalidate()
    }

    override fun onCleared() {
        super.onCleared()
        personDataSourceFactory.compositeDisposable?.dispose()
        personDataSourceFactory.compositeDisposable?.clear()
        personDataSourceFactory.compositeDisposable = null
    }
}