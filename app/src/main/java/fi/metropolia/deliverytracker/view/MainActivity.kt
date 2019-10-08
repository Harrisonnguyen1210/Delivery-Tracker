package fi.metropolia.deliverytracker.view

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import fi.metropolia.deliverytracker.R
import fi.metropolia.deliverytracker.model.DeliveryTrackDatabase

class MainActivity : AppCompatActivity() {

    private val MY_PERMISSIONS_REQUEST_LOCATION = 99

    override fun onCreate(savedInstanceState: Bundle?) {
        //Reset theme after splash screen
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        checkLocationPermission()
        setContentView(R.layout.activity_main)
    }

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
}
