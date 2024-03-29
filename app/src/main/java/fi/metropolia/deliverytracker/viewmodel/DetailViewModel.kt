package fi.metropolia.deliverytracker.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import fi.metropolia.deliverytracker.model.DeliveryTrackDatabase
import fi.metropolia.deliverytracker.model.Request
import fi.metropolia.deliverytracker.util.SharedPreferencesHelper
import kotlinx.coroutines.launch

/**
 * ViewModel for detail screen
 */
class DetailViewModel(application: Application) : BaseViewModel(application) {
    val requestDetail = MutableLiveData<Request>()
    val startDeliveryState = MutableLiveData<Int>()
    private var prefHelper = SharedPreferencesHelper(getApplication())

    //Fetch request detail based on request Id
    fun fetch(requestId: Int) {
        launch {
            val request = DeliveryTrackDatabase(getApplication()).requestDao().getRequest(requestId)
            requestDetail.value = request
            //Checking status of the request
            if(request.status == "Finished") startDeliveryState.value = 3
            else {
                when {
                    //No transporter accepted yet
                    request.transporterName == null -> startDeliveryState.value = 0
                    //User has accepted the request
                    request.transporterName == prefHelper.getUsername() -> startDeliveryState.value = 1
                    //Somebody has accepted request
                    else -> startDeliveryState.value = 2
                }
            }

        }
    }
}