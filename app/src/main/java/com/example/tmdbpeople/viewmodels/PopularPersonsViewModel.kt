package com.example.tmdbpeople.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.tmdbpeople.datasource.PersonDataSource
import com.example.tmdbpeople.datasource.PersonDataSourceFactory
import com.example.tmdbpeople.models.PersonModel

class PopularPersonsViewModel : ViewModel() {
    val itemPagedList: LiveData<PagedList<PersonModel?>>
    val liveDataSource: LiveData<PageKeyedDataSource<Int, PersonModel>>

    init {
        val personDataSourceFactory = PersonDataSourceFactory()
        liveDataSource = personDataSourceFactory.itemLiveDataSource
        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PersonDataSource.PAGE_SIZE).build()
        itemPagedList = LivePagedListBuilder(
            personDataSourceFactory,
            pagedListConfig
        )
            .build()
    }
}