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

class RequestViewModel(application: Application) : BaseViewModel(application) {
    private val requestsApiService = RequestsApiService()
    private val requestDao = DeliveryTrackDatabase(getApplication()).requestDao()
    private val disposable = CompositeDisposable()
    val loading = MutableLiveData<Boolean>()
    val requests = MutableLiveData<List<Request>>()
    val requestsLoadError = MutableLiveData<Boolean>()

    fun refresh() {
        launch {
            val initialData = requestDao.getAllRequests()
            if(initialData.isEmpty()) {
                fetchFromRemote()
            }
            else fetchFromDatabase()
        }
    }

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

    private fun fetchFromDatabase() {
        loading.value = true
        launch {
            requestsRetrieved(requestDao.getAllRequests())
        }
    }

    private fun requestsRetrieved(requestsList: List<Request>) {
        println("LOLOLOLOLO")
        println(requestsList)
        requests.value = requestsList
        requestsLoadError.value = false
        loading.value = false
    }

    private fun storeRequestsLocally(requestList: List<Request>) {
        launch {
            requestDao.deleteAllRequests()
            requestDao.insertAll(requestList)
        }
    }
}