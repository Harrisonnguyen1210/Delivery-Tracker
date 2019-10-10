package fi.metropolia.deliverytracker.view

import android.view.View

/**
 * Listener interface for request item click
 */
interface RequestClickListener {
    fun onRequestClick(requestId: Int, v: View)
}