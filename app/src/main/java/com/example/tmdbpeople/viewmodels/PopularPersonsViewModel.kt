package com.example.tmdbpeople.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.tmdbpeople.datasource.populardatasource.PersonDataSourceFactory
import com.example.tmdbpeople.models.responsemodels.PersonDetailsResponse
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.networkutils.LoadCallback

//PopularPersonsViewModel create DataSource Factory for Person List Pagination and create LiveData object to observe on it
class PopularPersonsViewModel(private var loadCallback: LoadCallback) : ViewModel() {
    internal var personDataSourceFactory : PersonDataSourceFactory
    val personPagedList: LiveData<PagedList<PersonDetailsResponse?>>
    private val liveDataSource: LiveData<PageKeyedDataSource<Int?, PersonDetailsResponse?>>

    init {
        personDataSourceFactory =
            PersonDataSourceFactory(
                loadCallback,
                null
            )
        liveDataSource = personDataSourceFactory.itemLiveDataSource
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Constants.PAGE_SIZE).build()
        personPagedList = LivePagedListBuilder(
            personDataSourceFactory,
            pagedListConfig
        ).build()
    }

    fun invalidate() {
        personDataSourceFactory.invalidate()
    }
}