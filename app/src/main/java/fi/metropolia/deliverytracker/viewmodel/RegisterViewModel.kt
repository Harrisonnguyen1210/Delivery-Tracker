package fi.metropolia.deliverytracker.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import fi.metropolia.deliverytracker.model.DeliveryTrackDatabase
import fi.metropolia.deliverytracker.model.User
import fi.metropolia.deliverytracker.util.SharedPreferencesHelper
import kotlinx.coroutines.launch

class RegisterViewModel(application: Application): BaseViewModel(application) {
    val registerState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Boolean>()
    private val userDao = DeliveryTrackDatabase(getApplication()).userDao()
    private var prefHelper = SharedPreferencesHelper(getApplication())

    fun registerUser(user: User) {
        launch {
            val result = userDao.insertUser(user)
            if(result != (-1).toLong()) {
                registerState.value = true
                errorState.value = false
                prefHelper.saveUsername(user.userName)
            }
            else {
                registerState.value = false
                errorState.value = true
            }
        }
    }
}