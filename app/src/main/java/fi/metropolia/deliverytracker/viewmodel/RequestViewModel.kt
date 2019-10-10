package fi.metropolia.deliverytracker.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import fi.metropolia.deliverytracker.model.DeliveryTrackDatabase
import fi.metropolia.deliverytracker.model.Request
import fi.metropolia.deliverytracker.model.RequestsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

/**
 * ViewModel for requests screen
 */
class RequestViewModel(application: Application) : BaseViewModel(application) {
    private val requestsApiService = RequestsApiService()
    private val requestDao = DeliveryTrackDatabase(getApplication()).requestDao()
    private val disposable = CompositeDisposable()
    val loading = MutableLiveData<Boolean>()
    val requests = MutableLiveData<List<Request>>()
    val requestsLoadError = MutableLiveData<Boolean>()

    //Fetch data from API if database is empty, else fetch from database
    fun refresh() {
        launch {
            val initialData = requestDao.getAllRequests()
            if(initialData.isEmpty()) {
                fetchFromRemote()
            }
            else fetchFromDatabase()
        }
    }

    //Fetch data from API
    private fun fetchFromRemote() {
        loading.value = true
        disposable.add(
            //implement data retrieving on a new thread (not on main thread)
            requestsApiService.getRequests()
                .subscribeOn(Schedulers.newThread())
                //display result on UI thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Request>>() {
                    override fun onError(e: Throwable) {
                        requestsLoadError.value = true
                        loading.value = false
                        e.printStackTrace()
                    }

                    override fun onSuccess(requestsList: List<Request>) {
                        storeRequestsLocally(requestsList)
                        requestsRetrieved(requestsList)
                    }
                })
        )
    }

    //Fetch data from local database
    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            requestsRetrieved(requestDao.getAllRequests())
        }
    }

    //Update LiveData
    private fun requestsRetrieved(requestsList: List<Request>) {
        requests.value = requestsList
        requestsLoadError.value = false
        loading.value = false
    }

    //Insert list of requests into database
    private fun storeRequestsLocally(requestList: List<Request>) {
        launch {
            requestDao.deleteAllRequests()
            requestDao.insertAll(requestList)
        }
    }
}