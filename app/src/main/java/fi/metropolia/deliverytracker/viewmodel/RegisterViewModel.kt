package fi.metropolia.deliverytracker.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import fi.metropolia.deliverytracker.model.DeliveryTrackDatabase
import fi.metropolia.deliverytracker.model.User
import fi.metropolia.deliverytracker.util.SharedPreferencesHelper
import kotlinx.coroutines.launch

/**
 * ViewModel for register page
 */
class RegisterViewModel(application: Application): BaseViewModel(application) {
    val registerState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Boolean>()
    private val userDao = DeliveryTrackDatabase(getApplication()).userDao()
    private var prefHelper = SharedPreferencesHelper(getApplication())

    //Register User by inserting user to database
    fun registerUser(user: User) {
        launch {
            //Result returns -1 if there is already a user with same username
            val result = userDao.insertUser(user)
            if(result != (-1).toLong()) {
                registerState.value = true
                errorState.value = false
                //Save user to SharePreferences
                prefHelper.saveUsername(user.userName)
            }
            else {
                registerState.value = false
                errorState.value = true
            }
        }
    }
}