package com.example.tmdbpeople.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList

import com.example.tmdbpeople.datasource.searchdatasource.PersonSearchDataSourceFactory
import com.example.tmdbpeople.models.responsemodels.PersonDetailsResponse
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.networkutils.LoadCallback

class SearchPersonsViewModel(var loadCallback: LoadCallback) : ViewModel() {

    var personPagedList: LiveData<PagedList<PersonDetailsResponse?>> = MutableLiveData()
    private var liveDataSource = MutableLiveData<PageKeyedDataSource<Int?, PersonDetailsResponse?>>()
    internal var personDataSource: PersonSearchDataSourceFactory

    init {
        personDataSource = PersonSearchDataSourceFactory(loadCallback,null)

        liveDataSource = personDataSource.itemLiveDataSource

        val pagedListConfig = PagedList.Config.Builder()
            .setPageSize(Constants.PAGE_SIZE)
            .setEnablePlaceholders(false).build()

        personPagedList = LivePagedListBuilder(personDataSource, pagedListConfig)
            .build()
    }

    fun doSearch(query: String) {
        if (!query.isEmpty()) {
            personDataSource.query = query
            personDataSource.invalidate()
        }
    }
}
