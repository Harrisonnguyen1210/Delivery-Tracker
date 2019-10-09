package fi.metropolia.deliverytracker.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class SharedPreferencesHelper {

    companion object {
        private const val USER_NAME = "Username"
        private var prefs: SharedPreferences? = null

        @Volatile private var instance: SharedPreferencesHelper? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): SharedPreferencesHelper = instance ?: synchronized(LOCK) {
            instance ?: builderHelper(context).also {
                instance = it
            }
        }

        private fun builderHelper(context: Context): SharedPreferencesHelper {
            prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferencesHelper()
        }
    }

    fun saveUsername(username: String) {
        prefs?.edit(commit = true) {
            putString(USER_NAME, username)
        }
    }

    fun getUsername() = prefs?.getString(USER_NAME, "delivery")
}