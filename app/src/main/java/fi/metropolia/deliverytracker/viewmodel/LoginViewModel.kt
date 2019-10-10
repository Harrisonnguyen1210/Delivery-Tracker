package fi.metropolia.deliverytracker.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import fi.metropolia.deliverytracker.model.DeliveryTrackDatabase
import fi.metropolia.deliverytracker.model.User
import fi.metropolia.deliverytracker.util.SharedPreferencesHelper
import kotlinx.coroutines.launch

/**
 * ViewModel for login screen
 */
class LoginViewModel(application: Application): BaseViewModel(application) {
    private var prefHelper = SharedPreferencesHelper(getApplication())
    val loginState = MutableLiveData<Boolean>()
    val errorState = MutableLiveData<Boolean>()
    private val userDao = DeliveryTrackDatabase(getApplication()).userDao()

    //Login user by checking if User exists
    fun loginUser(user: User) {
        launch {
            val retrievedUser = userDao.loginUser(user.userName, user.password)
            if(retrievedUser != null) {
                errorState.value = false
                loginState.value = true
                //Save user to SharePreferences
                prefHelper.saveUsername(user.userName)
            }
            else {
                errorState.value = true
                loginState.value = false
            }
        }
    }
}