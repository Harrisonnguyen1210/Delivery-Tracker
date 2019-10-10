package fi.metropolia.deliverytracker.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import fi.metropolia.deliverytracker.R
import fi.metropolia.deliverytracker.model.DeliveryTrackDatabase

/**
 * Main and single activity for whole app
 */
class MainActivity : AppCompatActivity() {

    private val MY_PERMISSIONS_REQUEST_LOCATION = 99
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        //Reset theme after splash screen
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        checkLocationPermission()
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.fragment)
    }

    //Close app database when app closes
    override fun onDestroy() {
        super.onDestroy()
        DeliveryTrackDatabase(applicationContext).close()
    }

    //Check if the app has location permission
    private fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder(this)
                    .setTitle("Location permission")
                    .setMessage("Give location permission")
                    .setPositiveButton("Ok") { _, _ ->
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            MY_PERMISSIONS_REQUEST_LOCATION
                        )
                    }
                    .create()
                    .show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
            return false
        } else {
            return true
        }
    }

    override fun onBackPressed() {
        NavigationUI.navigateUp(navController, null)
    }
}
