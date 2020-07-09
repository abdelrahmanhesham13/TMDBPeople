package com.example.tmdbpeople.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tmdbpeople.dagger.component.DaggerNetworkServiceComponent
import com.example.tmdbpeople.dagger.component.NetworkServiceComponent
import com.example.tmdbpeople.models.PersonImage
import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.models.responsemodels.PersonImagesResponse
import com.example.tmdbpeople.networkutils.ConnectionUtils
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.networkutils.PersonsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PersonDetailsRepository(val context: Context) {
    @Inject
    lateinit var service : PersonsService

    init {
        val networkServiceComponent : NetworkServiceComponent = DaggerNetworkServiceComponent.builder()
            .build()
        networkServiceComponent.inject(this)
    }
    val loadStateLiveData : MutableLiveData<Int> = MutableLiveData()
    val errorStateLiveData : MutableLiveData<Int> = MutableLiveData()
    val compositeDisposable : CompositeDisposable = CompositeDisposable()
    //Function to call person details api and returns LiveData object to observe changes and get person details response
    fun getPersonDetails(personId: Int): LiveData<PersonModel?> {
        val personDetails = MutableLiveData<PersonModel?>()
        if (ConnectionUtils.isOnline(context)) {
            loadStateLiveData.postValue(Constants.FIRST_LOAD_STATE)
            compositeDisposable.add(service.personDetails(personId, Constants.API_KEY_VALUE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    loadStateLiveData.postValue(Constants.SUCCESS_STATE)
                    personDetails.value = it
                },{
                    personDetails.value = null
                    errorStateLiveData.postValue(Constants.SERVER_ERROR_MESSAGE)
                }))
        } else {
            errorStateLiveData.postValue(Constants.NETWORK_ERROR_MESSAGE)
        }
        return personDetails
    }

    //Function to call person images api and returns LiveData object to observe changes and get person images response
    fun getPersonImages(personId: Int): LiveData<PersonImagesResponse?> {
        val personImages = MutableLiveData<PersonImagesResponse?>()
        if (ConnectionUtils.isOnline(context)) {
            loadStateLiveData.postValue(Constants.FIRST_LOAD_STATE)
            compositeDisposable.add(service.personImages(personId, Constants.API_KEY_VALUE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({

                    (it.profiles as ArrayList<PersonImage>).add(0, PersonImage())
                    personImages.value = it
                    loadStateLiveData.postValue(Constants.SUCCESS_STATE)

                }, {
                    errorStateLiveData.postValue(Constants.SERVER_ERROR_MESSAGE)
                }))
        } else {
            errorStateLiveData.postValue(Constants.NETWORK_ERROR_MESSAGE)
        }
        return personImages
    }

}