package fi.metropolia.deliverytracker.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fi.metropolia.deliverytracker.R
import fi.metropolia.deliverytracker.model.DeliveryTrackDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //Reset theme after splash screen
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()
        DeliveryTrackDatabase(applicationContext).close()
    }
}
