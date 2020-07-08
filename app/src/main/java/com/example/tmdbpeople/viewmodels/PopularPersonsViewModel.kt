package com.example.tmdbpeople.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.tmdbpeople.datasource.PersonDataSource
import com.example.tmdbpeople.datasource.PersonDataSourceFactory
import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.networkutils.LoadCallback

class PopularPersonsViewModel(private var loadCallback: LoadCallback) : ViewModel() {
    val personPagedList: LiveData<PagedList<PersonModel?>>
    private val liveDataSource: LiveData<PageKeyedDataSource<Int?, PersonModel?>>

    init {
        val personDataSourceFactory = PersonDataSourceFactory(loadCallback,Constants.POPULAR_TYPE , null)
        liveDataSource = personDataSourceFactory.itemLiveDataSource
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PersonDataSource.PAGE_SIZE).build()
        personPagedList = LivePagedListBuilder(
            personDataSourceFactory,
            pagedListConfig
        ).build()
    }
}